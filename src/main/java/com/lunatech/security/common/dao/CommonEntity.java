package com.lunatech.security.common.dao;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@MappedSuperclass
@Data()
public class CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date editedDate;

	public void updateAuditing() {
		Date now = new Date();
		createdDate = now;
		editedDate = now;
	}

	public void updateEditingDate(Date date) {
		this.editedDate = date;
	}

	public void updateCreationDate(Date date) {
		this.createdDate = date;
	}

}
