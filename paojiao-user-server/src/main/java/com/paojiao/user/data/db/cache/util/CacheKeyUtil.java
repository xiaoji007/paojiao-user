package com.paojiao.user.data.db.cache.util;

import com.paojiao.user.data.db.entity.UserAttrInfoEntity;
import com.paojiao.user.data.db.entity.UserInfoEntity;
import com.paojiao.user.data.db.entity.UserInviteInfoEntity;
import com.paojiao.user.data.db.entity.UserPicInfoEntity;

public class CacheKeyUtil {

    public static String getUserPicInfoKey(int userId) {
        return UserPicInfoEntity.class.getName() + "_" + userId;
    }

    public static String getUserInfoKey(int userId) {
        return UserInfoEntity.class.getName() + "_" + userId;
    }

    public static String getUserAttrInfoKey(int userId) {
        return UserAttrInfoEntity.class.getName() + "_" + userId;
    }

    public static String getUserInviteInfoKey(String code) {
        return UserInviteInfoEntity.class.getName() + "_" + code;
    }
}


