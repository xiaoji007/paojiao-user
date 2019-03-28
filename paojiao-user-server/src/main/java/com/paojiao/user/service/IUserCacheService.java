package com.paojiao.user.service;

import java.util.List;

public interface IUserCacheService {

    Long getUserLastOnlineKeepTime(int userId);

    List<Integer> getOnlineUsers(List<Integer> userIds);

    List<Integer> listOnlineUserIds();

    void updateUserOnline(int userId, boolean online);

    boolean isOnline(int userId);
}


