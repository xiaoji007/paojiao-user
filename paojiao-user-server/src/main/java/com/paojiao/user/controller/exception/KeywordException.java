package com.paojiao.user.controller.exception;

import com.fission.next.common.error.FissionCodeException;

public class KeywordException extends FissionCodeException {
	private static final long serialVersionUID = -649938400223269422L;
	private String keyworld;

	public KeywordException(String message, int code) {
		super(code);
		this.keyworld = keyworld;
	}

	public String getKeyworld() {
		return keyworld;
	}
}
