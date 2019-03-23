package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.UserPicInfoEntity;

import java.sql.SQLException;
import java.util.List;

public interface IUserPicReadDao {

	List<UserPicInfoEntity> listAllUserPicInfo(int userId) throws SQLException;

	List<UserPicInfoEntity> listResetAllUserPicInfo(int userId) throws SQLException;
}


