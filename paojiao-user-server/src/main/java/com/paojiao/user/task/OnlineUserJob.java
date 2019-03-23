package com.paojiao.user.task;

import com.paojiao.user.config.ApplicationConfig;
import com.paojiao.user.service.IUserCacheService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.spring.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@JobHandler(name = "OnlineUserJob")
@Service
public class OnlineUserJob extends IJobHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineUserJob.class);

    @Inject
    private IUserCacheService userCacheService;
    @Inject
    private ApplicationConfig applicationConfig;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        List<Integer> userIds = userCacheService.listOnlineUserIds();
        if (null == userIds || userIds.isEmpty()) {
            return ReturnT.SUCCESS;
        }
        userIds.stream().forEach((Integer userId) -> {
            if (null == userId) {
                return;
            }
            Long lastKeepTime = this.userCacheService.getUserLastOnlineKeepTime(userId);
            if (null != lastKeepTime && this.applicationConfig.isOnlineCheck(lastKeepTime)) {
                return;
            }
            this.userCacheService.updateUserOnline(userId, false);
            LOGGER.debug("recve {} online keepalive timeout.set online false", userId);

        });
        return ReturnT.SUCCESS;
    }

}
