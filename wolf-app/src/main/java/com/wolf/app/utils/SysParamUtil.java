package com.wolf.app.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.wolf.app.comm.cache.SysParamCache;
import com.wolf.core.utils.ContextUtil;

public class SysParamUtil {

	private static SysParamCache cache = ContextUtil.getBean(SysParamCache.class);

	public static String getString(String type, String key, String defaultVal) {
		return StringUtils.defaultIfEmpty(cache.get(type, key), defaultVal);
	}

	public static boolean getBoolean(String type, String key, boolean defaultVal) {
		String value = getString(type, key, String.valueOf(defaultVal));
		return BooleanUtils.toBoolean(value);
	}

	public static int getInt(String type, String key, int defaultVal) {
		String value = getString(type, key, null);
		return NumberUtils.toInt(value, defaultVal);
	}

	public static void update(String type, String key, Object value) {
		cache.put(type, key, value);
	}

}
