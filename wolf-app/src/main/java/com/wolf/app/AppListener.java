package com.wolf.app;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.wolf.core.utils.ContextUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppListener implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {

		} else if (event instanceof ServletWebServerInitializedEvent) {

		} else if (event instanceof ApplicationReadyEvent) {
			ApplicationContext context = ((ApplicationReadyEvent) event).getApplicationContext();
			int count = context.getBeanDefinitionCount();
			log.info("load {} beans", count);
			init(context);
		}
	}

	private void init(ApplicationContext context) {
		// init context
		ContextUtil.setApplicationContext(context);

	}

}
