package com.paojiao.user.api.services;

import com.fission.motan.spring.Rpc;
import com.fission.next.common.bean.ClientContext;
import com.fission.next.common.constant.ServiceName;
import com.fission.utils.bean.ResultUtil;
import com.paojiao.user.api.bean.UserInfoBean;
import com.paojiao.user.api.bean.UserInviteCodeBean;
import com.weibo.api.motan.transport.async.MotanAsync;

import java.util.Date;
import java.util.List;
import java.util.Map;

@MotanAsync
@Rpc(ServiceName.PAOJIAO_USER)
public interface IUserService {

    /**
     * 获取聊天token
     *
     * @param userId
     * @param clientContext
     * @return
     */
    ResultUtil<String> getUserToken(int userId, ClientContext clientContext);

    /**
     * 生成邀请码
     *
     * @param userId
     * @param shareType
     * @param clientContext
     * @return
     */
    ResultUtil<String> getUserInviteCode(int userId, int shareType, ClientContext clientContext);

    /**
     * 获取用户的被邀请信息
     *
     * @param userId
     * @param clientContext
     * @return
     */
    ResultUtil<UserInviteCodeBean> getInviteRegisterUser(int userId, ClientContext clientContext);

    /**
     * 更新用户邀请码邀请信息
     *
     * @param userId
     * @param inviteCode
     * @param inviteType
     * @param clientContext
     * @return
     */
    ResultUtil<Void> updateUserInviteCode(int userId, String inviteCode, short inviteType, ClientContext clientContext);

    /**
     * 获取用户信息
     *
     * @param userId
     * @param clientContext
     * @return
     */
    ResultUtil<UserInfoBean> getUserInfo(int userId, ClientContext clientContext);

    /**
     * 更新用户属性
     *
     * @param userId
     * @param updateUserAttr
     * @param clientContext
     * @return
     */
    ResultUtil<Void> updateUserAttr(int userId, Map<Short, Object> updateUserAttr, ClientContext clientContext);

    /**
     * 创建用户id
     *
     * @param userType
     * @param loginId
     * @param clientContext
     * @return
     */
    ResultUtil<Integer> getUserId(short userType, int loginId, ClientContext clientContext);

    /**
     * 设置用户在线状态，普通用户调用
     *
     * @param userId
     * @param online
     * @param clientContext
     * @return
     */
    ResultUtil<Void> setUserOnlineStatus(int userId, boolean online, ClientContext clientContext);

    /**
     * 批量设置用户在线状态，陪聊账号调用
     *
     * @param userIds
     * @param online
     * @param clientContext
     * @return
     */
    ResultUtil<Void> setUsersOnlineStatus(List<Integer> userIds, boolean online, ClientContext clientContext);

    /**
     * @param userId
     * @param clientContext
     * @return
     */
    ResultUtil<UserInfoBean> getSelfInfo(int userId, ClientContext clientContext);

    /**
     * 判断用户是否在线
     *
     * @param userId
     * @param clientContext
     * @return
     */
    ResultUtil<Boolean> userIdOnline(int userId, ClientContext clientContext);
}




