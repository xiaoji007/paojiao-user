package com.paojiao.user.service;

import com.fission.datasource.exception.RollbackSourceException;
import com.paojiao.user.api.bean.UserInviteCodeBean;
import com.paojiao.user.service.bean.UserInfo;
import com.paojiao.user.service.bean.UserInviteInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IUserService {

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    UserInfo getUserInfo(int userId);

    /**
     * 获取自己信息
     *
     * @param userId
     * @return
     */
    UserInfo getSelfInfo(int userId);

    /**
     * 重置完善度
     *
     * @param userId
     * @param oldIntegrity
     * @param toDb
     * @return
     */
    @Transactional(rollbackFor = {RollbackSourceException.class})
    int resetUserIntegrity(int userId, int oldIntegrity, boolean toDb);

    /**
     * 初始化用户信息
     *
     * @param userType
     * @param loginId
     * @return
     */
    @Transactional(rollbackFor = {RollbackSourceException.class})
    int initUserInfo(short userType, int loginId);

    /**
     * 更新用户属性
     *
     * @param userId
     * @param updateUserAttr
     */
    @Transactional(rollbackFor = {RollbackSourceException.class})
    void updateUserAttr(int userId, Map<Short, Object> updateUserAttr);

    /**
     * 清理用户缓存
     *
     * @param userId
     */
    @Transactional(rollbackFor = {RollbackSourceException.class})
    void clearUserCacheInfo(int userId);

    /**
     * 添加邀请码
     *
     * @param userId
     * @param shareType
     * @return
     */
    @Transactional(rollbackFor = {RollbackSourceException.class})
    String addUserInviteInfo(int userId, int shareType);

    /**
     * 获取用户邀请信息
     *
     * @param userId
     * @return
     */
    UserInviteCodeBean getUserRegisterInviteInfo(int userId);

    /**
     * 更新邀请码邀请数
     *
     * @param userId
     * @param inviteType
     * @param inviteCode
     */
    @Transactional(rollbackFor = {RollbackSourceException.class})
    void updateUserInviteInfo(int userId, short inviteType, String inviteCode);

    /**
     * 获取用户邀请码列表
     *
     * @param userId
     * @return
     */
    List<UserInviteInfo> listUserInviteInfo(int userId);
}



