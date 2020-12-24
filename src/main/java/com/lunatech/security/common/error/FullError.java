package com.lunatech.security.common.error;

import com.lunatech.security.blocking.Violations;

public class FullError extends AppError {

	public FullError() {
		super(ErrorCode.full.code, "Full Error", Violations.FULL);
	}

}
