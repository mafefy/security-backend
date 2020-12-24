package com.lunatech.security.common.error;

public class NotExistError extends AppError {

	public NotExistError() {
		super(ErrorCode.notExist.code, "Not exist error");
	}

}
