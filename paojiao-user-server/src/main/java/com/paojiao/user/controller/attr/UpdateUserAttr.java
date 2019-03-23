package com.paojiao.user.controller.attr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class UpdateUserAttr {

	private static final ConcurrentHashMap<Short, UpdateUserAttr> UPDATE_USER_ATTR_MAP = new ConcurrentHashMap<>();

	public UpdateUserAttr(short id) {
		UpdateUserAttr.UPDATE_USER_ATTR_MAP.put(id, this);
	}

	public static UpdateUserAttr getUpdateUserAttr(short id) {
		return UpdateUserAttr.UPDATE_USER_ATTR_MAP.get(id);
	}

	public abstract Map<Short,Object> getUpdateUserAttr(int userId, String data);
}



