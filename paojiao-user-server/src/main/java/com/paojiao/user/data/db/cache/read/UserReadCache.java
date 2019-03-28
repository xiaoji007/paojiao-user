package com.paojiao.user.data.db.cache.read;

import com.fission.cache.FissionBeforeCache;
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
import java.util.List;

@Repository("userReadCacheDao")
public class UserReadCache extends BaseCache implements FissionBeforeCache, IUserReadDao {

    @Override
    public UserInfoEntity getUserInfo(int userId) throws SQLException {
        return CacheServiceUtil.getCacheObject(CacheKeyUtil.getUserInfoKey(userId), UserInfoEntity.class);
    }

    @Override
    public List<UserAttrInfoEntity> listUserAttrInfo(int userId) throws SQLException {
        return CacheServiceUtil.listCacheObject(CacheKeyUtil.getUserAttrInfoKey(userId), UserAttrInfoEntity.class);
    }

    @Override
    public UserInfoEntity getSelfUserInfo(int userId) throws SQLException {
        CacheUtil.addFissionNoCacheMethod();
        return null;
    }

    @Override
    public List<UserAttrInfoEntity> listSelfUserAttrInfo(int userId) throws SQLException {
        CacheUtil.addFissionNoCacheMethod();
        return null;
    }

    @Override
    public UserInviteInfoEntity getInviteUser(String inviteCode) throws SQLException {
        return CacheServiceUtil.getCacheObject(CacheKeyUtil.getUserInviteInfoKey(inviteCode), UserInviteInfoEntity.class);
    }
}
