package com.springboot.project.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = {"com.springboot.project.api"})
public class Application {
	
	private static Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		log.info("---------------------------");
		log.info("start application...");
		log.info("---------------------------");
		
		SpringApplication.run(Application.class, args);
	}
}
