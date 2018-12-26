package com.wolf.app.data.entity;

import javax.persistence.Table;

import com.wolf.dao.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name = "T_TASK_LOG")
@Getter
@Setter
public class TaskLog extends BaseEntity {
	private String taskId;
	private Long execTime;
	private String execStatus;
	private String execResult;
	private String fireTime;
}