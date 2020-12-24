package com.lunatech.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SecurityBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityBackendApplication.class, args);
	}

}
