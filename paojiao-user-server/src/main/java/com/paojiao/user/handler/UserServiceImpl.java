package com.paojiao.user.handler;

import com.fission.next.common.bean.ClientContext;
import com.fission.next.common.constant.RouteEventNames;
import com.fission.next.common.constant.RouteFieldNames;
import com.fission.next.common.error.FissionException;
import com.fission.next.common.transport.bean.TokenResult;
import com.fission.next.common.transport.service.TokenService;
import com.fission.next.utils.ErrorCode;
import com.fission.utils.bean.ResultUtil;
import com.fission.utils.tool.ArrayUtils;
import com.fission.utils.tool.JsonUtil;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.bean.UserInfoBean;
import com.paojiao.user.api.bean.UserInviteCodeBean;
import com.paojiao.user.api.services.IUserService;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.api.util.UserErrorCode;
import com.paojiao.user.config.ApplicationConfig;
import com.paojiao.user.service.IUserCacheService;
import com.paojiao.user.service.IUserPicService;
import com.paojiao.user.service.bean.UserInfo;
import com.paojiao.user.service.bean.UserPicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Named("rpc_UserServiceImpl")
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private com.paojiao.user.service.IUserService userService;

    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    private RabbitTemplate rabbit;
    @Inject
    private TokenService tokenService;
    @Inject
    private IUserCacheService userCacheService;

    @Inject
    private IUserPicService userPicService;

    @Inject
    @Named(com.paojiao.user.util.ConstUtil.NameUtil.REDIS_USER)
    private RedisTemplate redis;

    @Override
    public ResultUtil<String> getUserToken(int userId, ClientContext clientContext) {
        ResultUtil<String> resultUtil = new ResultUtil<>();
        try {
            UserInfo userInfo = this.userService.getUserInfo(userId);
            if (null == userInfo) {
                resultUtil.setCode(UserErrorCode.USER_NO_EXISTS_ERROR);
                return resultUtil;
            }
            TokenResult userGetTokenResult = tokenService.getToken(userInfo.getUserId(), userInfo.getNickName(), userInfo.getHeadPic());
            if (userGetTokenResult.getCode() != 200) {
                throw new FissionException("getToken fail.userId:" + userInfo.getUserId() + ",code" + userGetTokenResult.getCode() + ",message:" + userGetTokenResult.getErrorMessage());
            }
            String token = userGetTokenResult.getToken();
            if (StringUtil.isBlank(token)) {
                resultUtil.setCode(UserErrorCode.USER_ERROR);
                return resultUtil;
            }
            resultUtil.setDataInfo(token);
            resultUtil.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            String message = String.format("getUserToken error.param(userId:%s,context:%s)", userId, JsonUtil.objToJsonString(clientContext));
            UserServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    @Override
    public ResultUtil<String> getUserInviteCode(int userId, int shareType, ClientContext clientContext) {
        ResultUtil<String> resultUtil = new ResultUtil<>();
        try {
            String inviteCode = this.userService.addUserInviteInfo(userId, shareType);
            if (StringUtil.isBlank(inviteCode)) {
                resultUtil.setCode(ErrorCode.SERVER_ERROR);
                return resultUtil;
            }
            resultUtil.setCode(ErrorCode.SUCCESS);
            resultUtil.setDataInfo(inviteCode);
        } catch (Exception e) {
            String message = String.format("getUserInviteAddress error.param(userId:%s,context:%s)", userId, JsonUtil.objToJsonString(clientContext));
            UserServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    @Override
    public ResultUtil<UserInviteCodeBean> getInviteRegisterUser(int userId, ClientContext clientContext) {
        ResultUtil<UserInviteCodeBean> resultUtil = new ResultUtil<>();
        try {
            UserInviteCodeBean inviteCodeBean = this.userService.getUserRegisterInviteInfo(userId);
            resultUtil.setDataInfo(inviteCodeBean);
            resultUtil.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            String message = String.format("getInviteUser error.param(userId:%s,context:%s)", userId, JsonUtil.objToJsonString(clientContext));
            UserServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    @Override
    public ResultUtil<Void> updateUserInviteCode(int userId, String inviteCode, short inviteType, ClientContext clientContext) {
        ResultUtil<Void> resultUtil = new ResultUtil<>();
        try {
            if (StringUtil.isBlank(inviteCode)) {
                resultUtil.setCode(ErrorCode.SUCCESS);
                return resultUtil;
            }
            this.userService.updateUserInviteInfo(userId, inviteType, inviteCode);
            resultUtil.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            String message = String.format("updateUserInviteCode error.param(userId:%s,inviteCode:%s,inviteType:%s,context:%s)", userId, inviteCode, inviteType, JsonUtil.objToJsonString(clientContext));
            UserServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    private List<UserInfoBean> initUserInfoBeans(List<UserInfo> userInfos) {
        if (ArrayUtils.isNullOrEmpty(userInfos)) {
            return new ArrayList<>();
        }
        List<Integer> userIds = userInfos.stream().filter(t -> t != null).map(UserInfo::getUserId).collect(Collectors.toList());
        List<Integer> onlineUsers = this.userCacheService.getOnlineUsers(userIds);
        return userInfos.stream().filter(t -> t != null).map(userInfo ->
                initUserInfoBean(userInfo, onlineUsers.contains(userInfo.getUserId()), false)
        ).collect(Collectors.toList());
    }

    private UserInfoBean initUserInfoBean(UserInfo userInfo, boolean isOnline, boolean resetIntegrity) {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setUserId(userInfo.getUserId());
        userInfoBean.setSurfing(userInfo.getSurfing());
        userInfoBean.setLoginId(userInfo.getLoginId());
        userInfoBean.setUserType(userInfo.getUserType());
        userInfoBean.setNickName(userInfo.getNickName());
        userInfoBean.setSex(userInfo.getSex());
        userInfoBean.setUserDesc(userInfo.getUserDesc());
        userInfoBean.setHeadPic(userInfo.getHeadPic());
        UserPicInfo userPicInfo = this.userPicService.getUserHeadPicInfo(userInfo.getUserId());
        if (null != userPicInfo) {
            userInfoBean.setHeadPic(userPicInfo.getPicUrl());
            userInfoBean.setOldHeadPic(userPicInfo.getOldPicUrl());
            userInfoBean.setHeadVerifyState(userPicInfo.getVerifyState());
        }
        userInfoBean.setBirthday(userInfo.getBirthday());
        userInfoBean.setGisX(null == userInfo.getGisX() ? null : userInfo.getGisX().toString());
        userInfoBean.setGisY(null == userInfo.getGisY() ? null : userInfo.getGisY().toString());
        userInfoBean.setHometown(userInfo.getHometown());
        userInfoBean.setPresentAddress(userInfo.getPresentAddress());
        userInfoBean.setRelationshipStatus(userInfo.getRelationshipStatus());
        userInfoBean.setProfessional(userInfo.getProfessional());
        userInfoBean.setSchool(userInfo.getSchool());
        userInfoBean.setRegionId(userInfo.getRegionId());
        userInfoBean.setAudioIntroduceUrl(userInfo.getAudioIntroduceUrl());
        userInfoBean.setVideoIntroduceUrl(userInfo.getVideoIntroduceUrl());
        userInfoBean.setLanguageType(userInfo.getLanguageType());
        userInfoBean.setObjective(userInfo.getObjective());
        userInfoBean.setReligion(userInfo.getReligion());
        userInfoBean.setFoodHabit(userInfo.getFoodHabit());
        userInfoBean.setWeight(userInfo.getWeight());
        userInfoBean.setHeight(userInfo.getHeight());
        userInfoBean.setCreateTime(userInfo.getCreateTime());
        int integrity = this.applicationConfig.getUserAttrIntegrity();
        if (integrity > 0) {
            int integrityTemp = userInfo.getIntegrity();
            if (integrityTemp < 1) {
                if (resetIntegrity) {
                    integrityTemp = this.userService.resetUserIntegrity(userInfo.getUserId(), 0, resetIntegrity);
//					this.userTaskService.addResetIntegrityUser(userInfo.getUserId());
                }
            }
            userInfoBean.setIntegrity(Math.round(integrityTemp * 100.0f / integrity));
        } else {
            userInfo.setIntegrity(100);
        }
        userInfoBean.setLastActiveTime(userInfo.getLastActiveTime());
        userInfoBean.setOnline(isOnline);
        return userInfoBean;
    }

    private UserInfoBean initUserInfoBean(UserInfo userInfo, ClientContext clientContext) {
        if (null == userInfo) {
            return null;
        }
        ResultUtil<Boolean> onlineResultUtil = this.userIdOnline(userInfo.getUserId(), clientContext);
        boolean isOnline = onlineResultUtil.getCode() == ErrorCode.SUCCESS && onlineResultUtil.getDataInfo();
        return this.initUserInfoBean(userInfo, isOnline, true);
    }

    @Override
    public ResultUtil<UserInfoBean> getUserInfo(int userId, ClientContext clientContext) {
        try {
            UserInfoBean userInfoBean = this.initUserInfoBean(this.userService.getUserInfo(userId), clientContext);
            if (null == userInfoBean) {
                return ResultUtil.build(UserErrorCode.USER_NO_EXISTS_ERROR);
            }
            return ResultUtil.build(ErrorCode.SUCCESS, userInfoBean);
        } catch (Exception e) {
            String message = String.format("getUserInfo error.param(userId:%s,context:%s)", userId, JsonUtil.objToJsonString(clientContext));
            UserServiceImpl.LOGGER.error(message, e);
            return ResultUtil.build(UserErrorCode.USER_ERROR);
        }
    }

    @Override
    public ResultUtil<UserInfoBean> getSelfInfo(int userId, ClientContext clientContext) {
        try {
            UserInfoBean userInfoBean = this.initUserInfoBean(this.userService.getSelfInfo(userId), clientContext);
            if (null == userInfoBean) {
                return ResultUtil.build(UserErrorCode.USER_NO_EXISTS_ERROR);
            }
            return ResultUtil.build(ErrorCode.SUCCESS, userInfoBean);
        } catch (Exception e) {
            String message = String.format("getSelfInfo error.param(userId:%s,context:%s)", userId, JsonUtil.objToJsonString(clientContext));
            UserServiceImpl.LOGGER.error(message, e);
            return ResultUtil.build(UserErrorCode.USER_ERROR);
        }
    }

    @Override
    public ResultUtil<Boolean> userIdOnline(int userId, ClientContext clientContext) {
        Long lastKeepTime = userCacheService.getUserLastOnlineKeepTime(userId);
        return ResultUtil.build(ErrorCode.SUCCESS, null != lastKeepTime && this.applicationConfig.isOnlineCheck(lastKeepTime));
    }

    @Override
    public ResultUtil<Integer> getUserId(short userType, int loginId, ClientContext clientContext) {
        ResultUtil<Integer> resultUtil = new ResultUtil<>();
        try {
            int userId = this.userService.initUserInfo(userType, loginId);
            List<Integer> userIds = new ArrayList<>();
            userIds.add(userId);
            resultUtil.setDataInfo(userId);
            resultUtil.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            String message = String.format("getUserId error.param(userType:%s,loginId:%s,context:%s)", userType, loginId, JsonUtil.objToJsonString(clientContext));
            UserServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    @Override
    public ResultUtil<Void> updateUserAttr(int userId, Map<Short, Object> updateUserAttr, ClientContext clientContext) {
        ResultUtil<Void> resultUtil = new ResultUtil<>();
        try {
            this.userService.updateUserAttr(userId, updateUserAttr);
            {
                Map<String, Object> event = new HashMap<>();
                for (Map.Entry<Short, Object> entry : updateUserAttr.entrySet()) {
                    String name = ConstUtil.UserAttrId.getUpateUserAttrEventName(entry.getKey());
                    if (StringUtil.isNotBlank(name)) {
                        event.put(name, entry.getValue());
                    }
                }
                event.put(RouteFieldNames.USER_ID, userId);
                this.rabbit.convertAndSend(RouteEventNames.UPDATE_USER_INFO, event);
            }
            if (updateUserAttr.containsKey(ConstUtil.UserAttrId.HEAD_PIC)) {
                Map<String, Object> dispatchData = new HashMap();
                dispatchData.put(RouteFieldNames.USER_ID, userId);
                dispatchData.put(RouteFieldNames.TRIGGER_TIME, System.currentTimeMillis());
                rabbit.convertAndSend(RouteEventNames.USER_INDEX_PIC_UPDATE, dispatchData);
            }
            resultUtil.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            String message = String.format("updateUserAttr error.param(userId:%s,updateUserAttr:%s,context:%s)", userId, JsonUtil.objToJsonString(updateUserAttr), JsonUtil.objToJsonString(clientContext));
            UserServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    @Override
    public ResultUtil<Void> setUserOnlineStatus(int userId, boolean online, ClientContext aDefault) {
        UserServiceImpl.LOGGER.debug("setUserOnlineStatus userId={},online={}", userId, online);
        userCacheService.updateUserOnline(userId, online);
        if (online) {
            String key = "user.online.state.update." + userId;
            if (this.redis.opsForValue().setIfAbsent(key, System.currentTimeMillis())) {
                this.redis.expire(key, 3 * 60 * 1000, TimeUnit.MILLISECONDS);
                Map<Short, Object> updateUserAttr = new HashMap();
                updateUserAttr.put(ConstUtil.UserAttrId.LAST_ACTIV_TIME, System.currentTimeMillis());
                this.userService.updateUserAttr(userId, updateUserAttr);
            }
        }
        return ResultUtil.build(ErrorCode.SUCCESS);
    }

    @Override
    public ResultUtil<Void> setUsersOnlineStatus(List<Integer> userIds, boolean online, ClientContext aDefault) {
        if (ArrayUtils.isNotEmpty(userIds)) {
            userIds.parallelStream().forEach((Integer userId) -> {
                if (null != userId) {
                    this.setUserOnlineStatus(userId, online, aDefault);
                }
            });
        }
        return ResultUtil.build(ErrorCode.SUCCESS);
    }
}




