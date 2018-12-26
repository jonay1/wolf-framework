package com.wolf.app.comm.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.wolf.app.web.service.SysParamService;

@Component
public class SysParamCache {
	public static final String CACHE_KEY = "SysParamCache";
	@Autowired
	private Environment environment;
	@Autowired
	private SysParamService service;

	@Cacheable(CACHE_KEY)
	public String get(String type, String key) {
		String ns = service.param(type, key);
		return ns == null ? null : environment.resolvePlaceholders(ns);
	}

	@CachePut(CACHE_KEY)
	public void put(String type, String key, Object value) {
		service.updateParam(type, key, value == null ? null : String.valueOf(value));
	}

	@CacheEvict
	public void clear() {

	}

}
