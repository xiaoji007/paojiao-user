package com.paojiao.user.api.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserPicInfoBean implements Serializable {

    private int userPicId;
    private int userId;
    private String picUrl;
    private String oldPicUrl;
    private int index;
    private short verifyState;
    private Date createTime;

}



