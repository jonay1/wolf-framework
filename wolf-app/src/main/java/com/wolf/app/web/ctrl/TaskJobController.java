package com.wolf.app.web.ctrl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.app.comm.job.TaskBean;
import com.wolf.app.comm.job.TaskInfBean;
import com.wolf.app.web.service.QuartzService;
import com.wolf.app.web.service.TaskJobService;
import com.wolf.core.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/job")
@Api(tags = "定时任务管理")
public class TaskJobController {

	@Autowired(required = false)
	private TaskJobService jobService;

	@Autowired(required = false)
	private QuartzService quartzService;

	@RequestMapping(path = "/info", method = RequestMethod.GET)
	public Object info() throws Exception {
		return quartzService.getSchedulerMetaData();
	}

	@ApiOperation("获取所有任务信息")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	@ApiResponses(@ApiResponse(code = 200, message = "处理成功", responseContainer = "list", response = TaskInfBean.class))
	public List<TaskInfBean> listAllJob() {
		List<TaskInfBean> list = CommonUtil.foreach(jobService.findAll(), t -> {
			TaskInfBean bean = new TaskInfBean();
			bean.setGroup(t.getGroupName());
			bean.setName(t.getId());
			bean.setDesc(t.getName());
			bean.setCron(t.getCron());
			String status = quartzService.getJobStatus(t.getGroupName(), t.getId());
			bean.setStatus(status);
			return bean;
		});
		return list;
	}

	@ApiOperation("清理任务缓存")
	@RequestMapping(path = "/reset", method = RequestMethod.POST)
	public void reset() {
		jobService.reset();
	}

	@ApiOperation("暂时所有任务")
	@RequestMapping(path = "/pauseAll", method = RequestMethod.POST)
	public void pauseAll() throws Exception {
		quartzService.pauseAll();
	}

	@ApiOperation("暂时指定任务")
	@RequestMapping(path = "/pause", method = RequestMethod.POST)
	public void pause(@Valid @RequestBody TaskBean bean) throws Exception {
		quartzService.pauseJob(bean.getGroup(), bean.getTaskId());
	}

	@ApiOperation("恢复所有任务")
	@RequestMapping(path = "/resumeAll", method = RequestMethod.POST)
	public void resumeAll() throws Exception {
		quartzService.resumeAll();
	}

	@ApiOperation("恢复指定任务")
	@RequestMapping(path = "/resume", method = RequestMethod.POST)
	public void resume(@Valid @RequestBody TaskBean bean) throws Exception {
		quartzService.resumeJob(bean.getGroup(), bean.getTaskId());
	}

	@ApiOperation("启动所有任务")
	@RequestMapping(path = "/startAll", method = RequestMethod.POST)
	public void start() throws Exception {
		jobService.startAll();
	}

	@ApiOperation("启动指定任务")
	@RequestMapping(path = "/start", method = RequestMethod.POST)
	public void start(@Valid @RequestBody TaskBean bean) throws Exception {
		jobService.start(bean.getTaskId());
	}

	@ApiOperation("删除任务")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	public void delete(@Valid @RequestBody TaskBean bean) throws Exception {
		jobService.delete(bean.getGroup(), bean.getTaskId());
	}
}
