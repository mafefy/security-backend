package com.lunatech.security.user.model;

import javax.security.sasl.AuthorizeCallback;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lunatech.security.common.dao.AuthenticatedRequest;

import lombok.Data;

@Data
public class AddAccountRequest extends AuthenticatedRequest {
	
	@NotNull
	@Valid
	private Credentials credentials;
	@NotBlank
	private String systemId;
	@NotBlank
	private String role;
}
