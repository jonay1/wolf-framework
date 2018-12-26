package com.wolf.core.utils;

import java.util.Map;

import com.wolf.core.service.MessageService;

public class MessageUtil {

	public static Map<String, String> getMessages() {
		return me().getMessages();
	}

	public static String getMessage(String code, Object... args) {
		return getMessage(code, args, code);
	}

	public static String getMessage(String code, Object[] args, String defaultValue) {
		return me().getMessage(code, args, defaultValue);
	}

	private static MessageService me() {
		return ContextUtil.getBean(MessageService.class);
	}
}
