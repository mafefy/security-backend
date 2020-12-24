package com.lunatech.security.common.error;

import com.lunatech.security.blocking.Violations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class WrongCodeError extends AppError {

	private String fileId;
	public WrongCodeError(String fileId,String details) {
		
		super(ErrorCode.notAllowed.code, "Wrong password",details,  Violations.NOT_ALLOWED);
		this.fileId = fileId;
	}
}
