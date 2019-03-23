package com.paojiao.user.service.impl;

import com.codahale.metrics.annotation.Gauge;
import com.fission.next.common.constant.RouteEventNames;
import com.fission.next.common.constant.RouteFieldNames;
import com.paojiao.user.service.IPerfmonService;
import com.paojiao.user.service.IUserCacheService;
import com.paojiao.user.service.bean.UserActiveInfoBean;
import com.paojiao.user.util.ConstUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerfmonServiceImpl implements IPerfmonService {

	@Inject
	private IUserCacheService userCacheService;
	@Inject
	private RabbitTemplate rabbit;

	@Gauge(name = "user.online.number", absolute = true)
	@Override
	public int getOnlineUserCount() {
		List<Integer> userIds = userCacheService.listOnlineUserIds();
		Map<Integer, UserActiveInfoBean> userActiveInfoBeanMap = userCacheService.batchUserActive(userIds);
		int csUserNumber = (int) userActiveInfoBeanMap.values().stream().filter(userActiveInfoBean -> com.paojiao.user.api.util.ConstUtil.UserType.CS == userActiveInfoBean.getUserType()).count();
		int commonUserNumber = userIds.size() - csUserNumber;

		Map<String, Object> map = new HashMap<>(4);
		map.put(RouteFieldNames.NUMBER, userIds.size());
		map.put(RouteFieldNames.REAL_USER_NUMBER, commonUserNumber);
		try {
			map.put(RouteFieldNames.COMPUTER_NAME, InetAddress.getLocalHost().getHostName());
		} catch (Exception err) {

		}
		map.put(RouteFieldNames.TRIGGER_TIME, System.currentTimeMillis());
		rabbit.convertAndSend(RouteEventNames.PLATFORM_USER_NUMBER, map);
		return commonUserNumber;
	}
}
