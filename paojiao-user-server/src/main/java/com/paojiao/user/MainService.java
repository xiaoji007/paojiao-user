package com.paojiao.user;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.fission.datasource.spring.EnableJdbcTemplate;
import com.fission.datasource.spring.Jdbc;
import com.fission.elasticsearch.spring.EnableElasticsearch;
import com.fission.fastdfs.spring.EnableFastDfs;
import com.fission.logging.spring.EnableLogging;
import com.fission.mongodb.spring.EnableMongo;
import com.fission.motan.spring.EnableMotanRpc;
import com.fission.next.common.transport.spring.EnableTransport;
import com.fission.next.common.web.spring.EnableWeb;
import com.fission.perfmon.spring.EnablePerfmon;
import com.fission.rabbit.spring.EnableRabbit;
import com.fission.redis.spring.EnableRedis;
import com.fission.utils.tool.ApplicationMain;
import com.paojiao.user.util.ConstUtil;
import com.paojiao.user.util.RedisKeyUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.inject.Named;
import java.util.Arrays;


@EnableScheduling
@EnableRabbit
@EnablePerfmon
@EnableCaching
@EnableAspectJAutoProxy
@EnableJdbcTemplate(@Jdbc(db = ConstUtil.NameUtil.DB_USER))
@EnableRedis(ConstUtil.NameUtil.REDIS_USER)
@EnableMongo(ConstUtil.NameUtil.MONGO_USER)
@EnableTransport
@EnableMotanRpc
@EnableFastDfs
@EnableLogging
@EnableWeb
@EnableApolloConfig({"PIE.RabbitMQ", "PIE.Logging", "PIE.Transport", "PIE.Perfmon", "PIE.Mongodb", "PIE.Redis", "PIE.Database", "PIE.Fastdfs", "application"})
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class MainService {
    private static final int MINUTE = 60;
    private static final int HOUR = 60 * MINUTE;

    public static void main(String[] args) throws Exception {
        ApplicationMain.main(MainService.class, args);
    }

    @Bean
    public RedisCacheManager pet(@Named(ConstUtil.NameUtil.REDIS_USER) RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(HOUR);
        redisCacheManager.setCacheNames(Arrays.asList(RedisKeyUtil.CACHE_NAME));
        return redisCacheManager;
    }

    @Bean(name = ScheduledAnnotationBeanPostProcessor.DEFAULT_TASK_SCHEDULER_BEAN_NAME)
    ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("SCHEDULE-MATCH-");
        return threadPoolTaskScheduler;
    }
}


