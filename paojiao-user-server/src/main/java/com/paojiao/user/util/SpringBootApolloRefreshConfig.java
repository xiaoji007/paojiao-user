package com.paojiao.user.util;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author chenjun
 */
@Component
@Slf4j
public class SpringBootApolloRefreshConfig {
	@Autowired
	private RefreshScope refreshScope;

	@ApolloConfigChangeListener
	public void onChange(ConfigChangeEvent changeEvent) {
		Set<String> changedKeys = changeEvent.changedKeys();
		refreshScope.refreshAll();
	}
}
