package com.wolf.app.data.entity;

import javax.persistence.Table;

import com.wolf.dao.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name = "T_SYS_PARAM")
@Getter
@Setter
public class SysParam extends BaseEntity {

	private String paramType;
	private String paramKey;
	private String paramName;
	private String paramVal;
	private String status;

}