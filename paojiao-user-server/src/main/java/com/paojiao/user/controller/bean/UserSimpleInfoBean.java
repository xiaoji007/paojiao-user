package com.paojiao.user.controller.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserSimpleInfoBean implements Serializable {
	private static final long serialVersionUID = -3127742808081560956L;
	private int userId;
	private int userType;
	private int surfing;
	private String userDesc;
	private String nickName;
	private short sex;
	private String headPic;
	private Date birthday;

	private String hometown;
	private String presentAddress;
	private short relationshipStatus;
	private String professional;
	private String school;
	private String videoIntroduceUrl;
	private String audioIntroduceUrl;
	private short objective;
	private int religion;
	private int languageType;

	private String gisX;
	private String gisY;
	private boolean online;
	private long groupType;
	private short groupSubType;
	private double distance;
	private long lastActiveTime;
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
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getSurfing() {
		return surfing;
	}

	public void setSurfing(int surfing) {
		this.surfing = surfing;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
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

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public short getObjective() {
		return objective;
	}

	public void setObjective(short objective) {
		this.objective = objective;
	}

	public int getReligion() {
		return religion;
	}

	public void setReligion(int religion) {
		this.religion = religion;
	}

	public String getGisX() {
		return gisX;
	}

	public void setGisX(String gisX) {
		this.gisX = gisX;
	}

	public String getGisY() {
		return gisY;
	}

	public void setGisY(String gisY) {
		this.gisY = gisY;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public long getGroupType() {
		return groupType;
	}

	public void setGroupType(long groupType) {
		this.groupType = groupType;
	}

	public short getGroupSubType() {
		return groupSubType;
	}

	public void setGroupSubType(short groupSubType) {
		this.groupSubType = groupSubType;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public long getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(long lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	public int getLanguageType() {
		return languageType;
	}

	public void setLanguageType(int languageType) {
		this.languageType = languageType;
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

	public int getIntegrity() {
		return integrity;
	}

	public void setIntegrity(int integrity) {
		this.integrity = integrity;
	}
}



