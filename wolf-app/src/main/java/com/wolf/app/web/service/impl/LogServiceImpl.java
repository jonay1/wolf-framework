package com.wolf.app.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.app.comm.job.JobState;
import com.wolf.app.data.entity.AccessLog;
import com.wolf.app.data.entity.TaskLog;
import com.wolf.app.web.service.LogService;
import com.wolf.core.consts.Consts;
import com.wolf.core.utils.DateUtil;
import com.wolf.dao.base.Dao;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogServiceImpl implements LogService {

	@Autowired
	private Dao<AccessLog> accessLog;
	@Autowired
	private Dao<TaskLog> taskLog;

	@Override
	public void logAccess(String path, long cost, boolean success) {
		try {
			AccessLog log = new AccessLog();
			log.setRequri(path);
			log.setProcCost(cost);
			log.setSuccess(success ? Consts.YES : Consts.NO);
			String dateAndTime = DateUtil.date19();
			String[] s = dateAndTime.split("[-: ]");
			log.setAccessYear(s[0]);
			log.setAccessMonth(s[1]);
			log.setAccessDay(s[2]);
			log.setAccessHour(s[3]);
			log.setAccessMinute(s[4]);
			log.setAccessSecond(s[5]);
			accessLog.save(log);

		} catch (Throwable e) {
			log.warn("", e);
		}
	}

	@Override
	public void logTaskJob(JobState state) {
		try {
			TaskLog log = new TaskLog();
			log.setTaskId(state.getTaskId());
			log.setExecResult(state.getMessage());
			log.setExecTime(state.getCost());
			log.setExecStatus(state.getStatus().name());
			log.setFireTime(state.getFireTime());
			taskLog.save(log);
		} catch (Throwable e) {
			log.warn("", e);
		}
	}

}
