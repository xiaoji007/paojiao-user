package com.paojiao.user.data.db.cache.util;

import com.paojiao.user.data.db.entity.UserPicInfoEntity;

public class CacheKeyUtil {

    public static String getUserPicInfoKey(int userId) {
        return UserPicInfoEntity.class.getName() + "_" + userId;
    }

}


