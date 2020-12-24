package com.lunatech.security.user;

import java.util.Date;
import java.util.List;

import javax.management.relation.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.lunatech.security.common.error.AuthenticationError;
import com.lunatech.security.common.error.ExistError;
import com.lunatech.security.common.error.FullError;
import com.lunatech.security.common.error.NotAllowedError;
import com.lunatech.security.common.error.NotCompleteError;
import com.lunatech.security.common.model.Response;
import com.lunatech.security.authorization.AuthorizationService;
import com.lunatech.security.authorization.AuthorizationStatus;
import com.lunatech.security.authorization.UserRole;
import com.lunatech.security.blocking.Violations;
import com.lunatech.security.user.dao.UserEntity;
import com.lunatech.security.user.dao.UserInfo;
import com.lunatech.security.user.dao.UserRepository;
import com.lunatech.security.user.model.ActivateUserRequest;
import com.lunatech.security.user.model.AddAdminRequest;
import com.lunatech.security.user.model.AuthenticatedResponse;
import com.lunatech.security.user.model.ChangeAdminPasswordRequest;
import com.lunatech.security.user.model.ChangePasswordRequest;
import com.lunatech.security.user.model.Credentials;
import com.lunatech.security.user.model.DeleteUserRequest;
import com.lunatech.security.user.model.LoginRequest;
import com.lunatech.security.user.model.SuspendUserRequest;
import com.lunatech.security.user.model.UserRegisterRequest;
import com.lunatech.security.user.model.UsersListRequest;
import com.lunatech.security.user.model.UsersListResponse;

