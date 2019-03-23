package com.paojiao.user.api.bean;

import java.io.Serializable;

public class UserInviteCodeBean implements Serializable {

	private int shareType;

	private int inviteUserId;

	private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
