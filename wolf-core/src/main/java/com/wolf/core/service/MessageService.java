package com.wolf.core.service;

import java.util.Map;

public interface MessageService {

	Map<String, String> getMessages();

	String getMessage(String code, Object... args);

	String getMessage(String code, Object[] args, String defaultValue);

}
