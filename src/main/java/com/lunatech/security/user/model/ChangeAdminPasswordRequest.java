package com.lunatech.security.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lunatech.security.common.dao.AuthenticatedRequest;

import lombok.Data;

@Data
public class ChangeAdminPasswordRequest extends AuthenticatedRequest {

	@NotNull
	private Long id;
	@NotBlank
	private String newPassword;
}
