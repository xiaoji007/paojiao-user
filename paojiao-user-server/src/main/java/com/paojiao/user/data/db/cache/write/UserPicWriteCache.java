package com.paojiao.user.data.db.cache.write;

import com.fission.cache.FissionInitCache;
import com.fission.cache.util.CacheServiceUtil;
import com.fission.cache.util.CacheUtil;
import com.fission.utils.tool.ArrayUtils;
import com.paojiao.user.data.db.IUserPicWriteDao;
import com.paojiao.user.data.db.cache.BaseCache;
import com.paojiao.user.data.db.cache.util.CacheKeyUtil;
import com.paojiao.user.data.db.entity.UserPicInfoEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("userPicWriteCacheDao")
public class UserPicWriteCache extends BaseCache implements FissionInitCache, IUserPicWriteDao {

    @Override
    public int addUserPicInfo(UserPicInfoEntity userPicInfoEntity) throws SQLException {
        this.clearUserPicCache(userPicInfoEntity.getUserId());
        return CacheUtil.getCurrentCacheContextReturnVal(Integer.class);
    }

    @Override
    public void updateUserPicInfo(List<UserPicInfoEntity> userPicInfoEntitys) throws SQLException {
        if (ArrayUtils.isNotEmpty(userPicInfoEntitys)) {
            Set<Integer> userIds = new HashSet<>();
            userPicInfoEntitys.forEach((UserPicInfoEntity userPicInfoEntity) -> {
                userIds.add(userPicInfoEntity.getUserId());
            });
            userIds.forEach((Integer userId) -> {
                this.clearUserPicCache(userId);
            });
        }
    }

    @Override
    public void refuseUserPicInfo(int userId, int userPicId) throws SQLException {
        this.clearUserPicCache(userId);
    }

    @Override
    public void passUserPicInfo(int userId, int userPicId) throws SQLException {
        this.clearUserPicCache(userId);
    }

    @Override
    public void removeUserPicInfo(int userId, List<Integer> userPicIds) throws SQLException {
        this.clearUserPicCache(userId);
    }

    @Override
    public void removeUserPicInfo(int userId, int minPicNum) throws SQLException {
        this.clearUserPicCache(userId);
    }

    private void clearUserPicCache(int userId) {
        String key =  CacheKeyUtil.getUserPicInfoKey(userId);
        CacheServiceUtil.removeCacheListObject(key, UserPicInfoEntity.class);
    }
}
