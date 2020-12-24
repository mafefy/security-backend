package com.lunatech.security.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityOptions {
	
	/**
	 * list of accessible roles 
	 * @return
	 */
	UserRole [] roles() default {};
	/**
	 * enable blocking features to prevent suspecious unsecure operations
	 * @return
	 */
	boolean enableBlocking() default true;
	/**
	 * check if authorization token is valid 
	 * @return
	 */
	boolean enableAuthorization() default true;
	/*
	 * check if request has right roles 
	 */
	boolean enableRoles() default false;
}
