package com.paojiao.user.util;

public class RedisKeyUtil {

	public static final String CACHE_NAME = "REDIS_USER_CACHE";
	public static final String USER_INFO = "'user.info.v2.'+#userId";
	public static final String USER_DB_ATTR_INFO = "'user.info.db.attr.'+#userId";
	public static final String USER_DB_INFO = "'user.info.db.'+#userId";

	public static final String NICK = "user.nick.keyworld";
	public static final String NICK_ASCII = "user.nick.ascii.keyworld";

	public static String getUserOnlineKey() {
		return "paojiao.user.online.map";
	}

	public static String getUserOnlineLockKey(int userId) {
		return "paojiao.user.online.lock_" + userId;
	}

	public static String getUserKeepKey(int userId) {
		return "paojiao.user.keep." + userId;
	}
}
