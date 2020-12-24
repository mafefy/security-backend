package com.lunatech.security.user.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.lunatech.security.common.dao.AuthenticatedRequest;

import lombok.Data;

@Data
public class UsersListRequest extends AuthenticatedRequest {

	@NotNull
	private String name;
	@NotNull
	@Min(0)
	private Integer pageIndex;
	@NotNull
	@Min(1)
	@Max(200)
	private Integer pageSize;
}
