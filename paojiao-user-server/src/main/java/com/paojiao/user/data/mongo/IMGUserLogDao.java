package com.paojiao.user.data.mongo;

import com.paojiao.user.data.mongo.entity.MGUserInviteUserInfoEntity;
import com.paojiao.user.data.mongo.entity.MGUserPicLogInfoEntity;

import java.util.List;

public interface IMGUserLogDao {

    void addUserInviteUserInfo(MGUserInviteUserInfoEntity userInviteUserInfoEntity);

    List<MGUserInviteUserInfoEntity> listUserInviteUserInfo(int userId, short inviteState, boolean isInvite);

    MGUserInviteUserInfoEntity getUserRegisterInviteInfo(int userId);

    boolean manageUserInviteState(String id);

    void addUserPicLogInfo(List<MGUserPicLogInfoEntity> MGUserPicLogInfoEntityList);
}
