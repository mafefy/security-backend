package com.lunatech.security.common.error;

public class NotCompleteError extends AppError {

	public NotCompleteError() {
		super(ErrorCode.notComplete.code, "Not complete Error");
	}

}
