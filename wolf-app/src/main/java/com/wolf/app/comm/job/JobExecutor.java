package com.wolf.app.comm.job;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.util.StopWatch;

import com.wolf.app.web.service.QuartzService;
import com.wolf.core.base.TaskDetail;
import com.wolf.core.utils.ContextUtil;
import com.wolf.core.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Slf4j
public class JobExecutor implements Job {

	public static final String EXEC_RESULT = "_RESULT_";

	@Override
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap dataMap = jobDetail.getJobDataMap();
		TaskDetail task = (TaskDetail) dataMap.get(QuartzService.JOBBEAN_KEY);
		TaskStatus status = TaskStatus.IGNORE;
		String message = null;
		Long cost = null;
		if (StringUtils.isBlank(task.getBeanName())) {
			message = "配置不正确";
			log.warn("#########任务[{}-{}]配置不正确，忽略#########", task.getId(), task.getName());
		} else {
			StopWatch sw = new StopWatch();
			try {
				// FIXME
				// TaskJob job = ClassUtil.newInstance(task.getBeanName());
				// factory.autowireBean(job);
				TaskJob job = ContextUtil.getBean(task.getBeanName(), TaskJob.class);
				log.info("#########任务[{}-{}]执行开始#########", task.getId(), task.getName());
				try {
					sw.start();
					job.execute(dataMap);
					sw.stop();
					status = TaskStatus.SUCCESS;
					message = "执行成功";
					cost = sw.getTotalTimeMillis();
					log.info("#########任务[{}-{}]执行成功,用时:{}s#########", task.getId(), task.getName(), sw.getTotalTimeSeconds());
				} catch (Exception e) {
					status = TaskStatus.FAIL;
					message = StringUtils.abbreviate(e.getMessage(), 1000);
					log.error("#########任务[{}-{}]执行异常#########", task.getId(), task.getName(), e);
				}

			} catch (Exception e) {
				message = "任务不存在";
				log.error("#########任务[{}-{}]初始化异常#########", task.getId(), task.getName(), e);
			}
		}
		JobState state = new JobState();
		state.setTaskId(task.getId());
		state.setStatus(status);
		state.setCost(cost);
		state.setMessage(message);
		String fireTime = DateUtil.format(context.getFireTime(), DateUtil.yyyy_MM_dd_HH_mm_ss);
		state.setFireTime(fireTime);
		dataMap.put(EXEC_RESULT, state);
	}

}
