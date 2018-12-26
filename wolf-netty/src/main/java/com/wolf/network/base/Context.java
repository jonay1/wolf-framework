package com.wolf.network.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Context {
	private ThreadLocal<Map<String, Object>> local = new ThreadLocal<Map<String, Object>>() {
		public Map<String, Object> initialValue() {
			return new ConcurrentHashMap<String, Object>();
		}
	};

	public void put(String key, Object value) {
		local.get();
	}
}
