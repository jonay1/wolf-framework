package com.wolf.app.comm.job;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.wolf.app.web.service.LogService;
import com.wolf.app.web.service.QuartzService;
import com.wolf.core.base.TaskDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalTriggerListener implements TriggerListener {

	@Autowired
	private LogService logService;

	@Override
	public String getName() {
		return "global-trigger-listener@default";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		log.info("triggerFired");
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		log.info("vetoJobExecution");
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		log.info("triggerMisfired");

	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		log.info("triggerComplete");
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap dataMap = jobDetail.getJobDataMap();
		TaskDetail task = (TaskDetail) dataMap.get(QuartzService.JOBBEAN_KEY);
		if (task.loggable()) {
			JobState state = (JobState) dataMap.get(JobExecutor.EXEC_RESULT);
			logService.logTaskJob(state);
		}
	}

}
