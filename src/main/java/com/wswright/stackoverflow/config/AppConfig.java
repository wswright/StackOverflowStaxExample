package com.wswright.stackoverflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource({ "classpath:application.properties" })
public class AppConfig {
	private final Environment env;

	public AppConfig(Environment env) {
		this.env = env;
	}

	public String getXMLFilename() {
		return env.getProperty("xml-filename");
	}
}
