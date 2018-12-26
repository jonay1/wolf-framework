package com.wolf.core.utils;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.context.ApplicationContext;

public class ContextUtil {

	private static ApplicationContext applicationContext = null;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取applicationContext
	 * 
	 * @return
	 */
	private static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 通过name获取 Bean.
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name, Object... args) {
		return (T) getApplicationContext().getBean(name, args);
	}

	/**
	 * 通过class获取Bean.
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz, Object... args) {
		return getApplicationContext().getBean(clazz, args);
	}

	/**
	 * 通过name,以及Clazz返回指定的Bean
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

	public static Map<String, Object> getBeansByAnnotation(Class<? extends Annotation> annotationType) {
		return getApplicationContext().getBeansWithAnnotation(annotationType);
	}

	public static <T> Map<String, T> getBeansByType(Class<T> clazz) {
		return getApplicationContext().getBeansOfType(clazz);
	}

}
