package com.wolf.app.web.service;

public interface SequenceService {
	
	long nextval(String group, String key);

	String nextval(String group, String key, String template);
}
