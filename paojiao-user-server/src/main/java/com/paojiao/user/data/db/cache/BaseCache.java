package com.paojiao.user.data.db.cache;

import com.fission.cache.FissionCache;
import com.fission.cache.util.CacheUtil;

public abstract class BaseCache implements FissionCache {

	public BaseCache() {
		CacheUtil.addFissionCache(this);
	}
}
