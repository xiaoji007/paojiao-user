package com.paojiao.user.controller.exception;

public class DescKeywordException extends UpdateUserAttrException {
	private static final long serialVersionUID = -649938400223269422L;
	private String keyworld;

	public DescKeywordException(String message) {
		super();
		this.keyworld = keyworld;
	}

	public String getKeyworld() {
		return keyworld;
	}
}
