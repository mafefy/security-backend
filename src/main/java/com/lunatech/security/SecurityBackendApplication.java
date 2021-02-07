package com.lunatech.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication( scanBasePackages =  {SecurityBackendApplication.packageName , SecurityAspects.packageName})
@EnableJpaRepositories( basePackages =  {SecurityBackendApplication.packageName , SecurityAspects.packageName})
@EntityScan( basePackages =  {SecurityBackendApplication.packageName , SecurityAspects.packageName} )

public class SecurityBackendApplication {

	public static final String packageName = "com.lunatech.security";
	public static void main(String[] args) {
		SpringApplication.run(SecurityBackendApplication.class, args);
	}

}
