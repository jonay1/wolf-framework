package com.wolf.app.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = AppConfig.PREFIX)
public class AppConfig {
	protected static final String PREFIX = "app";

	private String publicPath = "/public";
	private String uploadPath = "${user.dir}/app";

	private boolean sessionCheck;
	private List<String> sessionCheckExcludes;
	private List<String> refererAccepts;
	private List<String> refererIgnores;

}
