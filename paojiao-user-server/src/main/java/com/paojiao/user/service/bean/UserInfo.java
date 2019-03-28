package com.paojiao.user.service.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserInfo implements Serializable {

	private String userDesc;
	private Date birthday;
	private Date createTime;
	private int loginId;
	private int userId;
	private int surfing;
	private BigDecimal gisX;
	private String nickName;
	private short sex;
	private BigDecimal gisY;
	private String headPic;
	private String hometown;
	private String presentAddress;
	private short relationshipStatus;
	private String professional;
	private String school;
	private short objective;
	private int regionId;
	private String videoIntroduceUrl;
	private String audioIntroduceUrl;
	private int languageType;
	private short userType;
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
	 * 宗教
	 */
	private int religion;
	/**
	 * 完成度
	 */
	private int integrity;
	private Date lastActiveTime;
}


