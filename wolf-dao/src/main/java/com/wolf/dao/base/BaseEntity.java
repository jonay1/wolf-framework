package com.wolf.dao.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Id
	private String id;
	
	@Column(updatable = false)
	private String createTime;
	
	@Column(updatable = false)
	private String creator;

	private String updateTime;

	private String updator;
	
	private String rmk;
}
