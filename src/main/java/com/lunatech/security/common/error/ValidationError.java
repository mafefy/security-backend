package com.lunatech.security.common.error;

public class ValidationError extends AppError {

	public ValidationError() {
		super(ErrorCode.validation.code, "Validation error");
	}


	public ValidationError(String details) {
		super(ErrorCode.validation.code, "Validation error", details);
	}
}
