package com.wolf.app.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.wolf.app.comm.handler.ResponseBodyReturnValueHandler;
import com.wolf.app.comm.security.ExtSecurityInterceptor;
import com.wolf.app.config.AppConfig;
import com.wolf.core.consts.Consts;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

	@Autowired
	private AppConfig config;

	@Autowired
	public void decorateRequestMappingHandlerAdapter(RequestMappingHandlerAdapter adapter) {
		List<HandlerMethodReturnValueHandler> handlers = adapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> newhandlers = new ArrayList<>(handlers);
		decorateHandlers(newhandlers);
		adapter.setReturnValueHandlers(newhandlers);
	}

	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (handler instanceof RequestResponseBodyMethodProcessor) {
				// 用自己的ResponseBody包装类替换掉框架的，达到返回Result的效果
				ResponseBodyReturnValueHandler decorator = new ResponseBodyReturnValueHandler(handler);
				int index = handlers.indexOf(handler);
				handlers.set(index, decorator);
				break;
			}
		}
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
		registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
		registry.addResourceHandler(config.getPublicPath() + "/**").addResourceLocations(ResourceUtils.FILE_URL_PREFIX + config.getUploadPath());
		super.addResourceHandlers(registry);
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(newInterceptor1());
	}

	@Bean
	public HandlerInterceptor newInterceptor1() {
		return new ExtSecurityInterceptor();
	}

	@Bean
	public Filter characterEncodingFilter() {
		log.info("==========初始化编码过滤器:{}=================", Consts.DEFAULT_CHARSET);
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding(Consts.DEFAULT_CHARSET.name());
		filter.setForceEncoding(true);
		return filter;
	}
}
