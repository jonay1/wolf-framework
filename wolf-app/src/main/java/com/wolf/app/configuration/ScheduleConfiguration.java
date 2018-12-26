package com.wolf.app.configuration;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.quartz.TriggerListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.wolf.app.comm.job.GlobalTriggerListener;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnClass(Scheduler.class)
@EnableScheduling
@Setter
public class ScheduleConfiguration {

	/**
	 * 调度工厂bean
	 * 
	 * @param datasource
	 * 
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactory(TriggerListener globalTriggerListener, DataSource datasource) {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		// schedulerFactory.setJobFactory(springJobFacotry);
		// 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
		schedulerFactory.setOverwriteExistingJobs(true);
		schedulerFactory.setGlobalTriggerListeners(globalTriggerListener);
		schedulerFactory.setStartupDelay(10);
		schedulerFactory.setDataSource(datasource);
		schedulerFactory.setAutoStartup(true);
		schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
		// 延时启动，应用启动1秒后
		schedulerFactory.setStartupDelay(20);
		log.info("scheduler inited.");
		return schedulerFactory;
	}

	@Bean
	public Scheduler scheduler(SchedulerFactoryBean schedulerFactory) {
		Scheduler scheduler = schedulerFactory.getScheduler();
		return scheduler;
	}

	@Bean
	@ConditionalOnMissingBean
	public TriggerListener globalTriggerListener() {
		return new GlobalTriggerListener();
	}
}
