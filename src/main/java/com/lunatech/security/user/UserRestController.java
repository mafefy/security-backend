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
import com.lunatech.security.user.model.AddAdminRequest;
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
	public AuthenticatedResponse login(@Valid @RequestBody LoginRequest request) {
		return userService.login(request);
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

	@PostMapping(value = "suspend-admin")
	@SecurityOptions(roles = { UserRole.ROOT })
	public Response suspendAdmin(@Valid @RequestBody SuspendUserRequest request) {
		return userService.suspendAdmin(request);
	}
	


	@PostMapping(value = "add-admin")
	@SecurityOptions(roles = { UserRole.ROOT })
	public Response addAdmin(@Valid @RequestBody AddAdminRequest request) {
		return userService.addAdmin(request);
	}
	
	@PostMapping(value = "delete-admin")
	@SecurityOptions(roles = { UserRole.ROOT} )
	public Response suspendUser(@Valid @RequestBody DeleteUserRequest request) {
		return userService.deleteUser(request);
	}

	@PostMapping(value = "change-admin-password")
	@SecurityOptions(roles = { UserRole.ROOT} )
	public Response changeAdminPassword(@Valid @RequestBody ChangeAdminPasswordRequest request) {
		return userService.changeAdminPassword(request);
	}

	@PostMapping(value = "admins-list")
	@SecurityOptions(roles = { UserRole.ROOT })
	public UsersListResponse adminsList(@Valid @RequestBody UsersListRequest request) {
		return userService.adminsList(request);
	}

	

	@GetMapping(value = "test")
	public String test() {

		return "yepy";
	}
}
