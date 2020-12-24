package com.lunatech.security.common.model;

import com.lunatech.security.common.error.AppError;

import lombok.Data;

@Data
public class Response {

	private Boolean success;
	private AppError error;
	public Response() {
		this(false,null);
	}
	
	public Response(Boolean success, AppError error) {
		initialize(success, error);
	}
	
	public void  initialize(Boolean success, AppError error)  {
		this.success = success;
		this.error = error;
	}
	
	
}
