package com.paojiao.user.data.db;

import com.paojiao.user.data.db.entity.UserPicInfoEntity;

import java.sql.SQLException;
import java.util.List;

public interface IUserPicWriteDao {

	/**
	 * 添加用户首页照片
	 *
	 * @param userPicInfoEntity
	 * @return
	 * @throws SQLException
	 */
	int addUserPicInfo(UserPicInfoEntity userPicInfoEntity) throws SQLException;

	/**
	 * 添加用户首页照片
	 *
	 * @param userPicInfoEntitys
	 * @return
	 * @throws SQLException
	 */
	void updateUserPicInfo(List<UserPicInfoEntity> userPicInfoEntitys) throws SQLException;

	/**
	 *
	 * @param userId
	 * @param userPicId
	 * @throws SQLException
	 */
	void refuseUserPicInfo(int userId, int userPicId) throws SQLException;

	/**
	 *
	 * @param userId
	 * @param userPicId
	 * @throws SQLException
	 */
	void passUserPicInfo(int userId, int userPicId) throws SQLException;

	/**
	 * 删除用户首页照片
	 *
	 * @param userId
	 * @param userPicIds
	 * @throws SQLException
	 */
	void removeUserPicInfo(int userId, List<Integer> userPicIds) throws SQLException;

	/**
	 * 删除用户首页照片
	 *
	 * @param userId
	 * @param minPicNum
	 * @throws SQLException
	 */
	void removeUserPicInfo(int userId, int minPicNum) throws SQLException;
}


