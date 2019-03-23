package com.paojiao.user.util;

import com.fission.next.utils.ErrorCode;
import com.paojiao.user.api.util.UserErrorCode;
import com.paojiao.user.controller.bean.UserInfoBean;
import com.paojiao.user.service.bean.UserInfo;

public class ConstUtil {

    public static class NameUtil {
        public static final String REDIS_USER = "redis_user";

        public static final String DB_USER = "db_user";

        public static final String MONGO_USER = "mongo_user";
    }

    /**
     * 检测用户是否初始化完成
     */
    public static class CheckUserInfoUtil {

        public static int checkInit(UserInfoBean userInfoBean) {
            if (null == userInfoBean) {
                return UserErrorCode.USER_NO_INIT_ERROR;
            }
            if ((com.paojiao.user.api.util.ConstUtil.Sex.MAN != userInfoBean.getSex() && com.paojiao.user.api.util.ConstUtil.Sex.WOMAN != userInfoBean.getSex())/* || StringUtil.isBlank(userInfoBean.getNickName()) || StringUtil.isBlank(userInfoBean.getHeadPic()) || null == userInfoBean.getBirthday()*/) {
                return UserErrorCode.USER_NO_INIT_ERROR;
            }
			/*else if (Language.UNDEFIND == userInfoBean.getLanguageType()) {
				return UserErrorCode.USER_NO_INIT_LANGUAGE_ERROR;
			} else if (ConstUtil.ObjectiveType.CHAT != userInfoBean.getObjective() && ConstUtil.ObjectiveType.DATE != userInfoBean.getObjective() && ConstUtil.ObjectiveType.FRIEND != userInfoBean.getObjective()) {
			return UserErrorCode.USER_NO_INIT_OBJECTIVE_ERROR;
		}*/
            return ErrorCode.SUCCESS;
        }

        public static int checkInit(UserInfo userInfo) {
            if (null == userInfo) {
                return UserErrorCode.USER_NO_INIT_ERROR;
            }
            if ((com.paojiao.user.api.util.ConstUtil.Sex.MAN != userInfo.getSex() && com.paojiao.user.api.util.ConstUtil.Sex.WOMAN != userInfo.getSex()) /*|| StringUtil.isBlank(userInfo.getNickName()) || StringUtil.isBlank(userInfo.getHeadPic()) || null == userInfo.getBirthday()*/) {
                return UserErrorCode.USER_NO_INIT_ERROR;
            }/* else if (Language.UNDEFIND == userInfo.getLanguageType()) {
                return UserErrorCode.USER_NO_INIT_LANGUAGE_ERROR;
            } else if (ConstUtil.ObjectiveType.CHAT != userInfoBean.getObjective() && ConstUtil.ObjectiveType.DATE != userInfoBean.getObjective() && ConstUtil.ObjectiveType.FRIEND != userInfoBean.getObjective()) {
				return UserErrorCode.USER_NO_INIT_OBJECTIVE_ERROR;
			}*/
            return ErrorCode.SUCCESS;
        }
    }

    public static class KeyworldRultType {
        /**
         * 特殊处理:0
         */
        public static final short UNDEFINED = 0;
        /**
         * 包含:1
         */
        public static final short CONTAIN = 1;
        /**
         * 相同:2
         */
        public static final short EQUALS = 2;
    }

    /**
     * 昵称关键字ascii状态
     */
    public static class NickNameKeywordAscIIType {
        /**
         * 0:允许
         */
        public static final short Y = 0;
        /**
         * 1:拒绝
         */
        public static final short N = 1;
    }
}