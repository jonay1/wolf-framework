package com.wolf.app.web.service;

import org.springframework.scheduling.annotation.Async;

import com.wolf.app.comm.job.JobState;

public interface LogService {

	@Async("myAsync")
	void logAccess(String path, long cost, boolean success);

	@Async("myAsync")
	void logTaskJob(JobState state);
}
