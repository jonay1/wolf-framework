package com.wolf.app.web.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class DefaultSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.info("session created {}", se.getSession().getId());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.info("session destroyed {}", se.getSession().getId());

	}

}
