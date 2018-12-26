//package com.wolf.app.comm.job;
//
//import org.quartz.spi.JobFactory;
//import org.quartz.spi.TriggerFiredBundle;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.scheduling.quartz.AdaptableJobFactory;
//import org.springframework.stereotype.Component;
//
//import lombok.Setter;
//
//@Component
//@ConditionalOnClass(JobFactory.class)
//@Setter
//public class SpringJobFactory extends AdaptableJobFactory {
//	@Autowired
//	private AutowireCapableBeanFactory capableBeanFactory;
//
//	@Override
//	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
//		Object jobInstance = super.createJobInstance(bundle);
//		capableBeanFactory.autowireBean(jobInstance);
//		return jobInstance;
//	}
//
//}
