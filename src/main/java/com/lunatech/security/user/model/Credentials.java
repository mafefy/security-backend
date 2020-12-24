package com.lunatech.security.user.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lunatech.security.common.dao.AuthenticatedRequest;
import com.lunatech.security.common.model.Request;

import lombok.Data;

/**
 * 
 * @author vedusoft2016@gmail.com client regiesteration if self registeration
 *         enabled
 *
 */
@Data
public class Credentials {
	@NotBlank
	@Size(min = 2, max = 512 )
	private String name;
	@NotBlank
	@Size(min = 4, max = 512)
	private String password;

	public Credentials() {

	}

	public Credentials(String name, String password) {
		this.name = name;
		this.password = password;
	}

}
