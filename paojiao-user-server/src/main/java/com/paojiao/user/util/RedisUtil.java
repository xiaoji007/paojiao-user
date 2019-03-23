package com.paojiao.user.util;

import com.fission.redis.common.RedisDistributionLock;
import com.fission.utils.tool.StringUtil;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class RedisUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
	private static <H, R> R redisLock(RedisTemplate redis, H h, String redisKey, Function<H, R> function) {
		if (null == redis || null == h || null == function || StringUtil.isBlank(redisKey)) {
			return null;
		}
		RedisDistributionLock distributionLock = new RedisDistributionLock(redis, redisKey, 60 * 1000);
		try {
			distributionLock.lock();
			return function.apply(h);
		} finally {
			distributionLock.unlock();
		}
	}

	public static <R> R redisValueLocks(RedisTemplate redis, String redisKey, Function<ValueOperations, R> function) {
		ValueOperations valueOperations = redis.opsForValue();
		return RedisUtil.redisLock(redis, valueOperations, redisKey, function);
	}

	public static <R> R redisValueLocks(RedisTemplate redis, String redisKey, Callable<R> callable) {
		Function<Callable<R>, R> function = (Callable<R> call) -> {
			try {
				return call.call();
			} catch (Exception e) {
				RedisUtil.LOGGER.error("redisValueLocks redisKey error", e);
				return null;
			}
		};
		return RedisUtil.redisLock(redis, callable, redisKey, function);
	}

	public static void redisLock(RedisTemplate redis, String redisKey, Consumer<Void> consumer) {
		Function<Consumer<Void>, Void> function = (Consumer<Void> call) -> {
			try {
				call.accept((Void) null);
			} catch (Exception e) {
				RedisUtil.LOGGER.error("redisValueLocks redisKey error", e);
			}
			return (Void) null;
		};
		RedisUtil.redisLock(redis, consumer, redisKey, function);
	}

}
