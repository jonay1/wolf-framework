package com.wolf.app.web.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import com.wolf.app.comm.job.JobExecutor;
import com.wolf.app.configuration.ScheduleConfiguration;
import com.wolf.app.web.service.QuartzService;
import com.wolf.core.base.TaskDetail;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConditionalOnBean(ScheduleConfiguration.class)
@Setter
public class QuartzServiceImpl implements QuartzService {

	@Autowired(required = false)
	private Scheduler scheduler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wolf.app.component.schedule.service.QuartzService#getJobStatus(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public String getJobStatus(String group, String name) {
		TriggerState triggerState = TriggerState.NONE;
		TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
		try {
			triggerState = scheduler.getTriggerState(triggerKey);
		} catch (Exception e) {
			log.error("{}", triggerKey, e);
		}
		return triggerState.name();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wolf.app.component.schedule.service.QuartzService#pauseJob(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public boolean pauseJob(String group, String name) {
		JobKey jobKey = new JobKey(name, group);
		try {
			scheduler.pauseJob(jobKey);
			return true;
		} catch (Exception e) {
			log.error("{}", jobKey, e);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wolf.app.component.schedule.service.QuartzService#deleteJob(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public boolean deleteJob(String group, String name) {
		JobKey jobKey = new JobKey(name, group);
		try {
			return scheduler.deleteJob(jobKey);
		} catch (Exception e) {
			log.error("{}", jobKey, e);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wolf.app.component.schedule.service.QuartzService#resumeJob(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public boolean resumeJob(String group, String name) {
		JobKey jobKey = new JobKey(name, group);
		try {
			scheduler.resumeJob(jobKey);
			return true;
		} catch (Exception e) {
			log.error("{}", jobKey, e);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wolf.app.component.schedule.service.QuartzService#triggerJob(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void triggerJob(String group, String name) {
		JobKey jobKey = new JobKey(name, group);
		try {
			scheduler.triggerJob(jobKey);
		} catch (Exception e) {
			log.error("{}", jobKey, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wolf.app.component.schedule.service.QuartzService#pauseAll()
	 */
	@Override
	public boolean pauseAll() {
		try {
			scheduler.pauseAll();
			return true;
		} catch (Exception e) {
			log.error("", e);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wolf.app.component.schedule.service.QuartzService#resumeAll()
	 */
	@Override
	public boolean resumeAll() {
		try {
			scheduler.resumeAll();
			return true;
		} catch (Exception e) {
			log.error("", e);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wolf.app.component.schedule.service.QuartzService#startJob(com.wolf.app.
	 * module.schedule.base.TaskDetail)
	 */
	@Override
	public boolean startJob(TaskDetail job) {
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), job.getGroupName());
		CronTrigger trigger = null;
		try {
			trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == trigger) {
				JobDetail jobDetail = JobBuilder.newJob(JobExecutor.class).withIdentity(job.getId(), job.getGroupName()).build();
				jobDetail.getJobDataMap().put(JOBBEAN_KEY, job);
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
				TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(job.getId(), job.getGroupName()).withSchedule(scheduleBuilder);
				trigger = triggerBuilder.build();
				scheduler.scheduleJob(jobDetail, trigger);

			} else {
				// ignore
				log.warn("已存在任务{}", job.getName());
			}
			return true;
		} catch (Exception e) {
			log.error("start job {}.{} failed", job.getGroupName(), job.getName(), e);
			return false;
		}

	}

	@Override
	public Object getSchedulerMetaData() {
		try {
			return scheduler.getMetaData();
		} catch (Exception e) {
			return null;
		}
	}

}
