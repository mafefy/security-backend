package com.lunatech.security.common.error;

public class ExistError extends AppError {

	public ExistError() {
		super(ErrorCode.exist.code , "Exist error");
	}

}
