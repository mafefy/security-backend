package com.lunatech.security.common.error;

public class SuspendedError extends AppError {

	public SuspendedError() {
		super(ErrorCode.suspended.code, "Suspended Error");
	}

}
