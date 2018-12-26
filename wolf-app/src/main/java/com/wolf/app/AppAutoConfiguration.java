package com.wolf.app;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.wolf.app.config.AppConfig;

@Configuration
@ComponentScan
@EnableConfigurationProperties({ AppConfig.class})
public class AppAutoConfiguration {
}
