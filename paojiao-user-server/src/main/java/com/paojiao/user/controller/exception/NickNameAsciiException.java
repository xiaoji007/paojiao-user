package com.paojiao.user.controller.exception;

public class NickNameAsciiException extends UpdateUserAttrException {
	private static final long serialVersionUID = -649938400223269422L;
	private String keyworld;

	public NickNameAsciiException(String message) {
		super();
		this.keyworld = keyworld;
	}

	public String getKeyworld() {
		return keyworld;
	}
}