@Service()
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserBuilder userBuilder;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Enable users to register themselves if self registration enabled Prevent
	 * system from accepting more users if reached maximum pending registration
	 * 
	 * @param request contains self registration info
	 * @return Operation Response
	 */
	public Response clientRegister(UserRegisterRequest request) {
		Response response = new Response();
		if (maximumPendingRegisterations()) {
			throw new FullError();
		} else {

			if (userExist(request.getCredentials().getName())) {
				throw new ExistError();
			} else {
				UserEntity user = userBuilder.buildUser(request.getCredentials(), false, UserRole.USER);
				userRepository.save(user);
				response.setSuccess(true);
			}
		}
		return response;
	}

	private boolean userExist(String name) {
		return findUser(name) != null;
	}

	@Value("${system.registration.pending.max}")
	private Long maxPendingRegisterations;

	private boolean maximumPendingRegisterations() {
		return userRepository.countByActivated(false) >= maxPendingRegisterations;
	}

	private UserEntity findUser(Credentials creds) {
		return findUser(creds.getName());
	}

	private UserEntity findUser(String name) {
		return userRepository.findByName(name);
	}

	@Value("${system.admin.root.user}")
	private String rootName;
	@Value("${system.admin.root.password}")
	private String rootPassword;

	/**
	 * 
	 * create root owner account with credentials from application.properties
	 * created once when the app start for first time
	 */
	public void registerDefaultRootAccount() {
		if (noRootRegisteredYet()) {
			UserEntity root = userBuilder.buildRoot(rootName, rootPassword);
			userRepository.save(root);
			logger.info("System initialize root account for you based on your settings");
			logger.info("Root name:" + rootName + " password:" + rootPassword);
		} else {
			logger.info("System already has root account");
		}

	}

	public boolean noRootRegisteredYet() {
		return userRepository.countByRole(UserRole.ROOT.role) == 0;
	}

	private AuthorizationService authenticationService = new AuthorizationService();

	public AuthenticatedResponse login(LoginRequest req) {
		AuthenticatedResponse response = new AuthenticatedResponse();
		UserEntity user = findUser(req.getCredentials());
		if (user != null) {
			// check user password
			if (user.getHash().equals(userBuilder.getHash(req.getCredentials()))) {
				if (user.getActivated()) {
					if (user.getSuspended()) {
						throw new NotAllowedError(Violations.LOGIN);
					} else {
						response.setUser(user);
						response.setToken(authenticationService.generateToken(convert(user)));
						response.setSuccess(true);
						return response;
					}
				} else {
					throw new NotCompleteError();
				}
			}
		}
		throw new AuthenticationError();
	}
	

	private UserInfo convert(UserEntity entity) {
			UserInfo info = new UserInfo();
			info.setId(entity.getId());
			info.setName(entity.getName());
			info.setRole(entity.getRole());
			info.setSuspended(entity.getSuspended());
			return info;
	}

	/**
	 * set user to be active accessible by root and admin
	 * 
	 * @param request
	 * @return
	 */
	public Response activateUser(ActivateUserRequest request) {

		Response response = new Response();
		UserEntity targetUser = userRepository.findById(request.getId()).get();
		targetUser.setActivated(true);
		targetUser.updateEditingDate(new Date());
		userRepository.save(targetUser);
		response.setSuccess(true);
		return response;
	}

	public Response deleteUser(DeleteUserRequest request) {

		Response response = new Response();
		userRepository.deleteById(request.getId());
		response.setSuccess(true);
		return response;
	}

	/**
	 * suspend normal user by ( admin or root )
	 * 
	 * @param request
	 * @return
	 */
	public Response suspendUser(SuspendUserRequest request) {
		return editSuspend(request, UserRole.USER);
	}

	/**
	 * suspend admin by ( root )
	 * 
	 * @param request
	 * @return
	 */
	public Response suspendAdmin(SuspendUserRequest request) {
		return editSuspend(request, UserRole.ADMIN);
	}

	public Response editSuspend(SuspendUserRequest request, UserRole targetUserRole) {

		Response response = new Response();
		UserEntity targetUser = userRepository.findById(request.getId()).get();
		if (targetUserRole.hasRole(targetUser.getRole())) {
			targetUser.setSuspended(request.getSuspend());
			targetUser.updateEditingDate(new Date());
			userRepository.save(targetUser);
			response.setSuccess(true);
		} else {
			throw new NotAllowedError();
		}
		return response;
	}

	/*
	 * roots only can add admins by credentials
	 */
	public Response addAdmin(AddAdminRequest request) {
		Response response = new Response();

		if (userExist(request.getCredentials().getName())) {
			throw new ExistError();
		} else {
			UserEntity user = userBuilder.buildUser(request.getCredentials(), true, UserRole.ADMIN);
			userRepository.save(user);
			response.setSuccess(true);
		}

		return response;
	}

	/**
	 * change my password using old and new password
	 * 
	 * @param request
	 * @return
	 */

	public Response changePassword(ChangePasswordRequest request) {
		Response response = new Response();
		AuthorizationStatus status = request.getStatus();
		String currentHash = userBuilder.getHash(status.getName(), request.getCurrentPassword());
		UserEntity user = userRepository.findById(status.getUserId()).get();
		if (user.getHash().equals(currentHash)) {
			String newHash = userBuilder.getHash(status.getName(), request.getNewPassword());
			user.setHash(newHash);
			userRepository.save(user);
			response.setSuccess(true);
		} else {
			throw new AuthenticationError();
		}

		return response;
	}

	public Response changeAdminPassword(ChangeAdminPasswordRequest request) {
		Response response = new Response();
		UserEntity admin = userRepository.findById(request.getId()).get();
		String newHash = userBuilder.getHash(admin.getName(), request.getNewPassword());
		admin.setHash(newHash);
		userRepository.save(admin);
		response.setSuccess(true);

		return response;
	}

	/**
	 * search for users by name , pageable ( index , size )
	 * 
	 * @param request
	 * @return
	 */
	public UsersListResponse getUsers(UsersListRequest request, UserRole userRole) {
		UsersListResponse response = new UsersListResponse();
		response.setTotalRecords(userRepository.countByRole(userRole.role));
		List<UserEntity> users = userRepository.findByRoleAndNameContaining(userRole.role, request.getName(),
				PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(Direction.DESC, "createdDate")));
		response.setUsers(users);
		response.setSuccess(true);
		return response;
	}

	public UsersListResponse adminsList(UsersListRequest request) {
		return getUsers(request, UserRole.ADMIN);
	}

	public UsersListResponse usersList(UsersListRequest request) {
		return getUsers(request, UserRole.USER);
	}

}
