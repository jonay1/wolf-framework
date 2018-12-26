package com.wolf.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolf.core.exception.ExceptionUtil;

public class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String json2string(Object bean) {
		try {
			return mapper.writeValueAsString(bean);
		} catch (Exception e) {
			ExceptionUtil.throwException(e);
			return null;
		}
	}

	public static <T> T string2json(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			ExceptionUtil.throwException(e);
			return null;
		}
	}
}
