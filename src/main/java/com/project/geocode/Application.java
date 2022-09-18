package com.project.geocode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.project.geocode.service.GeoCodeServiceImpl;

@ComponentScan(basePackages = { "com.project"})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GeoCodeServiceImpl.class, args);
		GeoCodeServiceImpl service = applicationContext.getBean(GeoCodeServiceImpl.class);
		service.getGeocodeData();
	}

}
