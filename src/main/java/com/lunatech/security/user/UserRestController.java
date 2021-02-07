package com.lunatech.security.user;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lunatech.security.common.model.Response;
import com.lunatech.security.authorization.SecurityOptions;
import com.lunatech.security.authorization.UserRole;
import com.lunatech.security.user.model.ActivateUserRequest;
import com.lunatech.security.user.model.AddAccountRequest;
import com.lunatech.security.user.model.AuthenticatedResponse;
import com.lunatech.security.user.model.ChangeAdminPasswordRequest;
import com.lunatech.security.user.model.ChangePasswordRequest;
import com.lunatech.security.user.model.DeleteUserRequest;
import com.lunatech.security.user.model.LoginRequest;
import com.lunatech.security.user.model.SuspendUserRequest;
import com.lunatech.security.user.model.UserRegisterRequest;
import com.lunatech.security.user.model.UsersListRequest;
import com.lunatech.security.user.model.UsersListResponse;

@RestController
@RequestMapping(value = "user")
public class UserRestController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "login")
	@SecurityOptions(enableBlocking = true, enableAuthorization = false, enableRoles = false)
	public AuthenticatedResponse loginSecuritySystemUser(@Valid @RequestBody LoginRequest request) {
		return userService.loginSecuritySystemUser(request);
	}
	
	
	@PostMapping(value = "login-user")
	@SecurityOptions(enableBlocking = true, enableAuthorization = false, enableRoles = false)
	public AuthenticatedResponse loginUser(@Valid @RequestBody LoginRequest request) {
		return userService.loginUser(request);
	}
	 
	
	@PostMapping(value = "suspend-account")
	@SecurityOptions(roles = { UserRole.ROOT })
	public Response suspendAccount(@Valid @RequestBody SuspendUserRequest request) {
		return userService.suspendAccount(request);
	}
	


	@PostMapping(value = "add-account")
	@SecurityOptions(roles = { UserRole.ROOT })
	public Response addAccount(@Valid @RequestBody AddAccountRequest request) {
		return userService.addAccount(request);
	}
	
	@PostMapping(value = "delete-account")
	@SecurityOptions(roles = { UserRole.ROOT} )
	public Response suspendUser(@Valid @RequestBody DeleteUserRequest request) {
		return userService.deleteUser(request);
	}

	@PostMapping(value = "change-admin-password")
	@SecurityOptions(roles = { UserRole.ROOT} )
	public Response changeAdminPassword(@Valid @RequestBody ChangeAdminPasswordRequest request) {
		return userService.changeAdminPassword(request);
	}

	@PostMapping(value = "accounts-list")
	@SecurityOptions(roles = { UserRole.ROOT })
	public UsersListResponse accountsList(@Valid @RequestBody UsersListRequest request) {
		return userService.accountsList(request);
	}
	

	/*
	 * 
	 * @PostMapping(value = "register-user")
	@SecurityOptions(enableBlocking = true, enableAuthorization = false, enableRoles = false)
	public Response clientRegister(@Valid @RequestBody UserRegisterRequest request) {
		return userService.clientRegister(request);
	}
	@PostMapping(value = "activate-user")
	@SecurityOptions(roles = { UserRole.ADMIN, UserRole.ROOT })
	public Response activateUser(@Valid @RequestBody ActivateUserRequest request) {
		return userService.activateUser(request);
	}
	
		@PostMapping(value = "suspend-user")
	@SecurityOptions(roles = { UserRole.ROOT, UserRole.ADMIN })
	public Response suspendUser(@Valid @RequestBody SuspendUserRequest request) {
		return userService.suspendUser(request);
	}


@PostMapping(value = "users-list")
	@SecurityOptions(roles = { UserRole.ADMIN, UserRole.ROOT })
	public UsersListResponse usersList(@Valid @RequestBody UsersListRequest request) {
		return userService.usersList(request);
	}

	*/



	

}
