package com.lunatech.security.user.model;

import java.util.List;

import com.lunatech.security.common.model.Response;
import com.lunatech.security.user.dao.UserEntity;

import lombok.Data;

@Data
public class UsersListResponse extends Response {

	private List<UserEntity> users;
	
	private Long totalRecords ;
	
}
