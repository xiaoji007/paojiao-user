package com.paojiao.user.service.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserInfo implements Serializable {

    private String userDesc;
    private Date birthday;
    private Date createTime;
    private int loginId;
    private int userId;
    private int surfing;
    private BigDecimal gisX;
    private BigDecimal gisY;
    private String nickName;
    private short sex;
    private String headPic;
    private String city;
    private String professional;
    private short userType;
    private int integrity;
    private Date lastActiveTime;
}


