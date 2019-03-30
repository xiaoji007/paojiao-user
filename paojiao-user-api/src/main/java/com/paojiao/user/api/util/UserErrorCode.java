package com.paojiao.user.api.util;


import com.fission.next.utils.ErrorCode;
import com.fission.utils.annotation.ErrorAnnotation;

public class UserErrorCode {

    /**
     * 用户系统错误 20000
     */
    @ErrorAnnotation("用户系统错误")
    public static final int USER_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 1;

    /**
     * 未初始化用户信息
     */
    @ErrorAnnotation("未初始化用户信息")
    public static final int USER_NO_INIT_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 2;

    /**
     * 未初始化目的信息
     */
    @ErrorAnnotation("未初始化目的信息")
    public static final int USER_NO_INIT_OBJECTIVE_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 3;

    /**
     * 用户不存在
     */
    @ErrorAnnotation("用户不存在")
    public static final int USER_NO_EXISTS_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 4;

    /**
     * 初始化属性错误
     */
    @ErrorAnnotation("初始化属性错误")
    public static final int INIT_USER_ATTR_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 5;

    /**
     * 更新属性错误
     */
    @ErrorAnnotation("更新属性错误")
    public static final int UPDATE_USER_ATTR_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 6;

    /**
     * 昵称为空
     */
    @ErrorAnnotation("昵称为空")
    public static final int NICK_NAME_NULL_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 7;

    /**
     * 昵称长度错误
     */
    @ErrorAnnotation("昵称长度错误")
    public static final int NICK_NAME_LENGTH_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 8;

    /**
     * 昵称包含关键字
     */
    @ErrorAnnotation("昵称包含关键字")
    public static final int NICK_NAME_KEYWORD_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 9;

    /**
     * 已经始化用户信息
     */
    @ErrorAnnotation("已经始化用户信息")
    public static final int USER_HAVE_INIT_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 10;

    /**
     * 性别错误
     */
    @ErrorAnnotation("性别错误")
    public static final int SEX_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 11;


    /**
     * 用户介绍包含关键字
     */
    @ErrorAnnotation("用户介绍包含关键字")
    public static final int DESC_KEYWORD_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 12;

    /**
     * 用户介绍太长
     */
    @ErrorAnnotation("用户介绍太长")
    public static final int DESC_LENGTH_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 13;

    /**
     * 昵称包含非法ASCII
     */
    @ErrorAnnotation("昵称包含非法ASCII")
    public static final int NICK_NAME_ASCII_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 14;

    /**
     * 文件太大错误
     */
    @ErrorAnnotation("文件太大错误")
    public static final int PIC_TOO_LONG_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 15;

    /**
     * 文件格式错误
     */
    @ErrorAnnotation("文件格式错误")
    public static final int PIC_FILE_EXT_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 16;


    /**
     * 生日错误
     */
    @ErrorAnnotation("生日错误")
    public static final int BIRTHDAY_ERROR = ErrorCode.PaoJiao.USER_SERVER_ERROR + 17;

}



