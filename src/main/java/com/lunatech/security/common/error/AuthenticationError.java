package com.lunatech.security.common.error;

import com.lunatech.security.blocking.Violations;

public class AuthenticationError extends AppError {

	public AuthenticationError() {
		super(ErrorCode.authenticationError.code, "Authenticationt error", Violations.LOGIN);
	}

}
