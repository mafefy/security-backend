package com.lunatech.security.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lunatech.security.blocking.Violations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode( callSuper = true)
@JsonIgnoreProperties( value = "violation" , allowGetters = false)
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class AppError extends RuntimeException {

	private Integer code;
	private String message;
	private String details;
	private Violations violation;

	public AppError(Integer code, String message, Violations violation) {
		initialize(code, message,null, violation);
	}
	
	public AppError(Integer code, String message,String details, Violations violation) {
		initialize(code, message,details, violation);
	}

	public AppError(Integer code, String message) {
		initialize(code, message,null, null);
	}
	
	public AppError(Integer code, String message, String details) {
		initialize(code, message,details, null);
	}
	
	private void initialize(Integer code, String message, String details, Violations violation) {
		this.code = code;
		this.message = message;
		this.violation = violation;
		this.details = details;
	
	}
	
	public AppError clearStackTrace() {
		setStackTrace(new StackTraceElement[] {});
		return this;
	}
}
