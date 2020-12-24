package com.lunatech.security.user.model;

import javax.validation.constraints.NotBlank;

import com.lunatech.security.common.dao.AuthenticatedRequest;

import lombok.Data;

@Data 
public class ChangePasswordRequest extends AuthenticatedRequest {

	@NotBlank
	private String currentPassword;
	@NotBlank
	private String newPassword;
}
