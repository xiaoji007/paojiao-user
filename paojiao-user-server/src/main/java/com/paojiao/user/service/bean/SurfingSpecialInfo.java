package com.paojiao.user.service.bean;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SurfingSpecialInfo implements Serializable {

	private Date createTime;

	private int toSurfing;

	private int surfing;

	private short surfingState;

	private int surfingId;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getToSurfing() {
		return toSurfing;
	}

	public void setToSurfing(int toSurfing) {
		this.toSurfing = toSurfing;
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

	public int getSurfingId() {
		return surfingId;
	}

	public void setSurfingId(int surfingId) {
		this.surfingId = surfingId;
	}
}