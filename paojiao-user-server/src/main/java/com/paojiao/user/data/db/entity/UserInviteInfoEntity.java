package com.paojiao.user.data.db.entity;

import com.fission.datasource.annotation.ColumnAnnotation;
import com.fission.datasource.annotation.TableAnnotation;
import lombok.Data;

@TableAnnotation(dbName = "paojiao_user", tableName = "user_invite_info")
@Data
public class UserInviteInfoEntity implements com.fission.datasource.bean.BaseEntity {


    private transient static final long serialVersionUID = 1L;

    public transient static final String SHARE_TYPE = "share_type";

    public transient static final String CREATE_TIME = "create_time";

    public transient static final String USER_INVITE_ID = "user_invite_id";

    public transient static final String USER_ID = "user_id";

    public transient static final String INVITE_CODE = "invite_code";

    public transient static final String INVITE_NUM = "invite_num";

    @ColumnAnnotation(length = 10, columnName = UserInviteInfoEntity.SHARE_TYPE)
    private int shareType;

    @ColumnAnnotation(length = 19, columnName = UserInviteInfoEntity.CREATE_TIME)
    private java.sql.Timestamp createTime;

    @ColumnAnnotation(isKey = true, length = 10, isAuto = true, columnName = UserInviteInfoEntity.USER_INVITE_ID)
    private int userInviteId;

    @ColumnAnnotation(length = 10, columnName = UserInviteInfoEntity.USER_ID)
    private int userId;

    @ColumnAnnotation(length = 128, columnName = UserInviteInfoEntity.INVITE_CODE)
    private String inviteCode;

    @ColumnAnnotation(length = 10, columnName = UserInviteInfoEntity.INVITE_NUM)
    private int inviteNum;

}