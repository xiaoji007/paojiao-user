package com.paojiao.user.data.db.cache.read;

import com.fission.cache.FissionInitCache;
import com.fission.cache.util.CacheServiceUtil;
import com.fission.cache.util.CacheUtil;
import com.paojiao.user.data.db.IUserPicReadDao;
import com.paojiao.user.data.db.cache.BaseCache;
import com.paojiao.user.data.db.cache.util.CacheKeyUtil;
import com.paojiao.user.data.db.entity.UserPicInfoEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository("userPicCahceDao")
public class UserPicReadCache extends BaseCache implements FissionInitCache, IUserPicReadDao {

    @Override
    public List<UserPicInfoEntity> listAllUserPicInfo(int userId) throws SQLException {
        String key = CacheKeyUtil.getUserPicInfoKey(userId);
        return CacheServiceUtil.listCacheObject(key, UserPicInfoEntity.class);
    }

    @Override
    public List<UserPicInfoEntity> listResetAllUserPicInfo(int userId) throws SQLException {
        CacheUtil.addFissionNoCacheMethod();
        return null;
    }
}
