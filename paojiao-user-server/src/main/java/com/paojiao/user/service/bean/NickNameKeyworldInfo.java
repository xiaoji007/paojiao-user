package com.paojiao.user.service.bean;

import java.util.Date;

public class NickNameKeyworldInfo {

	private Date createTime;

	private int keyworldId;

	private short ruleType;

	private String keyworld;

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getKeyworldId() {
		return this.keyworldId;
	}

	public void setKeyworldId(int keyworldId) {
		this.keyworldId = keyworldId;
	}

	public short getRuleType() {
		return ruleType;
	}

	public void setRuleType(short ruleType) {
		this.ruleType = ruleType;
	}

	public String getKeyworld() {
		return this.keyworld;
	}

	public void setKeyworld(String keyworld) {
		this.keyworld = keyworld;
	}

}