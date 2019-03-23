package com.paojiao.user.event;

import com.fission.next.common.constant.RouteEventNames;
import com.fission.rabbit.spring.RabbitEvent;
import com.paojiao.user.controller.attr.init.UpdateUserNickName;
import com.paojiao.user.service.IKeyworldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

@Service
public class UserEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEvent.class);

    @Inject
    private IKeyworldService keyworldService;

    @Inject
    private UpdateUserNickName updateUserNickName;

    @RabbitEvent(value = RouteEventNames.NICK_NAME_KEYWORLD_UPDATE, broadcast = true, autoDelete = true)
    private void initNickNameKeyword(Map<String, Object> data) {
        this.keyworldService.refreshNickNameKeyworldInfo();
        this.updateUserNickName.initNickNameKeyword();
    }

    @RabbitEvent(value = RouteEventNames.NICK_NAME_ASCII_UPDATE, broadcast = true, autoDelete = true)
    private void initNickNameAscii(Map<String, Object> data) {
        this.keyworldService.refreshNickNameAsciiInfo();
        this.updateUserNickName.initNickNameAscii();
    }
}
