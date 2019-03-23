package com.paojiao.user.data.db.entity;

import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import com.fission.datasource.bean.BaseEntity;
import lombok.Data;

@Data
@TableAnnotation(dbName = "paojiao_user", tableName = "user_info")
public class UserInfoEntity implements BaseEntity {

    public transient static final String BIRTHDAY = "birthday";
    public transient static final String LOGIN_ID = "login_id";
    public transient static final String CREATE_TIME = "create_time";
    public transient static final String LAST_UPDATE_TIME = "last_update_time";
    public transient static final String SURFING = "surfing";
    public transient static final String DEFAULT_SURFING = "default_surfing";
    public transient static final String SEX = "sex";
    public transient static final String HEAD_PIC = "head_pic";
    public transient static final String USER_TYPE = "user_type";
    public transient static final String USER_ID = "user_id";
    public transient static final String NICK_NAME = "nick_name";
    public transient static final String INTEGRITY = "integrity";

    private transient static final long serialVersionUID = 1L;
    @ColumnAnnotation(length = 10, columnName = UserInfoEntity.BIRTHDAY)
    private java.sql.Date birthday;

    @ColumnAnnotation(length = 10, columnName = UserInfoEntity.LOGIN_ID)
    private Integer loginId;

    @ColumnAnnotation(length = 19, columnName = UserInfoEntity.CREATE_TIME)
    private java.sql.Timestamp createTime;

    @ColumnAnnotation(length = 19, columnName = UserInfoEntity.LAST_UPDATE_TIME)
    private java.sql.Timestamp lastUpdateTime;

    @ColumnAnnotation(length = 10, columnName = UserInfoEntity.SURFING)
    private int surfing;

    @ColumnAnnotation(length = 10, columnName = UserInfoEntity.DEFAULT_SURFING)
    private int defaultSurfing;

    @ColumnAnnotation(length = 5, columnName = UserInfoEntity.SEX)
    private short sex;

    @ColumnAnnotation(length = 100, columnName = UserInfoEntity.HEAD_PIC)
    private String headPic;

    @ColumnAnnotation(length = 5, columnName = UserInfoEntity.USER_TYPE)
    private short userType;

    @ColumnAnnotation(isKey = true, length = 10, isAuto = true, columnName = UserInfoEntity.USER_ID)
    private int userId;

    @ColumnAnnotation(length = 50, columnName = UserInfoEntity.NICK_NAME)
    private String nickName;

    @ColumnAnnotation(length = 10, columnName = UserInfoEntity.INTEGRITY)
    private int integrity;
}