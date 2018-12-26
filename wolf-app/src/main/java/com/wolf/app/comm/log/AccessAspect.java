package com.wolf.app.comm.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import com.wolf.app.consts.ParamKey;
import com.wolf.app.utils.SysParamUtil;
import com.wolf.app.web.service.LogService;
import com.wolf.core.utils.MvcsUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class AccessAspect {

	@Autowired
	private LogService service;

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object interceptor(ProceedingJoinPoint point) throws Throwable {
		if (log.isDebugEnabled()) {
			log.debug("{}", point);
		}
		StopWatch sw = new StopWatch();
		sw.start();
		try {
			Object[] args = point.getArgs();
			Object rt = point.proceed(args);
			sw.stop();
			log(sw.getTotalTimeMillis(), true);
			return rt;
		} catch (Throwable e) {
			log.error("", e);
			sw.stop();
			log(sw.getTotalTimeMillis(), false);
			throw e;
		} finally {
		}

	}

	private void log(long cost, boolean flg) {
		if (SysParamUtil.getBoolean(ParamKey.SYS, ParamKey.LOG_API, true)) {
			final String path = MvcsUtil.getHttpRequest().getRequestURI();
			service.logAccess(path, cost, flg);
		}
	}
}
