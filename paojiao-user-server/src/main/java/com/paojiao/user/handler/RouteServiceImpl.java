package com.paojiao.user.handler;

import com.alibaba.fastjson.JSONObject;
import com.fission.next.common.bean.ClientContext;
import com.fission.next.common.constant.MessageType;
import com.fission.next.common.transport.bean.RouteMessage;
import com.fission.next.common.transport.service.RouteService;
import com.fission.next.utils.ErrorCode;
import com.fission.utils.bean.ResultUtil;
import com.paojiao.user.api.services.IUserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * TODO
 *
 * @author: chenjun
 * @date: 2017/11/6 20:04
 */
@Service
@Named("rpc_RouteServiceImpl")
public class RouteServiceImpl implements RouteService {

    @Inject
    private IUserService userHandler;

    @Override
    public ResultUtil<Void> route(RouteMessage message) {
        String msgType = message.getObjectName();
        switch (msgType) {
            case MessageType.ONLINE_STATE_UPDATE: {
                JSONObject jsonObject = JSONObject.parseObject(message.getContent());
                int userId = message.getFromUserId();
                Boolean online = jsonObject.getBooleanValue("online");
                this.userHandler.setUserOnlineStatus(userId, online, ClientContext.Default);
            }
            break;
            default:
                break;
        }
        return ResultUtil.build(ErrorCode.SUCCESS);
    }
}
