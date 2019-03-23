package com.paojiao.user.api.services;

import com.fission.motan.spring.Rpc;
import com.fission.next.common.bean.ClientContext;
import com.fission.next.common.constant.ServiceName;
import com.fission.utils.bean.ResultUtil;
import com.weibo.api.motan.transport.async.MotanAsync;

@MotanAsync
@Rpc(ServiceName.PAOJIAO_USER)
public interface IUserCacheService {

    ResultUtil<Void> clearUser(int userId, ClientContext clientContext);
}
