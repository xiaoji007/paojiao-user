package com.paojiao.user.service.bean;


import java.util.Date;

public class NickNameAsciiInfo {

	private int endAscii;

	private Date createTime;

	private int startAscii;

	private short asciiType;

	private int asciiId;

	public void setEndAscii(int endAscii) {
		this.endAscii = endAscii;
	}

	public int getEndAscii() {
		return this.endAscii;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setStartAscii(int startAscii) {
		this.startAscii = startAscii;
	}

	public int getStartAscii() {
		return this.startAscii;
	}

	public void setAsciiType(short asciiType) {
		this.asciiType = asciiType;
	}

	public short getAsciiType() {
		return this.asciiType;
	}

	public void setAsciiId(int asciiId) {
		this.asciiId = asciiId;
	}

	public int getAsciiId() {
		return this.asciiId;
	}

}