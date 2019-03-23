package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.UserAttrInfoEntity;
import com.paojiao.user.data.db.entity.UserInfoEntity;
import com.paojiao.user.data.db.entity.UserInviteInfoEntity;

import java.sql.SQLException;
import java.util.List;

public interface IUserReadDao {

	UserInfoEntity getUserInfo(int userId) throws SQLException;

	List<UserAttrInfoEntity> listUserAttrInfo(int userId) throws SQLException;

	UserInviteInfoEntity getInviteUser(String inviteCode) throws SQLException;
}


