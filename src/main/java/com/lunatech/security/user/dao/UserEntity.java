package com.lunatech.security.user.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value  = { "hash" }, allowGetters = false, allowSetters = false)
public class UserEntity extends UserCommonEntity {

	/*
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<ChatEntity> chats = new ArrayList<ChatEntity>();
	*/

}
