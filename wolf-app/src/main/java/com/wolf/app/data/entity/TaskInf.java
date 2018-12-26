package com.wolf.app.data.entity;

import javax.persistence.Table;

import com.wolf.core.base.TaskDetail;
import com.wolf.core.consts.Consts;
import com.wolf.dao.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name = "T_TASK_INF")
@Getter
@Setter
public class TaskInf extends BaseEntity implements TaskDetail {

	private String groupName;

	private String name;

	private String cron;

	private String dolog;

	private String beanName;

	@Override
	public boolean loggable() {
		return Consts.YES.equals(dolog);
	}

}