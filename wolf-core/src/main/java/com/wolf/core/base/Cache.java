package com.wolf.core.base;

public interface Cache {
	
	String get(String key);

	void put(String key, Object value);

	void clear();
}
