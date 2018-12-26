package com.wolf.app.comm.job;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "任务", description = "请求对象")
public class TaskBean {
	@NotBlank
	@ApiModelProperty(value = "任务组", required = true, example = "DEFAULT")
	private String group;
	@NotBlank
	@ApiModelProperty(value = "任务ID", required = true, example = "timer")
	private String taskId;
}
