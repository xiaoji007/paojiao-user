package com.paojiao.user.handler;

import com.fission.next.common.bean.ClientContext;
import com.fission.task.util.TaskDataUtil;
import com.fission.utils.bean.ResultUtil;
import com.fission.utils.tool.ArrayUtils;
import com.fission.utils.tool.ErrorCode;
import com.fission.utils.tool.JsonUtil;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.api.bean.UserPicInfoBean;
import com.paojiao.user.api.services.IUserPicService;
import com.paojiao.user.api.util.UserErrorCode;
import com.paojiao.user.data.mongo.entity.MGUserPicLogInfoEntity;
import com.paojiao.user.service.bean.UserPicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Named("rpc_UserPicServiceImpl")
public class UserPicServiceImpl implements IUserPicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPicServiceImpl.class);

    @Inject
    private com.paojiao.user.service.IUserPicService userPicService;

    @Override
    public ResultUtil<Void> addUserPicInfo(int userId, int index, String pic, ClientContext clientContext) {
        ResultUtil<Void> resultUtil = new ResultUtil<>();
        try {
            if (StringUtil.isBlank(pic) || index < 1) {
                resultUtil.setCode(ErrorCode.PARAM_ERROR);
                return resultUtil;
            }
            this.userPicService.addUserPicInfo(userId, index, pic);

            //上传日志
            MGUserPicLogInfoEntity mgUserPicLogInfoEntity = new MGUserPicLogInfoEntity();
            mgUserPicLogInfoEntity.setCreateTime(new Date());
            mgUserPicLogInfoEntity.setIndex(index);
            mgUserPicLogInfoEntity.setPic(pic);
            mgUserPicLogInfoEntity.setUserId(userId);
            TaskDataUtil.addTaskDataInfo(mgUserPicLogInfoEntity);
        } catch (Exception e) {
            String message = String.format("addUserPicInfo error.param(userId:%s,index:%s,pic:%s,context:%s)", userId, index, pic, JsonUtil.objToJsonString(clientContext));
            UserPicServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    @Override
    public ResultUtil<Void> moveUserPic(int userId, int index, int toIndex, ClientContext clientContext) {
        ResultUtil<Void> resultUtil = new ResultUtil<>();
        try {
            if (index == toIndex && index > 0) {
                resultUtil.setCode(ErrorCode.SUCCESS);
                return resultUtil;
            }
            this.userPicService.moveUserPic(userId, index, toIndex);
        } catch (Exception e) {
            String message = String.format("moveUserPic error.param(userId:%s,index:%s,toIndex:%s,context:%s)", userId, index, toIndex, JsonUtil.objToJsonString(clientContext));
            UserPicServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    @Override
    public ResultUtil<Void> subUserIndexPicInfo(int userId, List<Integer> userPicIds, ClientContext clientContext) {
        ResultUtil<Void> resultUtil = new ResultUtil<>();
        try {
            if (ArrayUtils.isNullOrEmpty(userPicIds)) {
                resultUtil.setCode(ErrorCode.SUCCESS);
                return resultUtil;
            }
            this.userPicService.subUserIndexPicInfo(userId, userPicIds);
        } catch (Exception e) {
            String message = String.format("subUserIndexPicInfo error.param(userId:%s,userPicIds:%s,context:%s)", userId, JsonUtil.objToJsonString(userPicIds), JsonUtil.objToJsonString(clientContext));
            UserPicServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }

    @Override
    public ResultUtil<List<UserPicInfoBean>> listUserAllPicInfo(int userId, ClientContext clientContext) {
        ResultUtil<List<UserPicInfoBean>> resultUtil = new ResultUtil<>();
        try {
            List<UserPicInfo> userPicInfoList = this.userPicService.listUserAllPicInfo(userId, userId == clientContext.getUserId());
            if (ArrayUtils.isNotEmpty(userPicInfoList)) {
                List<UserPicInfoBean> userPicInfoBeanList = new ArrayList<>();
                userPicInfoList.forEach((UserPicInfo userPicInfo) -> {
                    if (null == userPicInfo) {
                        return;
                    }
                    UserPicInfoBean userPicInfoBean = new UserPicInfoBean();
                    userPicInfoBean.setUserPicId(userPicInfo.getUserPicId());
                    userPicInfoBean.setUserId(userPicInfo.getUserId());
                    userPicInfoBean.setIndex(userPicInfo.getIndex());
                    userPicInfoBean.setOldPicUrl(userPicInfo.getOldPicUrl());
                    userPicInfoBean.setVerifyState(userPicInfo.getVerifyState());
                    userPicInfoBean.setCreateTime(userPicInfo.getCreateTime());
                    userPicInfoBeanList.add(userPicInfoBean);
                });
            }
            resultUtil.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            String message = String.format("listUserAllPicInfo error.param(userId:%s,context:%s)", userId, JsonUtil.objToJsonString(clientContext));
            UserPicServiceImpl.LOGGER.error(message, e);
            resultUtil.setCode(UserErrorCode.USER_ERROR);
        }
        return resultUtil;
    }
}
