package com.wolf.app.comm.job;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "任务", description = "返回对象")
public class TaskInfBean {
	private String group;

	private String name;
	
	private String desc;

	private String cron;

	private String className;

	private String status;

	private String statusName;
}
