package com.lunatech.security.user.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;

import com.lunatech.security.common.dao.CommonEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)

@JsonIgnoreProperties( value = {"hash"} , allowGetters =  false , allowSetters =  false)
public class UserCommonEntity extends CommonEntity {

	@Column(unique = true, nullable = false)
	private String name;
	@Column(nullable = false)
	private String hash;

	private String middleName;
	private String lastName;
	private Boolean activated;
	private Boolean suspended;
	private String address;
	private String role;
	private String statusMessage;

}
