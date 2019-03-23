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

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSurfing() {
		return surfing;
	}

	public void setSurfing(int surfing) {
		this.surfing = surfing;
	}

	public BigDecimal getGisX() {
		return gisX;
	}

	public void setGisX(BigDecimal gisX) {
		this.gisX = gisX;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public short getSex() {
		return sex;
	}

	public void setSex(short sex) {
		this.sex = sex;
	}

	public BigDecimal getGisY() {
		return gisY;
	}

	public void setGisY(BigDecimal gisY) {
		this.gisY = gisY;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public short getRelationshipStatus() {
		return relationshipStatus;
	}

	public void setRelationshipStatus(short relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public short getObjective() {
		return objective;
	}

	public void setObjective(short objective) {
		this.objective = objective;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getVideoIntroduceUrl() {
		return videoIntroduceUrl;
	}

	public void setVideoIntroduceUrl(String videoIntroduceUrl) {
		this.videoIntroduceUrl = videoIntroduceUrl;
	}

	public String getAudioIntroduceUrl() {
		return audioIntroduceUrl;
	}

	public void setAudioIntroduceUrl(String audioIntroduceUrl) {
		this.audioIntroduceUrl = audioIntroduceUrl;
	}

	public int getLanguageType() {
		return languageType;
	}

	public void setLanguageType(int languageType) {
		this.languageType = languageType;
	}

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}

	public int getReligion() {
		return religion;
	}

	public void setReligion(int religion) {
		this.religion = religion;
	}

	public int getIntegrity() {
		return integrity;
	}

	public void setIntegrity(int integrity) {
		this.integrity = integrity;
	}

	public List<Integer> getFoodHabit() {
		return foodHabit;
	}

	public void setFoodHabit(List<Integer> foodHabit) {
		this.foodHabit = foodHabit;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}


