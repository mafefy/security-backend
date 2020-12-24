package com.lunatech.security.user.model;

import com.lunatech.security.common.model.Response;
import com.lunatech.security.user.dao.UserCommonEntity;
import com.lunatech.security.user.dao.UserEntity;

import lombok.Data;

@Data
public class AuthenticatedResponse extends Response {

	private String token;
	private UserCommonEntity user;
}
