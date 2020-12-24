package com.lunatech.security.common.error;

public class AuthenticationExpiredError extends AppError {

	public AuthenticationExpiredError() {
		super(ErrorCode.authenticationExpired.code, "Authentication token expired error");
	}

}
