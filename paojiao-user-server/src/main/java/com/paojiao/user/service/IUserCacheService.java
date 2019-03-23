package com.paojiao.user.service;

import com.paojiao.user.service.bean.UserActiveInfoBean;

import java.util.List;
import java.util.Map;

public interface IUserCacheService {

	Long getUserLastOnlineKeepTime(int userId);

	List<Integer> getOnlineUsers(List<Integer> userIds);

	List<Integer> listOnlineUserIds();

	void updateUserOnline(int userId, boolean online);

	void updateUserActive(int userId, short userType);

	Map<Integer, UserActiveInfoBean> batchUserActive(List<Integer> userIds);

	UserActiveInfoBean getUserActive(Integer userId);

	boolean isOnline(int userId);
}


