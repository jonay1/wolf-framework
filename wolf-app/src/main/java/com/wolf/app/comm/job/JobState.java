package com.wolf.app.comm.job;

import java.io.Serializable;

import lombok.Data;

@Data
public class JobState implements Serializable {
	private static final long serialVersionUID = 6821301906136230175L;
	private String taskId;
	private TaskStatus status;
	private String message;
	private Long cost;
	private String fireTime;
}
