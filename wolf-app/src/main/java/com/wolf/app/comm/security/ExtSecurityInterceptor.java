package com.wolf.app.comm.security;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wolf.app.config.AppConfig;
import com.wolf.core.exception.Errors;
import com.wolf.core.exception.ExceptionUtil;
import com.wolf.core.web.session.SessionUser;
import com.wolf.core.web.session.SessionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtSecurityInterceptor extends HandlerInterceptorAdapter {

	protected AntPathMatcher pathMatcher = new AntPathMatcher();

	private Map<String, Boolean> cacheSessionCheckUrl = new ConcurrentHashMap<>();

	@Autowired
	private AppConfig config;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean crsf = crsfCheck(request);
		if (!crsf) {
			ExceptionUtil.throwException(Errors.FORBIDDEN);
		}
		String requestURI = request.getRequestURI();
		if (!config.isSessionCheck() || isExclued(requestURI)) {
			return true;
		}
		SessionUser user = SessionUtil.getSessionUser(false);
		if (user == null) {
			log.warn("do login to access {}", requestURI);
			ExceptionUtil.throwException(Errors.SESSION_USER_INVALID);
		}
		return true;
	}

	private boolean isExclued(String requestURI) {
		Boolean exclude = cacheSessionCheckUrl.get(requestURI);
		if (exclude != null) {
			return exclude;
		}

		exclude = Boolean.FALSE;
		List<String> excludes = config.getSessionCheckExcludes();
		for (String s : excludes) {
			boolean match = pathMatcher.match(s, requestURI);
			if (match) {
				exclude = Boolean.TRUE;
				break;
			}
		}
		cacheSessionCheckUrl.put(requestURI, exclude);
		return exclude;
	}

	private boolean crsfCheck(HttpServletRequest req) {
		// csrf
		List<String> ignores = config.getRefererIgnores();
		Boolean ignore = Boolean.FALSE;
		String requestURI = req.getRequestURI();
		for (String s : ignores) {
			boolean match = pathMatcher.match(s, requestURI);
			if (match) {
				ignore = Boolean.TRUE;
				break;
			}
		}
		if (!ignore) {
			String ref = req.getHeader("Referer");
			Boolean accept = Boolean.FALSE;
			List<String> accepts = config.getRefererAccepts();
			if (!accepts.isEmpty()) {
				if (StringUtils.isNotBlank(ref)) {
					ref = ref.replaceAll("http://|https://", "");
					for (String s : accepts) {
						if (ref.startsWith(s)) {
							accept = Boolean.TRUE;
							break;
						}
					}
				}
			} else {
				accept = Boolean.TRUE;
			}
			if (!accept) {
				log.debug("FORBIDDEN: request referer is not accept, accepts '{}', actual '{}'", config.getRefererAccepts(), ref);
				return false;
			}
		}

		return true;
	}

}
