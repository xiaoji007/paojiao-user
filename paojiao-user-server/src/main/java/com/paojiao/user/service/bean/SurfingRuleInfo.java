package com.paojiao.user.service.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SurfingRuleInfo implements Serializable {


	private Date createTime;

	private int surfingIncrement;

	private String surfingRule;

	private int surfingNum;

	private int surfingRuleId;

	private String surfingRuleDesc;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSurfingIncrement() {
		return surfingIncrement;
	}

	public void setSurfingIncrement(int surfingIncrement) {
		this.surfingIncrement = surfingIncrement;
	}

	public String getSurfingRule() {
		return surfingRule;
	}

	public void setSurfingRule(String surfingRule) {
		this.surfingRule = surfingRule;
	}

	public int getSurfingNum() {
		return surfingNum;
	}

	public void setSurfingNum(int surfingNum) {
		this.surfingNum = surfingNum;
	}

	public int getSurfingRuleId() {
		return surfingRuleId;
	}

	public void setSurfingRuleId(int surfingRuleId) {
		this.surfingRuleId = surfingRuleId;
	}

	public String getSurfingRuleDesc() {
		return surfingRuleDesc;
	}

	public void setSurfingRuleDesc(String surfingRuleDesc) {
		this.surfingRuleDesc = surfingRuleDesc;
	}
}