package com.paojiao.user.service.impl;

import com.fission.next.common.constant.RouteEventNames;
import com.fission.next.common.constant.RouteFieldNames;
import com.paojiao.user.config.ApplicationConfig;
import com.paojiao.user.service.IUserCacheService;
import com.paojiao.user.service.bean.UserActiveInfoBean;
import com.paojiao.user.util.ConstUtil;
import com.paojiao.user.util.RedisKeyUtil;
import com.paojiao.user.util.RedisUtil;
import com.fission.utils.tool.ArrayUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserCacheServiceImpl implements IUserCacheService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserCacheServiceImpl.class);


	@Inject
	@Named(ConstUtil.NameUtil.REDIS_USER)
	private RedisTemplate redis;

	@Inject
	private RabbitTemplate rabbit;

	@Inject
	private ApplicationConfig applicationConfig;


	@Override
	public Long getUserLastOnlineKeepTime(int userId) {
		Long lastKeepTime = (Long) redis.opsForHash().get(RedisKeyUtil.getUserOnlineKey(), userId);
		return lastKeepTime;
	}

	@Override
	public List<Integer> getOnlineUsers(List<Integer> userIds) {
		List<Integer> onlineUserIds = new ArrayList<>();
		if (ArrayUtils.isNotEmpty(userIds)) {
			List<Long> times = redis.opsForHash().multiGet(RedisKeyUtil.getUserOnlineKey(), userIds);
			int size = userIds.size();
			for (int i = 0; i < size; i++) {
				Long time = times.get(i);
				if (time != null && applicationConfig.isOnlineCheck(time)) {
					onlineUserIds.add(userIds.get(i));
				}
			}
		}
		return onlineUserIds;
	}

	@Override
	public List<Integer> listOnlineUserIds() {
		List<Integer> userIds = new ArrayList<>();
		Cursor<Map.Entry<Integer, Long>> cursor = null;
		try {
			cursor = redis.opsForHash().scan(RedisKeyUtil.getUserOnlineKey(), ScanOptions.scanOptions().count(100).build());
			while (cursor.hasNext()) {
				Map.Entry<Integer, Long> entry = cursor.next();
				userIds.add(entry.getKey());
			}
		} finally {
			if (cursor != null) {
				try {
					cursor.close();
				} catch (Exception err) {

				}
			}
		}
		return userIds;
	}

	@Override
	public void updateUserOnline(int userId, boolean online) {
		RedisUtil.redisLock(redis, RedisKeyUtil.getUserOnlineLockKey(userId), (Void v) -> {
			Long lastTime = (Long) redis.opsForHash().get(RedisKeyUtil.getUserOnlineKey(), userId);
			if (online) {
				redis.opsForHash().put(RedisKeyUtil.getUserOnlineKey(), userId, System.currentTimeMillis());
				if (lastTime == null || !applicationConfig.isOnlineCheck(lastTime)) {
					onlineEvent(userId, online);
				}
			} else {
				redis.opsForHash().delete(RedisKeyUtil.getUserOnlineKey(), userId);
				if (lastTime != null) {
					onlineEvent(userId, online);
				}
			}
		});
	}

	@Override
	public void updateUserActive(int userId, short userType) {
		String key = RedisKeyUtil.getUserKeepKey(userId);
		UserActiveInfoBean userActiveInfoBean = (UserActiveInfoBean) redis.opsForValue().get(key);
		if (userActiveInfoBean == null) {
			userActiveInfoBean = new UserActiveInfoBean();
			userActiveInfoBean.setUserId(userId);
		}
		userActiveInfoBean.setUserType(userType);
		userActiveInfoBean.setLastActiveTime(System.currentTimeMillis());
		redis.opsForValue().set(key, userActiveInfoBean, 7, TimeUnit.DAYS);
	}

	@Override
	public Map<Integer, UserActiveInfoBean> batchUserActive(List<Integer> userIdLists) {
		Map<Integer, UserActiveInfoBean> map = new HashMap<>(userIdLists.size());
		List<List<Integer>> tmpList = Lists.partition(userIdLists, 100);
		for (List<Integer> userIds : tmpList) {
			int size = userIds.size();
			List<String> keys = userIds.stream().map(RedisKeyUtil::getUserKeepKey).collect(Collectors.toList());
			List<UserActiveInfoBean> userActiveInfoBeans = redis.opsForValue().multiGet(keys);
			for (int i = 0; i < size; i++) {
				UserActiveInfoBean userActiveInfoBean = userActiveInfoBeans.get(i);
				if (userActiveInfoBean != null) {
					int userId = userIds.get(i);
					synchronized (map) {
						map.put(userId, userActiveInfoBean);
					}
				}
			}
		}

		return map;
	}

	@Override
	public UserActiveInfoBean getUserActive(Integer userId) {
		String key = RedisKeyUtil.getUserKeepKey(userId);
		return (UserActiveInfoBean) redis.opsForValue().get(key);
	}

	@Override
	public boolean isOnline(int userId) {
		Long time = this.getUserLastOnlineKeepTime(userId);
		if (null == time) {
			return false;
		}
		return applicationConfig.isOnlineCheck(time);
	}

	private void onlineEvent(int userId, boolean online) {
		Map<String, Object> map = new HashMap<>();
		map.put(RouteFieldNames.USER_ID, userId);
		map.put(RouteFieldNames.ONLINE_STATE, online);
		rabbit.convertAndSend(RouteEventNames.UPDATE_USER_ONLINE_STATE, map);
	}

}


