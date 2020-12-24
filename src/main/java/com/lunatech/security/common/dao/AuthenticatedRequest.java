package com.lunatech.security.common.dao;

import javax.validation.constraints.NotBlank;

import com.lunatech.security.common.model.Request;
import com.lunatech.security.authorization.AuthorizationStatus;
import com.lunatech.security.user.dao.UserCommonEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class AuthenticatedRequest extends Request{

	/*
	 * you can use headers too
	 */
	private String token;
	
	// will be injected by authentication aspect
	@JsonIgnore
	private AuthorizationStatus status;
}
