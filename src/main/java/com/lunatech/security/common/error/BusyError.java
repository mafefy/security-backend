package com.lunatech.security.common.error;

public class BusyError extends AppError {

	public BusyError() {
		super(ErrorCode.busy.code, "Busy error");
	}
	
	public BusyError(String details) {
		super(ErrorCode.busy.code, details);
	}

}
