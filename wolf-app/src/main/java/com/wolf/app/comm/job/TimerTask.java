package com.wolf.app.comm.job;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimerTask implements TaskJob {

	// @Scheduled(fixedDelay = 10 * 1000)
	@Override
	public void execute(Map<String, Object> context) {
		// if(true) throw new RuntimeException("ss");
		log.info("current time is {}", new Date());

	}
}
