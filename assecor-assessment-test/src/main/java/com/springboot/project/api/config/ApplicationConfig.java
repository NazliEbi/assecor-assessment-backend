package com.springboot.project.api.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.springboot.project.api.entity.Person;

@Configuration
public class ApplicationConfig {
	
	@Value("${file.file_Type}")
	private String fileType;
	
	@Value("${file.file_Name}")
	private String fileName;
	
	private Resource resourceFile;

	public String getFiletype() {
		return fileType;
	}
	
	public String getFileName() {
		 return fileName;
	}
	
	public Resource getResourceFile() throws IOException{	
		 resourceFile = new ClassPathResource(getFileName()+ "." +getFiletype(), this.getClass().getClassLoader());
		 return resourceFile;
	}
	
	@Bean
	Person getPerson() {
		return new Person();		
	}

	public Person getPerson(int id, String name, String lastName, String zipCode, String city, String color) {
		return new Person(id, name, lastName, zipCode, city, color);
	}


}
