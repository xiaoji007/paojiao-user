package com.paojiao.user.data.mongo;

import com.paojiao.user.data.mongo.entity.UserInviteUserInfoEntity;
import com.paojiao.user.data.mongo.entity.UserPicLogInfoEntity;

import java.util.List;

public interface IUserLogDao {

    void addUserInviteUserInfo(UserInviteUserInfoEntity userInviteUserInfoEntity);

    List<UserInviteUserInfoEntity> listUserInviteUserInfo(int userId, short inviteState, boolean isInvite);

    UserInviteUserInfoEntity getUserRegisterInviteInfo(int userId);

    boolean manageUserInviteState(String id);

    void addUserPicLogInfo(List<UserPicLogInfoEntity> userPicLogInfoEntityList);
}
