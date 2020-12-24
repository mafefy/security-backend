package com.lunatech.security.user.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	public UserEntity findByName(String name);

	public Long countByActivated(Boolean activated);

	public Long countByRole(String role);

	public List<UserEntity> findByRoleAndNameContaining(String role , String name, Pageable pageable);

}

