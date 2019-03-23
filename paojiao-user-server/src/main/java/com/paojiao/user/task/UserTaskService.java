package com.paojiao.user.task;

import com.fission.next.common.constant.RouteEventNames;
import com.fission.next.common.constant.RouteFieldNames;
import com.paojiao.user.api.util.ConstUtil;
import com.paojiao.user.service.IUserService;
import com.paojiao.user.service.bean.UserInviteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Service
public class UserTaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTaskService.class);

    private final LinkedBlockingDeque<Integer> inviteUser = new LinkedBlockingDeque<>();

    @Inject
    private IUserService userService;

    @Inject
    private RabbitTemplate rabbit;

    public void addInviteUser(int userId) {
        if (!this.inviteUser.contains(userId)) {
            this.inviteUser.add(userId);
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void inviteUserTask() {
        while (true) {
            if (this.inviteUser.isEmpty()) {
                return;
            }
            Integer userId = null;
            try {
                userId = this.inviteUser.poll(1, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (null != userId) {
                try {
                    List<UserInviteInfo> userInviteInfos = this.userService.listUserInviteInfo(userId);
                    if (null != userInviteInfos && !userInviteInfos.isEmpty()) {
                        userInviteInfos.forEach((UserInviteInfo userInviteInfo) -> {
                            if (null != userInviteInfo && userInviteInfo.getUserId() != userInviteInfo.getInviteUserId() && userInviteInfo.isInvite()) {
                                //邀请事件
                                if (ConstUtil.InviteType.REGISTER == userInviteInfo.getInviteType()) {
                                    Map<String, Object> event = buildUserInviteEventData(userInviteInfo);
                                    this.rabbit.convertAndSend(RouteEventNames.USER_INVITE, event);
                                } else if (ConstUtil.InviteType.HAVE_REGISTER == userInviteInfo.getInviteType()) {
                                    Map<String, Object> event = buildUserInviteEventData(userInviteInfo);
                                    this.rabbit.convertAndSend(RouteEventNames.USER_INVITE_EXISTS, event);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    UserTaskService.LOGGER.error("clear inviteUserTask error.", e);
                }
            }
        }
    }


    private Map<String, Object> buildUserInviteEventData(UserInviteInfo userInviteInfo) {
        Map<String, Object> event = new HashMap<>();
        event.put(RouteFieldNames.USER_ID, userInviteInfo.getUserId());
        event.put(RouteFieldNames.TO_USER_ID, userInviteInfo.getInviteUserId());
        event.put(RouteFieldNames.SHARE_REGISTER_TYPE, userInviteInfo.getShareType());
        event.put(RouteFieldNames.SHARE_REGISTER_CODE, userInviteInfo.getInviteCode());
        event.put(RouteFieldNames.TRIGGER_TIME, System.currentTimeMillis());
        return event;
    }
}
