package com.lunatech.security.common.error;

public class BlockedError extends AppError {

	public BlockedError() {
		super(ErrorCode.blocked.code, "Blocked Error");
	}

}
