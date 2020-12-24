package com.lunatech.security.common.error;

public class FailureError extends AppError {

	public FailureError() {
		super(ErrorCode.failure.code, "Failure Error");
	}
	
	public FailureError(String details) {
		super(ErrorCode.failure.code, "Failure Error", details);
	}

}
