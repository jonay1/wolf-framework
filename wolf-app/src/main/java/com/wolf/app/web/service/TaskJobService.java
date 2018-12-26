package com.wolf.app.web.service;

import java.util.List;

import com.wolf.app.data.entity.TaskInf;

public interface TaskJobService {
	
	List<TaskInf> findAll();

	void startAll();

	void start(String taskId);

	void reset();

	void delete(String group, String taskId);

}
