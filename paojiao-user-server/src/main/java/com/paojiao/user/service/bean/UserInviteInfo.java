package com.paojiao.user.service.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInviteInfo implements Serializable {


	private short inviteType;

	private Date createTime;

	private String userInviteId;

	private int userId;

	private short inviterState;

	private String inviteCode;

	private int shareType;

	private int inviteUserId;

	private boolean invite;

	public short getInviteType() {
		return inviteType;
	}

	public void setInviteType(short inviteType) {
		this.inviteType = inviteType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserInviteId() {
		return userInviteId;
	}

	public void setUserInviteId(String userInviteId) {
		this.userInviteId = userInviteId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public short getInviterState() {
		return inviterState;
	}

	public void setInviterState(short inviterState) {
		this.inviterState = inviterState;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public int getShareType() {
		return shareType;
	}

	public void setShareType(int shareType) {
		this.shareType = shareType;
	}

	public int getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(int inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public boolean isInvite() {
		return invite;
	}

	public void setInvite(boolean invite) {
		this.invite = invite;
	}
}