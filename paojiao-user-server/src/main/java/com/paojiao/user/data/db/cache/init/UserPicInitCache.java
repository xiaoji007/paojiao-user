package com.paojiao.user.data.db.cache.init;

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

@Repository("userPicInitCacheDao")
public class UserPicInitCache  extends BaseCache implements FissionInitCache, IUserPicReadDao {

    @Override
    public List<UserPicInfoEntity> listAllUserPicInfo(int userId) throws SQLException {
        List<UserPicInfoEntity> list = CacheUtil.listCurrentCacheContextReturnVal(UserPicInfoEntity.class);
        String key =  CacheKeyUtil.getUserPicInfoKey(userId);
        CacheServiceUtil.addCacheObject(key,list);
        return list;
    }

    @Override
    public List<UserPicInfoEntity> listResetAllUserPicInfo(int userId) throws SQLException {
        CacheUtil.addFissionNoCacheMethod();
        return CacheUtil.listCurrentCacheContextReturnVal(UserPicInfoEntity.class);
    }
}
