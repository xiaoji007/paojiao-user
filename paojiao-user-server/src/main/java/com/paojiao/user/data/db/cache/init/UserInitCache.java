package com.paojiao.user.data.db.cache.init;

import com.fission.cache.FissionInitCache;
import com.fission.cache.util.CacheServiceUtil;
import com.fission.cache.util.CacheUtil;
import com.paojiao.user.data.db.IUserReadDao;
import com.paojiao.user.data.db.cache.BaseCache;
import com.paojiao.user.data.db.cache.util.CacheKeyUtil;
import com.paojiao.user.data.db.entity.UserAttrInfoEntity;
import com.paojiao.user.data.db.entity.UserInfoEntity;
import com.paojiao.user.data.db.entity.UserInviteInfoEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("userInitCacheDao")
public class UserInitCache extends BaseCache implements FissionInitCache, IUserReadDao {
    @Override
    public UserInfoEntity getUserInfo(int userId) throws SQLException {
        UserInfoEntity userInfoEntity = CacheUtil.getCurrentCacheContextReturnVal(UserInfoEntity.class);
        CacheServiceUtil.addCacheObject(CacheKeyUtil.getUserInfoKey(userId), userInfoEntity);
        return userInfoEntity;
    }

    @Override
    public List<UserAttrInfoEntity> listUserAttrInfo(int userId) throws SQLException {
        List<UserAttrInfoEntity> userAttrInfoEntityList = new ArrayList<>();
        CacheServiceUtil.addCacheObject(CacheKeyUtil.getUserAttrInfoKey(userId), userAttrInfoEntityList);
        return userAttrInfoEntityList;
    }

    @Override
    public UserInfoEntity getSelfUserInfo(int userId) throws SQLException {
        UserInfoEntity userInfoEntity = CacheUtil.getCurrentCacheContextReturnVal(UserInfoEntity.class);
        CacheServiceUtil.addCacheObject(CacheKeyUtil.getUserInfoKey(userId), userInfoEntity);
        return userInfoEntity;
    }

    @Override
    public List<UserAttrInfoEntity> listSelfUserAttrInfo(int userId) throws SQLException {
        List<UserAttrInfoEntity> userAttrInfoEntityList = new ArrayList<>();
        CacheServiceUtil.addCacheObject(CacheKeyUtil.getUserAttrInfoKey(userId), userAttrInfoEntityList);
        return userAttrInfoEntityList;
    }

    @Override
    public UserInviteInfoEntity getInviteUser(String inviteCode) throws SQLException {
        UserInviteInfoEntity userInviteInfoEntity = CacheUtil.getCurrentCacheContextReturnVal(UserInviteInfoEntity.class);
        CacheServiceUtil.addCacheObject(CacheKeyUtil.getUserInviteInfoKey(inviteCode), userInviteInfoEntity);
        return userInviteInfoEntity;
    }
}
