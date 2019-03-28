package com.paojiao.user.api.bean;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserInfoBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3127742808081560956L;
    private int userId;
    private int surfing;
    private int loginId;
    private String userDesc;
    private String nickName;
    private short sex;
    private String headPic;
    private String oldHeadPic;
    private short headVerifyState;
    private Date birthday;
    private String city;
    private String professional;
    private String gisX;
    private String gisY;
    private Date createTime;
    private boolean online;
    private short userType;
    private int integrity;
    private Date lastLoginTime;
    private Date lastActiveTime;
}



