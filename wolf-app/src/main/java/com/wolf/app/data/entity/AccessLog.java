package com.wolf.app.data.entity;

import javax.persistence.Table;

import com.wolf.dao.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name = "T_ACCESS_LOG")
@Getter
@Setter
public class AccessLog extends BaseEntity {

	private String requri;
	private Long procCost;
	private String success;
	private String accessYear;
	private String accessMonth;
	private String accessDay;
	private String accessHour;
	private String accessMinute;
	private String accessSecond;

}