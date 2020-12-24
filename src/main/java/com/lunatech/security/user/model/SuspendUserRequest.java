package com.lunatech.security.user.model;

import javax.validation.constraints.NotNull;

import com.lunatech.security.common.dao.AuthenticatedRequest;

import lombok.Data;

@Data
public class SuspendUserRequest  extends AuthenticatedRequest{

	@NotNull
	private Long id;
	
	@NotNull
	private Boolean suspend;
}
