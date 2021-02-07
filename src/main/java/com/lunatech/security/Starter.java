package com.lunatech.security;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.lunatech.security.authorization.AuthorizationService;
import com.lunatech.security.user.UserService;

@Component
public class Starter implements CommandLineRunner {

	public Starter() {
	}

	@Autowired
	private UserService userService;

	@Value("${system.jwt.secret}")
	private String jwtSecret;

	// in ms
	@Value("${system.jwt.expire}")
	private Long expireInterval;

	
	@Value("${system.allowed.systems}")
	private String allowedSystems;
	
	/**
	 * 1-create default root user at first time 2-create store directory if not
	 * created
	 */
	@Override
	public void run(String... args) throws Exception {
		
		AuthorizationService.configure(jwtSecret, expireInterval,  Arrays.asList(allowedSystems.split(","))  );
		userService.registerDefaultRootAccount();
	}

}
