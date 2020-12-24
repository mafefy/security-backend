package com.lunatech.security.blocking.dao;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lunatech.security.common.dao.CommonEntity;

import lombok.Data;

@Data
@Entity
public class BlockingOperationEntity extends CommonEntity {

	private String ip;
	private String operation;
	private Long violations;
	private Long maxViolations;
	private Boolean reachedMaximum;

	public BlockingOperationEntity(String ip, String operation, Long maxViolations) {
		this.ip = ip;
		this.operation = operation;
		this.violations = 1L;
		this.maxViolations = maxViolations;
		checkCrossedTheLine();
	}

	public BlockingOperationEntity() {
	}

	public void addViolation() {
		if ( violations != null ) {			
			++violations;
			checkCrossedTheLine();
		}
	}

	public void checkCrossedTheLine() {	
		reachedMaximum = violations >= maxViolations ;
	}
}
