package com.springboot.project.api.config;

import java.util.HashMap;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author nbibak
 *
 */
@Configuration
public class ConfigurationInfo {
	
	@Autowired
	private Environment environment;
	private static Logger log = LoggerFactory.getLogger(ConfigurationInfo.class);
	
	/**
	 * IS Frontend: properties for info page
	 */
	@Bean
	void projectInfo() {
		
		log.info("environment interface_name:" + environment.getProperty("interface_name"));
		log.info("environment version_number:" + environment.getProperty("version_number"));
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		for (final String profileName : environment.getActiveProfiles()) {
            hashMap.put("profile", profileName);
        }
	}
}
