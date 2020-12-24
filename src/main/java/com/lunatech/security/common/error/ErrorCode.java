package com.lunatech.security.common.error;

public enum ErrorCode {

	validation(1), exist(2), notExist(3), busy(4), failure(5), notAllowed(6), full(7), authenticationExpired(8),
	authenticationError(9), notComplete(10), suspended(11) , blocked(12),  maxReached(13);

	private ErrorCode(Integer code) {
		this.code = code;
	}

	public Integer code;

	public boolean equals(ErrorCode error) {
		return code.equals(error.code);
	}

	public boolean equals(Integer code) {
		return code.equals(code);
	}
}
