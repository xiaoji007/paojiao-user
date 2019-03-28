package com.paojiao.user.api.util;

import com.fission.next.common.constant.RouteFieldNames;

public class ConstUtil {

    public static enum SurfingRuleAttrName {

        SURFING_INCREMENT("surfing_increment"),

        SURFING_RULE("surfing_rule"),

        SURFING_NUM("surfing_num"),

        SURFING_RULE_ID("surfing_rule_id"),

        SURFING_RULE_DESC("surfing_rule_desc");
        private String attrName;

        private SurfingRuleAttrName(String attrName) {
            this.attrName = attrName;
        }

        public String getAttrName() {
            return attrName;
        }
    }

    public static class UserAttrId {
        /**
         * 用户基本信息
         */

        /**
         * 昵称
         */
        public static final short NICK_NAME = 1;
        /**
         * 性别
         */
        public static final short SEX = 2;
        /**
         * 头像
         */
        public static final short HEAD_PIC = 3;
        /**
         * 生日
         */
        public static final short BIRTHDAY = 4;
        /**
         * 坐标
         */
        public static final short GIS = 5;

        /**
         *  用户属性信息
         *  user_attr
         *
         */

        /**
         * 描述
         */
        public static final short USER_DESC = 6;
        /**
         * 家乡
         */
        public static final short HOMETOWN = 7;
        /**
         * 现居地
         */
        public static final short PRESENT_ADDRESS = 8;
        /**
         * 职业
         */
        public static final short PROFESSIONAL = 9;
        /**
         * 学校
         */
        public static final short SCHOOL = 10;
        /**
         * 区域
         */
        public static final short REGION = 11;
        /**
         * 音频介绍
         */
        public static final short AUDIO_INTRODUCE_URL = 12;
        /**
         * 视频介绍
         */
        public static final short VIDEO_INTRODUCE_URL = 13;
        /**
         * 语言类型
         */
        public static final short LANGUAGE_TYPE = 14;
        /**
         * 目的
         */
        public static final short OBJECTIVE = 15;
        /**
         * 情感状态
         */
        public static final short RELATIONSHIP_STATUS = 16;
        /**
         * 宗教
         */
        public static final short RELIGION = 17;

        /**
         * 用户基本信息
         */

        /**
         * 用户登录id
         */
        public static final short LOGIN_ID = 18;

        /**
         * 用户靓号
         */
        public static final short SURFING = 19;

        /**
         * 用户类型
         */
        public static final short USER_TYPE = 20;

        /**
         * 用户信息完成度
         */
        public static final short INTEGRITY = 21;

        /**
         * 用户照片数
         */
        public static final short INDEX_PIC = 22;


        /**
         * 体重
         */
        public static final short WEIGHT = 23;

        /**
         * 升高
         */
        public static final short HEIGHT = 24;

        /**
         * 饮食习惯
         */
        public static final short FOOD_HABIT = 25;

        /**
         * 用户靓号
         */
        public static final short DEFAULT_SURFING = 26;

        /**
         * 最后活跃时间
         */
        public static final short LAST_ACTIV_TIME = 27;

