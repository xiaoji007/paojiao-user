package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.UserAttrInfoEntity;
import com.paojiao.user.data.db.entity.UserInfoEntity;
import com.paojiao.user.data.db.entity.UserInviteInfoEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IUserWriteDao {

	int addUserInfo(UserInfoEntity userInfoEntity) throws SQLException;

	void updateUserInfo(int userId, Map<String, Object> updateAttr) throws SQLException;

	void addUserAttrInfo(int userId, List<UserAttrInfoEntity> userAttrInfoEntitys) throws SQLException;

	void addUserInviteInfo(UserInviteInfoEntity userInviteInfoEntity) throws SQLException;

	boolean subUserInviteNum(String inviteCode) throws SQLException;
}


