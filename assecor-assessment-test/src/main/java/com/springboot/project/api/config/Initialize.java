package com.springboot.project.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.springboot.project.api.base.Database;
import com.springboot.project.api.base.Database.Type;

/** central class for initialize application
 * - annotations @Configuration, @Bean will inject properties from application.yaml
 * with the right profile context
 * @author nbibak
 *
 */
@Configuration
public class Initialize {
	
	private static Logger log = LoggerFactory.getLogger(Initialize.class);
	static String strLogSeparator = "------------------------";
	private String active_profile;
	@Autowired
	private Environment environment;
	
	@Bean
	String InitializeApp() throws Exception {
		
		log.info(strLogSeparator);
		log.info(String.format("%s interface.name: %s %s",">>>>>>>>>>",environment.getProperty("interface_name"),"<<<<<<<<<<"));		
		log.info(String.format("%s interface.version: %s %s",">>>>>>>>>>",environment.getProperty("version_number"),"<<<<<<<<<<"));
		log.info(String.format("%s java.version: %s %s", ">>>>>>>>>>",environment.getProperty( "java.version" ),"<<<<<<<<<<"));
		log.info(String.format("%s host name: %s (%s) %s", ">>>>>>>>>>",java.net.InetAddress.getLocalHost().getHostName(),java.net.InetAddress.getLocalHost().getHostAddress(),"<<<<<<<<<<"));
		log.info(strLogSeparator);
		
		//get profile
		for (final String profileName : environment.getActiveProfiles()) {
        	active_profile = profileName;
        	log.info(String.format("active profile: %s", profileName));
        }           		
        if (active_profile.equals(null) || active_profile.equals("")) {
			throw new Exception("can't get active profile from commandline; please start application with option '--spring.profiles.active=DEV|TEST|PROD'");
		}
        
        log.info(strLogSeparator);
        
     // **************************        
 	 //        J D B C 
 	 // **************************
        if(Boolean.parseBoolean(environment.getProperty("spring.datasource.enabled"))) {
        	try {
        		Database database = new Database();
            	log.info("test datasource connection ...");
            	database.setUrl(environment.getProperty("spring.datasource.url"));
            	database.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
            	database.setUsername(environment.getProperty("spring.datasource.username"));
            	database.setPassword(environment.getProperty("spring.datasource.password"));            	
            	database.setType(Type.valueOf(environment.getProperty("jdbc.type")));
            	database.initDataSource(); //init datasource, jdbctemplate
            	log.info(String.format("profile: %s; datasource properties: %s", active_profile, database.toString()));
            	boolean isValid = database.getJdbcTemplate().getDataSource().getConnection().isValid(0);
            	log.info(String.format("datasource connection successfully created; valid: %s",isValid));
            	//check person table
            	String strTemp = " select count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'person' ";
            	log.info(String.format("execute sql statement; %s", strTemp));
            	int intPersonCount= database.getJdbcTemplate().queryForObject(strTemp, Integer.class);
            	if (intPersonCount == 0) {
					throw new Exception("table person does not exist!!!; execute person sql create script to install db table");
				} else {
					log.info(String.format("table person does exist; number of rows= %s", intPersonCount));
				}
    			
    		} catch (Exception e) {
    			log.error(environment.getProperty("interface_name") + " database error occured; " + e.getMessage(), e);
    			throw e;
    		}      	
        }
	  	log.info(environment.getProperty("interface_name") + " has started...");
		
		return active_profile;	
	}

}
