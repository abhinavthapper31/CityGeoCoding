package com.project.geocode.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value = "file:application.properties")
@Component("Readproperty")
public class ReadProperty implements EnvironmentAware {

	private static Environment env;
	
	private static Logger LOGGER = LoggerFactory.getLogger(ReadProperty.class);

	@Override
	public void setEnvironment(Environment environment) {

		ReadProperty.env = environment;
		if (ReadProperty.env == null) {
			LOGGER.info("Failed to set properties");
		}
	}

	public static String getProperty(String key) {
		
		return ReadProperty.env.getProperty(key);
	}

}
