package com.springboot.project.api.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.springboot.project.api.config.ApplicationConfig;
import com.springboot.project.api.entity.Person;
import com.springboot.project.api.sevice.PersonService;

@Component
public class FileLoadUtil {
	private static Logger log = LoggerFactory.getLogger(FileLoadUtil.class);
		
	@Autowired
	private ApplicationConfig config;
	
	public FileLoadUtil(ApplicationConfig config) {
		this.config = config;
	}

	public void getDataFromFile(List<Person> persons, Person person, String fileType, String filePath) {
	
		switch (fileType) {
		case "csv":	
			readDataFromCsv(filePath, persons, person);			
			break;			
		case "json":
			log.info("There is no implementation for json format.");
		default:
			throw new IllegalArgumentException("Unexpected value: " + fileType);
		}		
	}
	
	public void readDataFromCsv(String filePath, List<Person> persons, Person person) {
		String zipCode = "";
		String city = "";
		Color color;
		try {
			CSVReader  csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(0).build();
			List<String[]> records = csvReader.readAll();
			int lineNumber = 0;
				
			for(String[] record:records) {
				lineNumber++;
				Arrays.setAll(record, (i) -> record[i].trim());
				if(record.length < 4) {
					log.info("Each record must contain four items, but record number " + lineNumber + " just has "+ record.length + " items.");
					continue;
	        	}else {
	        		String[] zipCode_city = record[2].split(" ",2);
	        		zipCode = zipCode_city[0];
					city = zipCode_city[1];
					color = Color.from(Integer.decode(record[3].toString()));
	        	}
				person = config.getPerson(lineNumber, record[1], record[0], zipCode, city, color.toString());
				persons.add(person);	
				PersonService.setListOfPersons(persons);								
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public boolean setDataIntoFile(List<Person> persons, boolean isSaved, String fileType, String filePath) {
		
		switch (fileType) {
		case "csv":	
			isSaved = writeDataIntoCsv(filePath, persons);			
			break;			
		case "json":
			log.info("There is no implementation for json format.");
		default:
			throw new IllegalArgumentException("Unexpected value: " + fileType);
		}
		return isSaved;		
	}
	
	public boolean writeDataIntoCsv(String filePath, List<Person> persons) {
		
		try {
			CSVReader  csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(0).build();
			List<String[]> records = csvReader.readAll();
			FileWriter outputfile = new FileWriter(filePath);
			CSVWriter writer = new CSVWriter(outputfile,',',
					CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_LINE_END);
			for(Person person: persons) {
				Integer number= Color.numberOfColor(person.getColor());	
				records.add(new String[] {person.getLastName()," "+ person.getName()," "+ person.getZipCode() + " " + person.getCity()," "+ number.toString()});				
			}
			writer.writeAll(records);
			writer.close();
			return true;
		} catch (IOException | CsvException e) {
			log.error(e.getMessage());
		}
		return false;
	}
}
