package com.paojiao.user.util;

public class RedisKeyUtil {

    public static final String CACHE_NAME = "REDIS_USER_CACHE";



    public static String getUserOnlineKey() {
        return "paojiao.user.online.map";
    }

    public static String getUserOnlineLockKey(int userId) {
        return "paojiao.user.online.lock_" + userId;
    }
}
