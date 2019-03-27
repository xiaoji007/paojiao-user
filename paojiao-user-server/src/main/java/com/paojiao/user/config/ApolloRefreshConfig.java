package com.paojiao.user.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Set;

@Component
public class ApolloRefreshConfig {
	private static final Logger logger = LoggerFactory.getLogger(ApolloRefreshConfig.class);
	@Inject
	private RefreshScope refreshScope;

	@ApolloConfigChangeListener
	public void onChange(ConfigChangeEvent changeEvent) {
		Set<String> changedKeys = changeEvent.changedKeys();
		logger.info("change spring holder scope keys:{}", changedKeys.toString());
		refreshScope.refreshAll();
	}
}
