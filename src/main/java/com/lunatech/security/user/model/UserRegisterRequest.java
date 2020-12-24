package com.lunatech.security.user.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lunatech.security.common.dao.AuthenticatedRequest;
import com.lunatech.security.common.model.Request;

import lombok.Data;

/**
 * @author vedusoft2016@gmail.com 
 * client regiester for himsel if self registeration enabled
 */
@Data
public class UserRegisterRequest extends Request {
	@NotNull
	@Valid
	private Credentials credentials;
}
