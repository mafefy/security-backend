package com.lunatech.security.authorization;

import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lunatech.security.common.error.AppError;
import com.lunatech.security.common.error.AuthenticationError;
import com.lunatech.security.common.error.AuthenticationExpiredError;
import com.lunatech.security.common.error.BlockedError;
import com.lunatech.security.common.error.NotAllowedError;
import com.lunatech.security.blocking.BlockingOperationService;

@Component
@Aspect
public class SecurityAspect {

	@Autowired
	private BlockingOperationService blockingService;

	/**
	 * inspect for token check if user is authorized with the correct role
	 * 
	 * @param joinPoint reference for the target function
	 * @throws Throwable
	 */
	@Around("@annotation(com.lunatech.security.authorization.SecurityOptions)")
	public Object logAroundAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		checkConnectionSafety(joinPoint);
		return checkAuthroization(joinPoint);
	}

	/**
	 * check if connection is safe and not blocked if blocked throws BlockedError
	 * exception
	 * 
	 * @param securityOptions
	 * @throws BlockedError
	 */
	private void checkConnectionSafety(ProceedingJoinPoint joinPoint) throws BlockedError {
		SecurityOptions securityOptions = getSecurityOptions(joinPoint);
		if (securityOptions.enableBlocking()) {
			if (blockingService.isConnectionBlocked()) {
				throw new BlockedError();
			}
		}
	}
	

	private Object checkAuthroization(ProceedingJoinPoint joinPoint) throws Throwable {
		SecurityOptions securityOptions = getSecurityOptions(joinPoint);
		if (securityOptions.enableAuthorization()) {
			String token = searchForToken(joinPoint);
			AuthorizationStatus status = autheticationService.getStatus(token);
			if (status.getAuthorized()) {
				return processAuthroizedRequest(joinPoint, status);
			} else {
				processNotAuthorizedRequest(status);
			}

		} else {
			return joinPoint.proceed(joinPoint.getArgs());
		}

		return null;
	}
	
	
	/*
	 * search for token 
	 * 1- serach first in the function parameters as passed json objects
	 * 2- search if passed in http headers
	 * 3- search if passed as query pamramer
	 */
	private String searchForToken(ProceedingJoinPoint joinPoint) {
		String token = findTokenInFuncParameters(joinPoint.getArgs());
		if (token == null) {
			token = findTokenInHeaders();
		}
		
		if (token == null) {
			token = findTokenInQueryParameters();
		}
		return token;
	}
	
	private Object processAuthroizedRequest(ProceedingJoinPoint joinPoint, AuthorizationStatus status)
			throws Throwable {
		if (hasRightRole(joinPoint, status) && (!status.getSuspended())) {
			injectStatusInRequest(joinPoint.getArgs(), status);
			return joinPoint.proceed(joinPoint.getArgs());
		} else {
			throw new NotAllowedError();
		}
	}

	/**
	 * if token expired throws expired error exception and if token is not valid
	 * throws authentication error
	 * 
	 * @param status
	 * @throws AppError
	 */
	private void processNotAuthorizedRequest(AuthorizationStatus status) throws AppError {
		if (status.getExpired()) {
			throw new AuthenticationExpiredError();
		} else {
			throw new AuthenticationError();
		}
	}

	private SecurityOptions getSecurityOptions(ProceedingJoinPoint joinPoint) {
		MethodSignature sig = ((MethodSignature) joinPoint.getSignature());
		return sig.getMethod().getAnnotation(SecurityOptions.class);
	}

	/**
	 * check if request has correct role against the SecurityOptions.roles() request
	 * role is fetched from AuthorizationStatus payload annotated function
	 */
	private boolean hasRightRole(ProceedingJoinPoint joinPoint, AuthorizationStatus status) {
		SecurityOptions securityOptions = getSecurityOptions(joinPoint);

		if (securityOptions.enableRoles()) {
			for (UserRole userRole : securityOptions.roles()) {
				if (userRole.role.equals(status.getRole())) {
					return true;
				}
			}
		} else {// no roles checking
			return true;
		}
		return false;
	}

	/*@Autowired
	private AuthorizationService autheticationService;*/
	private AuthorizationService autheticationService = new AuthorizationService();

	/**
	 * inspect the function parameters to find the request object and extract the
	 * token
	 */
	private String findTokenInFuncParameters(Object[] args) {

		for (Object obj : args) {
			Field tokenField = findFieldInClassHierarchy(TOKEN_KEY , obj);
			if (tokenField != null) {
				try {
					String token = (String) tokenField.get(obj);
					if (token != null) {
						return token;
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}
		}
		return null;
	}

	public static String TOKEN_KEY = "token";
	@Autowired
	private HttpServletRequest httpRequest;

	private String findTokenInHeaders() {
		return httpRequest.getHeader(TOKEN_KEY);
	}
	
	private String findTokenInQueryParameters() {
		return httpRequest.getParameter(TOKEN_KEY );
	}

	private void injectStatusInRequest(Object[] args, AuthorizationStatus status) {

		for (Object obj : args) {
			Field statusField = findFieldInClassHierarchy("status", obj);
			if (statusField != null) {
				try {
					statusField.set(obj, status);
					return;
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}

		}
	}

	/**
	 * search for filedName in targetObject until reach it's base class
	 * 
	 * @param fieldName
	 * @param targetObj
	 * @return field object
	 */

	private Field findFieldInClassHierarchy(String fieldName, Object targetObj) {
		Class<?> classType = targetObj.getClass();
		do {
			Field field;
			try {
				field = classType.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (Exception e) {
			}

		} while ((classType = classType.getSuperclass()) != null);
		return null;
	}

}
