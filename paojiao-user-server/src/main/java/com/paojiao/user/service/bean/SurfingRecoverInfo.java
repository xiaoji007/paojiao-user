package com.paojiao.user.service.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SurfingRecoverInfo implements Serializable {
	private Date updateTime;

	private Date createTime;

	private int surfing;

	private short surfingState;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSurfing() {
		return surfing;
	}

	public void setSurfing(int surfing) {
		this.surfing = surfing;
	}

	public short getSurfingState() {
		return surfingState;
	}

	public void setSurfingState(short surfingState) {
		this.surfingState = surfingState;
	}
}