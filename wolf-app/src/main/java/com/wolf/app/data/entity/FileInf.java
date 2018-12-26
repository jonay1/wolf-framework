package com.wolf.app.data.entity;

import javax.persistence.Table;

import com.wolf.dao.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name = "T_FILE_INF")
@Getter
@Setter
public class FileInf extends BaseEntity {

	private String relId;

	private String relType;

	private String filename;

	private String filepath;

	private long filesize;
	
	private String contentType;

}