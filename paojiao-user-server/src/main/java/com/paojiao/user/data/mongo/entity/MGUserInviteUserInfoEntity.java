package com.paojiao.user.data.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = MGUserInviteUserInfoEntity.TABLE_NAME)
public class MGUserInviteUserInfoEntity implements Serializable {

    public transient static final String ID = "id";
    public transient static final String TABLE_NAME = "user_invite_user_info";
    public transient static final String USER_ID = "userId";
    public transient static final String INVITE_USER_ID = "inviteUserId";
    public transient static final String INVITE_STATE = "inviteState";
    public transient static final String INVITE = "invite";
    public transient static final String UPDATE_TIME = "updateTime";
    public transient static final String CREATE_TIME = "createTime";
    private transient static final long serialVersionUID = 1L;
    @Id
    private String id;
    private int userId;
    private int shareType;
    private boolean invite;
    private short inviteType;
    private short inviteState;
    private String inviteCode;
    private int inviteUserId;
    private Date updateTime;
    private Date createTime;

}