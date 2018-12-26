package com.wolf.app.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wolf.app.data.entity.TaskInf;
import com.wolf.app.web.service.QuartzService;
import com.wolf.app.web.service.TaskJobService;
import com.wolf.core.consts.Consts;
import com.wolf.dao.base.Dao;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskJobServiceImpl implements TaskJobService {

	@Autowired
	private Dao<TaskInf> dao;
	@Autowired(required = false)
	private QuartzService quartz;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<TaskInf> findAll() {
		return dao.findAll();
	}

	@Override
	public void startAll() {
		findAll().forEach(task -> {
			quartz.startJob(task);
			log.info("启动任务[{}]", task.getName());
		});
	}

	@Override
	public void start(String taskId) {
		TaskInf task = dao.findOne(taskId);
		if (task != null) {
			if (quartz.startJob(task))
				log.info("启动任务[{}-{}]", taskId, task.getName());
		}
	}

	@Override
	public void reset() {
		try {
			File file = new ClassPathResource("script_2_2_3/tables_mysql.sql").getFile();
			String[] sqls = FileUtils.readLines(file, Consts.DEFAULT_CHARSET).stream().filter(s -> !s.startsWith("#")).collect(Collectors.joining()).split(";");
			jdbcTemplate.batchUpdate(sqls);
			log.info("清理完毕");
		} catch (IOException e) {
			log.error("", e);
		}
	}

	@Override
	public void delete(String group, String taskId) {
		boolean deleteJob = quartz.deleteJob(group, taskId);
		if (deleteJob) {
			dao.delete(taskId);
		}
	}

}
