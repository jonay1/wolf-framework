package com.wolf.core.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MvcsUtil {

	public static boolean isAjax(HttpServletRequest request) {
		String xhr = request.getHeader("");
		return "X-HttpRequest".equals(xhr);
	}

	public static HttpServletRequest getHttpRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	public static HttpServletResponse getHttpResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		return response;
	}

	public static HttpSession getHttpSession() {
		return getHttpSession(true);
	}

	public static HttpSession getHttpSession(boolean create) {
		return getHttpRequest().getSession(create);
	}

	public static String getRequestParameter(String name) {
		return getHttpRequest().getParameter(name);
	}

	public static Map<String, String> getRequestParameters() {
		ServletRequest request = getHttpRequest();
		Enumeration<String> parameterNames = request.getParameterNames();
		Map<String, String> params = new HashMap<>();
		while (parameterNames.hasMoreElements()) {
			String paramerName = parameterNames.nextElement();
			String value = request.getParameter(paramerName);
			// if (value != null) {
			// value = new String(value.getBytes(Charset.forName("iso-8859-1")),
			// Charset.forName("utf-8"));
			// }
			params.put(paramerName, value);
		}
		return params;
	}

	public static String getCookie(String name) {
		Cookie[] cookies = getHttpRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static void setCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		HttpServletRequest httpRequest = getHttpRequest();
		cookie.setPath(httpRequest.getContextPath() + "/");
		HttpServletResponse httpResponse = getHttpResponse();
		httpResponse.addCookie(cookie);
	}
}
