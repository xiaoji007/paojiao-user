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
    private String hometown;
    private String presentAddress;
    private short relationshipStatus;
    private String professional;
    private String school;
    private String gisX;
    private String gisY;
    private Date createTime;
    private boolean online;
    private int regionId;
    private String videoIntroduceUrl;
    private String audioIntroduceUrl;
    private short objective;
    private int languageType;
    private short userType;
    private int religion;
    /**
     * 饮食习惯
     */
    private List<Integer> foodHabit;
    /**
     * 体重
     */
    private int height;
    /**
     * 身高
     */
    private int weight;
    /**
     * 完整度
     */
    private int integrity;
    /**
     * 上次登录时间
     */
    private Date lastLoginTime;
    /**
     * 上次活跃时间
     */
    private Date lastActiveTime;
}



