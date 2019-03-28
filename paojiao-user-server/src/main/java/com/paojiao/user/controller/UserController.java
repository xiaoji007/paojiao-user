package com.paojiao.user.controller;

import com.codahale.metrics.annotation.Timed;
import com.fission.fastdfs.common.FastDfsTemplate;
import com.fission.motan.spring.Rpc;
import com.fission.next.common.bean.ClientContext;
import com.fission.next.common.constant.RouteEventNames;
import com.fission.next.common.constant.RouteFieldNames;
import com.fission.next.common.constant.ServiceName;
import com.fission.next.common.error.FissionCodeException;
import com.fission.next.common.error.FissionException;
import com.fission.next.utils.ErrorCode;
import com.fission.next.utils.LoginUtil;
import com.fission.utils.bean.ResultUtil;
import com.fission.utils.tool.JsonUtil;
import com.fission.utils.tool.StringUtil;
import com.fission.web.annotation.Login;
import com.login.api.service.ILoginService;
import com.paojiao.user.api.bean.UserInviteCodeBean;
import com.paojiao.user.api.services.IUserPicService;
import com.paojiao.user.api.services.IUserService;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.api.util.UserErrorCode;
import com.paojiao.user.config.ApplicationConfig;
import com.paojiao.user.controller.attr.UpdateUserService;
import com.paojiao.user.controller.bean.UserInfoBean;
import com.paojiao.user.controller.exception.KeywordException;
import com.paojiao.user.controller.util.ParamNameUtil;
import com.paojiao.user.service.IUserCacheService;
import com.paojiao.user.task.UserTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    private FastDfsTemplate fastDfsTemplate;
    @Rpc(ServiceName.LOGIN)
    private ILoginService loginHandler;
    @Inject
    private RabbitTemplate rabbit;
    @Inject
    private UpdateUserService updateUserService;
    @Inject
    private com.paojiao.user.api.services.IUserCacheService userCacheHandler;
    @Inject
    private IUserCacheService userCacheService;
    @Inject
    private IUserService userHandler;
    @Inject
    private UserTaskService userTaskService;
    @Inject
    private IUserPicService userPicHandler;

    /**
     * 获取chat token
     *
     * @param clientContext 客服端上下文
     */
    @Timed(name = "http.user.chat_token", absolute = true)
    @RequestMapping("chat_token")
    @Login
    public ResultUtil<String> getChatToken(ClientContext clientContext) {
        try {
            return this.userHandler.getUserToken(clientContext.getUserId(), clientContext);
        } catch (Exception e) {
            ResultUtil<String> resultUtil = new ResultUtil<>();
            resultUtil.setCode(UserErrorCode.USER_ERROR);
            String message = String.format("getChatToken error.param(context:%s)", JsonUtil.objToJsonString(clientContext));
            UserController.LOGGER.error(message, e);
            return resultUtil;
        }
    }

    /**
     * 获取用户在线状态
     *
     * @param user_ids
     * @param clientContext
     * @return
     */
    @Timed(name = "http.user.onlines", absolute = true)
    @RequestMapping("users/online")
    public ResultUtil<List<Integer>> getOnlineUsers(@RequestParam(ParamNameUtil.USER_IDS) String user_ids, ClientContext clientContext) {
        if (StringUtil.isBlank(user_ids)) {
            return ResultUtil.build(ErrorCode.PARAM_ERROR);
        }
        List<Integer> userIds = JsonUtil.stringToObjectList(user_ids, Integer.class);
        if (userIds == null) {
            return ResultUtil.build(ErrorCode.PARAM_ERROR);
        }
        return ResultUtil.build(ErrorCode.SUCCESS, userCacheService.getOnlineUsers(userIds));
    }

    /**
     * 保持在线
     *
     * @param clientContext
     * @return
     */
    @Timed(name = "http.user.online.keep", absolute = true)
    @RequestMapping("/keep")
    public ResultUtil<Void> keep(ClientContext clientContext) {
        this.userHandler.setUserOnlineStatus(clientContext.getUserId(), true, clientContext);
        return ResultUtil.build(ErrorCode.SUCCESS);
    }


    /**
     * 获取用户自己信息
     *
     * @param clientContext 客服端上下文
     */
    @RequestMapping("/")
    @Login
    @Timed(name = "http.user.self", absolute = true)
    public ResultUtil<Object> getSelfUserInfo(@RequestParam(required = false, value = ParamNameUtil.INVITE_CODE) String inviteCode, ClientContext clientContext) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        try {
            int selfUserId = clientContext.getUserId();
            //注册邀请类型
            short inviteType;
            if (selfUserId < 1) {
                inviteType = ConstUtil.InviteType.REGISTER;
                selfUserId = this.userHandler.getUserId(ConstUtil.UserType.COMMON, clientContext.getLoginId(), clientContext).getDataInfo();
                if (selfUserId < 1) {
                    resultUtil.setCode(UserErrorCode.USER_ERROR);
                    return resultUtil;
                }
                //登录信息中绑定app 和 用户id
                ResultUtil<Void> resultUtil2 = this.loginHandler.addLoginAppInfo(clientContext.getAppId(), clientContext.getLoginId(), selfUserId, clientContext);
                if (ErrorCode.SUCCESS != resultUtil2.getCode()) {
                    resultUtil.setCode(UserErrorCode.USER_ERROR);
                    return resultUtil;
                }
                clientContext.setUserId(selfUserId);
            } else {
                inviteType = ConstUtil.InviteType.HAVE_REGISTER;
            }

            if (StringUtil.isNotBlank(inviteCode)) {
                //更新code验证状态
                this.userHandler.updateUserInviteCode(selfUserId, inviteCode, inviteType, clientContext);
            }

            //清理缓存
            this.userCacheHandler.clearUser(clientContext.getUserId(), clientContext);

            //初始化用户信息
            ResultUtil<UserInfoBean> resultUtil2 = this.getUserInfoBean(clientContext, clientContext.getUserId());
            if (UserErrorCode.USER_NO_EXISTS_ERROR == resultUtil2.getCode()) {
                resultUtil.setCode(UserErrorCode.USER_NO_INIT_ERROR);
            } else {
                int code = com.paojiao.user.util.ConstUtil.CheckUserInfoUtil.checkInit(resultUtil2.getDataInfo());
                resultUtil.setCode(code);
                if (ErrorCode.SUCCESS == resultUtil.getCode()) {
                    resultUtil.setDataInfo(resultUtil2.getDataInfo());
                    this.userTaskService.addInviteUser(selfUserId);
                }
            }
            return resultUtil;
        } catch (Exception e) {
            String message = String.format("getSelfUserInfo error.param(context:%s)", JsonUtil.objToJsonString(clientContext));
            UserController.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
            return resultUtil;
        } finally {
            try {
                LoginUtil.me().delayLoginKey(clientContext.getLoginId(), clientContext.getAppId());
            } catch (Exception e) {
                UserController.LOGGER.error("delayLoginKey fail", e);
            }
        }
    }

    /**
     * 添加用户图片
     *
     * @param clientContext 客服端上下文
     * @param file
     */
    @RequestMapping("/pic/add")
    public ResultUtil<Void> addUserPicInfo(ClientContext clientContext, @RequestParam(ParamNameUtil.USER_PIC_INDEX) int userPicIndex, @RequestParam(ParamNameUtil.USER_PIC) MultipartFile file) {
        int selfUserId = clientContext.getUserId();
        try {
            String fileName = file.getOriginalFilename();
            int index = fileName.lastIndexOf(".");
            String fileExtName = "";
            if (index >= 0) {
                fileExtName = fileName.substring(index);
            }

            if (!this.applicationConfig.isPicSuffix(fileExtName)) {
                return ResultUtil.build(UserErrorCode.PIC_FILE_EXT_ERROR);
            }
            ResultUtil<String> resultUtil1 = this.uploadIntroduce(file, this.applicationConfig.getPicLength());
            if (ErrorCode.SUCCESS != resultUtil1.getCode()) {
                return ResultUtil.build(resultUtil1.getCode());
            }
            return this.userPicHandler.addUserPicInfo(selfUserId, userPicIndex, resultUtil1.getDataInfo(), clientContext);
        } catch (Exception e) {
            String message = String.format("addUserPicInfo error.param(selfUserId:%s,userPicIndex:%s,context:%s)", selfUserId, userPicIndex, JsonUtil.objToJsonString(clientContext));
            UserController.LOGGER.error(message, e);
            return ResultUtil.build(UserErrorCode.USER_ERROR);
        }
    }

    /**
     * 更新首页图片
     *
     * @param index   文件位置
     * @param toIndex 文件位置
     */
    @RequestMapping("/pic/move")
    public ResultUtil<Void> moveUserPic(ClientContext clientContext,
                                        @RequestParam(value = ParamNameUtil.USER_PIC_INDEX, defaultValue = "0") int index,
                                        @RequestParam(value = ParamNameUtil.USER_PIC_TO_INDEX, defaultValue = "0") int toIndex) {
        ResultUtil<Void> resultUtil = new ResultUtil<>();
        try {
            int selfUserId = clientContext.getUserId();
            int length = this.applicationConfig.getPicNum();
            if (index > length || index < 1 || toIndex > length || toIndex < -1) {
                resultUtil.setCode(ErrorCode.PARAM_ERROR);
                return resultUtil;
            }
            ResultUtil<Void> resultUtil2 = this.userPicHandler.moveUserPic(selfUserId, index, toIndex, clientContext);
            resultUtil.setCode(resultUtil2.getCode());
        } catch (Exception e) {
            resultUtil.setCode(UserErrorCode.USER_ERROR);
            String message = String.format("moveUserPic error.param(index:%s,toIndex:%s,context:%s)", index, toIndex, JsonUtil.objToJsonString(clientContext));
            UserController.LOGGER.error(message, e);
        }
        return resultUtil;
    }

    /**
     * 获取邀请连接
     *
     * @param clientContext 客服端上下文
     */
    @Timed(name = "http.user.invite_address", absolute = true)
    @RequestMapping("invite_address")
    @Login
    public ResultUtil<String> getUserInviteAddress(@RequestParam(value = ParamNameUtil.SHARE_TYPE, defaultValue = "0") int shareType, ClientContext clientContext) {
        ResultUtil<String> resultUtil = null;
        try {
            String userInviteAddress = this.applicationConfig.getUserInviteAddress();
            if (StringUtil.isBlank(userInviteAddress)) {
                resultUtil = new ResultUtil<>();
                resultUtil.setCode(UserErrorCode.USER_ERROR);
                return resultUtil;
            }
            resultUtil = this.userHandler.getUserInviteCode(clientContext.getUserId(), shareType, clientContext);
            if (ErrorCode.SUCCESS == resultUtil.getCode()) {
                resultUtil.setDataInfo(userInviteAddress + "" + resultUtil.getDataInfo());
            }
            return resultUtil;
        } catch (Exception e) {
            resultUtil = new ResultUtil<>();
            resultUtil.setCode(UserErrorCode.USER_ERROR);
            String message = String.format("getUserInviteAddress error.param(context:%s)", JsonUtil.objToJsonString(clientContext));
            UserController.LOGGER.error(message, e);
            return resultUtil;
        }
    }

    /**
     * 初始化用户属性
     *
     * @param clientContext 客服端上下文
     * @param inviteCode    邀请码
     * @param userAttr      用户属性Json 字符串
     */

    @Timed(name = "http.user.init_attr", absolute = true)
    @RequestMapping("init_attr")
    @Login
    public ResultUtil<Object> initUserAttr(ClientContext clientContext, @RequestParam(required = false, value = ParamNameUtil.INVITE_CODE) String inviteCode, @RequestParam(ParamNameUtil.USER_ATTR) String userAttr) {
        ResultUtil<Object> resultUtil = this.updateUserAttr(clientContext, userAttr, true);
        if (ErrorCode.SUCCESS == resultUtil.getCode()) {
            return this.getSelfUserInfo(inviteCode, clientContext);
        }
        return resultUtil;
    }

    /**
     * 更新用户属性
     *
     * @param clientContext 客服端上下文
     * @param userAttr      用户属性Json 字符串
     */
    @Timed(name = "http.user.update_attr", absolute = true)
    @RequestMapping("update_attr")
    @Login
    public ResultUtil<Object> updateUserAttr(ClientContext clientContext, @RequestParam(ParamNameUtil.USER_ATTR) String userAttr) {
        return this.updateUserAttr(clientContext, userAttr, false);
    }

    /**
     * 语音介绍
     *
     * @param clientContext
     * @param multipartFile
     * @param duration
     * @return
     */
    @Login
    @RequestMapping("/audio/introduce/upload")
    public ResultUtil<Object> uploadAudioIntroduce(ClientContext clientContext, @RequestParam(ParamNameUtil.INTRODUCE) MultipartFile multipartFile, @RequestParam(ParamNameUtil.DURATION) long duration) {
        return this.uploadIntroduce(clientContext, multipartFile, duration, String.valueOf(ConstUtil.UserAttrId.AUDIO_INTRODUCE_URL));
    }

    /**
     * 视频介绍
     *
     * @param clientContext
     * @param multipartFile
     * @param duration
     * @return
     */
    @Login
    @RequestMapping("/video/introduce/upload")
    public ResultUtil<Object> uploadVideoIntroduce(ClientContext clientContext, @RequestParam(ParamNameUtil.INTRODUCE) MultipartFile multipartFile, @RequestParam(ParamNameUtil.DURATION) long duration) {
        return this.uploadIntroduce(clientContext, multipartFile, duration, String.valueOf(ConstUtil.UserAttrId.VIDEO_INTRODUCE_URL));
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    private ResultUtil<UserInfoBean> getUserInfoBean(ClientContext clientContext, int userId) {
        try {
            ResultUtil<UserInfoBean> resultUtil = new ResultUtil<>();
            ResultUtil<com.paojiao.user.api.bean.UserInfoBean> resultUtil2;
            boolean isSelf = clientContext != null && clientContext.getUserId() == userId;
            if (!isSelf) {
                resultUtil2 = this.userHandler.getUserInfo(userId, clientContext);
            } else {
                resultUtil2 = this.userHandler.getSelfInfo(userId, clientContext);
            }
            resultUtil.setCode(resultUtil2.getCode());
            if (ErrorCode.SUCCESS == resultUtil.getCode()) {
                com.paojiao.user.api.bean.UserInfoBean bean = resultUtil2.getDataInfo();
                if (null == bean) {
                    resultUtil.setCode(UserErrorCode.USER_NO_EXISTS_ERROR);
                    return resultUtil;
                }
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setLoginId(bean.getLoginId());
                userInfoBean.setUserId(bean.getUserId());
                userInfoBean.setSurfing(bean.getSurfing());
                userInfoBean.setUserType(bean.getUserType());
                userInfoBean.setNickName(bean.getNickName());
                userInfoBean.setSex(bean.getSex());
                userInfoBean.setUserDesc(StringUtil.isNull(bean.getUserDesc()) ? "" : bean.getUserDesc());
                if (isSelf) {
                    userInfoBean.setHeadPic(bean.getOldHeadPic());
                    userInfoBean.setNewHeadPic(bean.getHeadPic());
                    userInfoBean.setHeadPicState(bean.getHeadVerifyState());
                } else {
                    userInfoBean.setHeadPic(bean.getOldHeadPic());
                    userInfoBean.setNewHeadPic(null);
                }

                userInfoBean.setBirthday(bean.getBirthday());

                userInfoBean.setGisX(StringUtil.isBlank(bean.getGisX()) ? null : new BigDecimal(bean.getGisX()));
                userInfoBean.setGisY(StringUtil.isBlank(bean.getGisY()) ? null : new BigDecimal(bean.getGisY()));
                userInfoBean.setHometown(StringUtil.isNull(bean.getHometown()) ? "" : bean.getHometown());
                userInfoBean.setPresentAddress(StringUtil.isNull(bean.getPresentAddress()) ? "" : bean.getPresentAddress());
                userInfoBean.setRelationshipStatus(bean.getRelationshipStatus());
                userInfoBean.setProfessional(StringUtil.isNull(bean.getProfessional()) ? "" : bean.getProfessional());
                userInfoBean.setSchool(StringUtil.isNull(bean.getSchool()) ? "" : bean.getSchool());
                userInfoBean.setRegionId(bean.getRegionId());
                userInfoBean.setAudioIntroduceUrl(StringUtil.isNull(bean.getAudioIntroduceUrl()) ? "" : bean.getAudioIntroduceUrl());
                userInfoBean.setVideoIntroduceUrl(StringUtil.isNull(bean.getVideoIntroduceUrl()) ? "" : bean.getVideoIntroduceUrl());
                userInfoBean.setLanguageType(bean.getLanguageType());
                userInfoBean.setObjective(bean.getObjective());
                userInfoBean.setReligion(bean.getReligion());
                userInfoBean.setCreateTime(bean.getCreateTime());
                userInfoBean.setFoodHabit(bean.getFoodHabit());
                userInfoBean.setIntegrity(bean.getIntegrity());
                userInfoBean.setWeight(bean.getWeight());
                userInfoBean.setHeight(bean.getHeight());
                resultUtil.setDataInfo(userInfoBean);
            }
            return resultUtil;
        } catch (Exception e) {
            throw new FissionException(e);
        }
    }

    /**
     * 更新用户属性
     *
     * @param clientContext
     * @param userAttr
     * @param init
     * @return
     */
    private ResultUtil<Object> updateUserAttr(ClientContext clientContext, String userAttr, boolean init) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        try {
            int selfUserId = clientContext.getUserId();
            if (init) {
                int code = com.paojiao.user.util.ConstUtil.CheckUserInfoUtil.checkInit(this.getUserInfoBean(clientContext, selfUserId).getDataInfo());
                if (UserErrorCode.USER_NO_INIT_ERROR != code) {
                    resultUtil.setCode(UserErrorCode.USER_HAVE_INIT_ERROR);
                    return resultUtil;
                }
            }
            this.updateUserService.updateUserAttr(selfUserId, userAttr, init, clientContext);
            resultUtil = ResultUtil.build(ErrorCode.SUCCESS);
            if (init) {
                ResultUtil<UserInviteCodeBean> resultUtil1 = this.userHandler.getInviteRegisterUser(selfUserId, clientContext);
                if (resultUtil1.getDataInfo() == null) {
                    //非邀请注册用户
                    Map<String, Object> event = new HashMap<>();
                    event.put(RouteFieldNames.USER_ID, selfUserId);
                    event.put(RouteFieldNames.TRIGGER_TIME, System.currentTimeMillis());
                    this.rabbit.convertAndSend(RouteEventNames.USER_REGISTER, event);
                }
            } else {
                com.paojiao.user.api.bean.UserInfoBean dataInfo = this.userHandler.getSelfInfo(selfUserId, clientContext).getDataInfo();
                resultUtil.setDataInfo(dataInfo.getIntegrity());
            }
        } catch (KeywordException e) {
            resultUtil.setDataInfo(e.getKeyworld());
            resultUtil.setCode(e.getCode());
        } catch (FissionCodeException e) {
            resultUtil.setCode(e.getCode());
        } catch (Exception e) {
            String message;
            if (init) {
                message = String.format("initUserAttr error.param(userAttr:%s,context:%s)", JsonUtil.objToJsonString(userAttr), JsonUtil.objToJsonString(clientContext));
                resultUtil.setCode(UserErrorCode.INIT_USER_ATTR_ERROR);
            } else {
                message = String.format("updateUserAttr error.param(userAttr:%s,context:%s)", JsonUtil.objToJsonString(userAttr), JsonUtil.objToJsonString(clientContext));
                resultUtil.setCode(UserErrorCode.UPDATE_USER_ATTR_ERROR);
            }
            UserController.LOGGER.error(message, e);
        } finally {
            return resultUtil;
        }
    }

    /**
     * 文件上传
     *
     * @param clientContext
     * @param multipartFile
     * @param duration      时长
     * @param userAttrName
     * @return
     */
    private ResultUtil<Object> uploadIntroduce(ClientContext clientContext, MultipartFile multipartFile, long duration, String userAttrName) {
        if (StringUtil.isBlank(userAttrName)) {
            return ResultUtil.build(UserErrorCode.USER_ERROR);
        }
        String url = null;
        if (null != multipartFile && duration > 0) {
            ResultUtil<String> resultUtil = this.uploadIntroduce(multipartFile, 0L);
            if (resultUtil.getCode() == ErrorCode.SUCCESS) {
                url = resultUtil.getDataInfo();
            } else {
                return ResultUtil.build(resultUtil.getCode());
            }
        }
        Map<String, String> userAttr = new HashMap<>();
        if (StringUtil.isNotBlank(url)) {
            Map<String, Object> map = new HashMap<>();
            map.put("url", url);
            map.put("duration", duration);
            userAttr.put(userAttrName, JsonUtil.objToJsonString(map));
        } else {
            userAttr.put(userAttrName, "");
        }
        return this.updateUserAttr(clientContext, JsonUtil.objToJsonString(userAttr), false);
    }

    /**
     * 文件上传
     *
     * @param multipartFile
     * @return
     */
    private ResultUtil<String> uploadIntroduce(MultipartFile multipartFile, long maxSize) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int readLen = 0;
            int totalLen = 0;
            InputStream inputStream = multipartFile.getInputStream();
            while ((readLen = inputStream.read(buffer)) > 0) {
                totalLen += readLen;
                arrayOutputStream.write(buffer, 0, readLen);
                if (maxSize > 0 && maxSize < totalLen) {
                    return ResultUtil.build(UserErrorCode.PIC_TOO_LONG_ERROR);
                }
            }
            buffer = arrayOutputStream.toByteArray();
            String path = this.fastDfsTemplate.upload(multipartFile.getOriginalFilename(), buffer);
            return ResultUtil.build(ErrorCode.SUCCESS, path);
        } catch (Exception e) {
            LOGGER.error("uploadIntroduce resource error.", e);
            return ResultUtil.build(ErrorCode.SERVER_ERROR);
        } finally {
            try {
                arrayOutputStream.close();
            } catch (Exception e) {
                LOGGER.error("close outputstream fail.", e);
            }
        }
    }
}




