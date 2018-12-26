package com.wolf.core.service.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.wolf.core.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	@Resource
	private MessageSource messageSource;

	private static Map<String, String> messages = null;

	@Override
	public Map<String, String> getMessages() {
		if (messages == null) {
			messages = new HashMap<>();
			if (messageSource instanceof ResourceBundleMessageSource) {
				ResourceBundleMessageSource bundleMessage = (ResourceBundleMessageSource) messageSource;
				Set<String> basenames = bundleMessage.getBasenameSet();
				for (String basename : basenames) {
					ResourceBundle bundle = ResourceBundle.getBundle(basename, getLocale());
					if (bundle != null) {
						Enumeration<String> keys = bundle.getKeys();
						while (keys.hasMoreElements()) {
							String key = keys.nextElement();
							String value = bundle.getString(key);
							messages.put(key, value);
						}
					}
				}
			}
		}
		return messages;
	}

	@Override
	public String getMessage(String code, Object... args) {
		return getMessage(code, args, code);
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultValue) {
		return messageSource.getMessage(code, args, defaultValue, getLocale());
	}

	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}
}
