package com.lunatech.security.common.error;

import com.lunatech.security.blocking.Violations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(value = { "violation" }, allowSetters = false, allowGetters = false)
@Data
public class NotAllowedError extends AppError {

	public NotAllowedError(Violations violations) {
		super(ErrorCode.notAllowed.code, "Not allowed Error", violations);
	}
	
	public NotAllowedError() {
		super(ErrorCode.notAllowed.code, "Not allowed Error", Violations.NOT_ALLOWED);
	}
	public NotAllowedError(String details) {
		super(ErrorCode.notAllowed.code, "Not allowed Error",details,  Violations.NOT_ALLOWED);
	}
}
