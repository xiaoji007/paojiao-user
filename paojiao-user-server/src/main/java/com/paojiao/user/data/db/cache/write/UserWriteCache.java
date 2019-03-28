package com.paojiao.user.data.db.cache.write;

import com.fission.cache.FissionAfterCache;
import com.fission.cache.util.CacheServiceUtil;
import com.fission.cache.util.CacheUtil;
import com.fission.utils.tool.ArrayUtils;
import com.paojiao.user.data.db.IUserWriteDao;
import com.paojiao.user.data.db.cache.BaseCache;
import com.paojiao.user.data.db.cache.util.CacheKeyUtil;
import com.paojiao.user.data.db.entity.UserAttrInfoEntity;
import com.paojiao.user.data.db.entity.UserInfoEntity;
import com.paojiao.user.data.db.entity.UserInviteInfoEntity;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserWriteCache extends BaseCache implements FissionAfterCache, IUserWriteDao {


    @Override
    public int addUserInfo(UserInfoEntity userInfoEntity) throws SQLException {
        CacheUtil.addFissionNoCacheMethod();
        return 0;
    }

    @Override
    public void updateUserInfo(int userId, Map<String, Object> updateAttr) throws SQLException {
        CacheServiceUtil.removeCacheObject(CacheKeyUtil.getUserInfoKey(userId), UserInfoEntity.class);
    }

    @Override
    public void addUserAttrInfo(int userId, List<UserAttrInfoEntity> userAttrInfoEntitys) throws SQLException {
        if (ArrayUtils.isNullOrEmpty(userAttrInfoEntitys)) {
            return;
        }
        String key = CacheKeyUtil.getUserAttrInfoKey(userId);
        List<UserAttrInfoEntity> userAttrInfoEntityList = CacheServiceUtil.listCacheObject(key, UserAttrInfoEntity.class);
        if (ArrayUtils.isNullOrEmpty(userAttrInfoEntityList)) {
            CacheServiceUtil.removeCacheListObject(key, UserAttrInfoEntity.class);
            return;
        }
        Map<Short, UserAttrInfoEntity> userAttrMap = userAttrInfoEntityList.stream().collect(Collectors.toMap(UserAttrInfoEntity::getUserAttrType, Function.identity()));
        userAttrInfoEntitys.forEach((UserAttrInfoEntity userAttrInfoEntity) -> {
            if (null != userAttrInfoEntity) {
                UserAttrInfoEntity userAttrInfoEntityCache = userAttrMap.get(userAttrInfoEntity.getUserAttrType());
                if (null != userAttrInfoEntityCache) {
                    userAttrInfoEntityCache.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
                    userAttrInfoEntityCache.setUserAttr(userAttrInfoEntity.getUserAttr());
                } else {
                    userAttrMap.put(userAttrInfoEntity.getUserAttrType(), userAttrInfoEntity);
                }
            }
        });
        List<UserAttrInfoEntity> userAttrInfoEntityList1 = new ArrayList<>();
        userAttrInfoEntityList1.addAll(userAttrMap.values());
        CacheServiceUtil.addCacheObject(key, userAttrInfoEntityList1);
    }

    @Override
    public void addUserInviteInfo(UserInviteInfoEntity userInviteInfoEntity) throws SQLException {
        CacheUtil.addFissionNoCacheMethod();
    }

    @Override
    public boolean subUserInviteNum(String inviteCode) throws SQLException {
        UserInviteInfoEntity userInviteInfoEntity = CacheServiceUtil.getCacheObject(CacheKeyUtil.getUserInviteInfoKey(inviteCode), UserInviteInfoEntity.class);
        if (null != userInviteInfoEntity) {
            userInviteInfoEntity.setInviteNum(userInviteInfoEntity.getInviteNum() - 1);
            if (userInviteInfoEntity.getInviteNum() < 1) {
                CacheServiceUtil.removeCacheListObject(CacheKeyUtil.getUserInviteInfoKey(inviteCode), UserInviteInfoEntity.class);
            } else {
                CacheServiceUtil.updateCacheObject(CacheKeyUtil.getUserInviteInfoKey(inviteCode), userInviteInfoEntity);
            }
        }
        return false;
    }
}