        public static String getUpateUserAttrEventName(short userAttrId) {
            switch (userAttrId) {
                /**
                 * 用户登录id
                 */
                case ConstUtil.UserAttrId.LOGIN_ID:
                    return RouteFieldNames.LOGIN_ID;
                /**
                 * 用户靓号
                 */
                case ConstUtil.UserAttrId.SURFING:
                    return RouteFieldNames.USER_SURFING;
                /**
                 * 用户类型
                 */
                case ConstUtil.UserAttrId.USER_TYPE:
                    return RouteFieldNames.USER_TYPE;
                /**
                 * 描述
                 */
                case ConstUtil.UserAttrId.USER_DESC:
                    return RouteFieldNames.USER_DESC;
                /**
                 * 昵称
                 */
                case ConstUtil.UserAttrId.NICK_NAME:
                    return RouteFieldNames.NICK_NAME;
                /**
                 * 性别
                 */
                case ConstUtil.UserAttrId.SEX:
                    return RouteFieldNames.SEX;
                /**
                 * 头像
                 */
                case ConstUtil.UserAttrId.HEAD_PIC:
                    return RouteFieldNames.HEAD_PIC;
                /**
                 * 生日
                 */
                case ConstUtil.UserAttrId.BIRTHDAY:
                    return RouteFieldNames.BIRTH_DAY;
                /**
                 * 坐标X
                 */
                case ConstUtil.UserAttrId.GIS:
                    return RouteFieldNames.GIS;
                /**
                 * 家乡
                 */
                case ConstUtil.UserAttrId.HOMETOWN:
                    return RouteFieldNames.HOMETOWN;
                /**
                 * 现居地
                 */
                case ConstUtil.UserAttrId.PRESENT_ADDRESS:
                    return RouteFieldNames.PRESENT_ADDRESS;
                /**
                 * 情感状态
                 */
                case ConstUtil.UserAttrId.RELATIONSHIP_STATUS:
                    return RouteFieldNames.RELATIONSHIP_STATUS;
                /**
                 * 职业
                 */
                case ConstUtil.UserAttrId.PROFESSIONAL:
                    return RouteFieldNames.PROFESSIONAL;
                /**
                 * 学校
                 */
                case ConstUtil.UserAttrId.SCHOOL:
                    return RouteFieldNames.SCHOOL;
                /**
                 * 区域
                 */
                case ConstUtil.UserAttrId.REGION:
                    return RouteFieldNames.REGION_ID;
                /**
                 * 音频介绍
                 */
                case ConstUtil.UserAttrId.AUDIO_INTRODUCE_URL:
                    return RouteFieldNames.AUDIO_INTRODUCE_URL;
                /**
                 * 视频介绍
                 */
                case ConstUtil.UserAttrId.VIDEO_INTRODUCE_URL:
                    return RouteFieldNames.VIDEO_INTRODUCE_URL;
                /**
                 * 目的
                 */
                case ConstUtil.UserAttrId.OBJECTIVE:
                    return RouteFieldNames.OBJECTIVE;
                /**
                 * 语言
                 */
                case ConstUtil.UserAttrId.LANGUAGE_TYPE:
                    return RouteFieldNames.LANGUAGE_TYPE;
                /**
                 * 宗教
                 */
                case ConstUtil.UserAttrId.RELIGION:
                    return RouteFieldNames.RELIGION;
                /**
                 * 体重
                 */
                case ConstUtil.UserAttrId.WEIGHT:
                    return RouteFieldNames.WEIGHT;
                /**
                 * 身高
                 */
                case ConstUtil.UserAttrId.HEIGHT:
                    return RouteFieldNames.HEIGHT;
                /**
                 * 饮食习惯
                 */
                case ConstUtil.UserAttrId.FOOD_HABIT:
                    return RouteFieldNames.FOOD_HABIT;

            }
            return null;
        }

    }

    /**
     * 性别
     */
    public static class Sex {
        public static final short UNDEFIND = 0;
        public static final short MAN = 1;
        public static final short WOMAN = 2;
    }

    /**
     * 目的类型
     */
    public static class ObjectiveType {
        public static final short UNDEFIND = 0;
        public static final short DATE = 1;
        public static final short CHAT = 2;
        public static final short FRIEND = 3;
    }

    /**
     * 情感状态类型
     */
    public static class RelationshipStatusType {
        public static final short UNDEFIND = 0;
        public static final short SINGLE = 1;
        public static final short LOVE = 2;
        public static final short MARRY = 3;
        public static final short ASK_ME = 4;
    }

    /**
     * 用户类型
     */
    public static class UserType {
        public static final short UNDEFIND = 0;
        public static final short SYSTEM = 1;
        public static final short COMMON = 2;
        public static final short CS = 98;
        public static final short PUPPET = 99;
    }

    /**
     * 邀请状态
     */
    public static final class InviteState {
        public static final short INIT = 0;
        public static final short MANAGE = 1;
    }

    /**
     * 用户注册邀请类型
     */
    public static final class InviteType {
        public static final short UNDEFIND = 0;
        public static final short REGISTER = 1;
        public static final short HAVE_REGISTER = 2;
    }

    /**
     * 靓号特殊状态
     */
    public static final class SurfingSpecialState {
        public static final short UNDEFIND = -1;
        public static final short INIT = 0;
        public static final short MANAGE = 1;
    }

    /**
     * 靓号回收状态
     */
    public static final class SurfingRecoverState {
        public static final short UNDEFIND = -1;
        public static final short INIT = 0;
        public static final short USER_USR = 1;
        public static final short SHOP_USR = 2;
        public static final short USER_ALREADY_USR = 3;
        public static final short SHOP_ALREADY_USR = 4;
    }

    public static final class OnlineState {
        public static final short UNDEFIND = -1;
        public static final short ONLINE = 0;
        public static final short UNONLINE = 1;
    }

    public static class UserPicVerifyState {
        /**
         * 特殊处理:0
         */
        public static final short UNDEFINED = 0;
        /**
         * 待验证:1
         */
        public static final short INIT = 1;
        /**
         * 验证通过:2
         */
        public static final short PASS = 2;
        /**
         * 验证拒绝:3
         */
        public static final short REFUSE = 3;
    }
}


