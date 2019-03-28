package com.paojiao.user.handler;

import com.fission.next.common.bean.ClientContext;
import com.fission.next.utils.ErrorCode;
import com.fission.utils.bean.ResultUtil;
import com.paojiao.user.api.services.IUserCacheService;
import com.paojiao.user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;

@Service
@Named("rpc_UserCacheServiceImpl")
public class UserCacheServiceImpl implements IUserCacheService {

    @Inject
    private IUserService userService;

    @Override
    public ResultUtil<Void> clearUser(int userId, ClientContext clientContext) {
        this.userService.clearUserCacheInfo(userId);
        return ResultUtil.build(ErrorCode.SUCCESS);
    }
}
