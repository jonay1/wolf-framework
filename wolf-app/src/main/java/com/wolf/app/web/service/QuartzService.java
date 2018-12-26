package com.wolf.app.web.service;

import org.springframework.scheduling.annotation.Async;

import com.wolf.core.base.TaskDetail;

public interface QuartzService {

	String JOBBEAN_KEY = "job-bean";

	String getJobStatus(String group, String name);

	boolean pauseJob(String group, String name);

	boolean deleteJob(String group, String name);

	boolean resumeJob(String group, String name);

	void triggerJob(String group, String name);

	/**
	 * 暂停调度中所有的job任务
	 * 
	 */
	@Async("quartzAysnc")
	boolean pauseAll();

	/**
	 * 恢复调度中所有的job的任务
	 * 
	 */
	@Async("quartzAysnc")
	boolean resumeAll();

	boolean startJob(TaskDetail job);

	Object getSchedulerMetaData();

}