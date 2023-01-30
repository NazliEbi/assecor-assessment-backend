package com.springboot.project.api.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.springboot.project.api.config.ApplicationConfig;
import com.springboot.project.api.entity.Person;
import com.springboot.project.api.sevice.PersonService;

public class FileLoadUtilTest {
	
	Person person = Mockito.mock(Person.class);
	ApplicationConfig config = Mockito.mock(ApplicationConfig.class);
	FileLoadUtil  fileLoadUtil = new FileLoadUtil(config);
	
	String filePath = "C:\\Users\\nbibak\\Desktop\\assecor\\assecor-assessment-backend\\assecor-assessment-test\\target\\classes\\sample-input.csv";
	List<Person> persons = new ArrayList<Person>();

	@Test
	@DisplayName("Read data from csv file")
	void testReadDataFromCsv_ShouldReadNinePersonsFromFile() {
		int expectedsizeofList = 9;
		fileLoadUtil.readDataFromCsv(filePath, persons, person);
		persons = PersonService.getListOfPersons();
		int result = persons.size();
		Assertions.assertEquals(expectedsizeofList, result,()-> "The readDataFromCsv() methode did not produce expected result.");		
	}
	
	@Test
	@DisplayName("Write data into csv file")
	void testWriteDataIntoCsv_ShouldWritePersonsIntoCSVFile_AndGiveATrueAsAReturnValue() {
		boolean actualValue = false;
		boolean expectedValue = true;
		person.setName("Robert");
		person.setLastName("Manns");
		person.setZipCode("12345");
		person.setCity("Dresden");
		person.setColor("rot");
		persons.add(person);
		actualValue = fileLoadUtil.writeDataIntoCsv(filePath, persons);	
		Assertions.assertEquals(expectedValue, actualValue,()-> "The writeDataIntoCsv() methode did not produce expected result.");		
	}
	
	@Test
	@DisplayName("Set Data Into File")
	void testSetDataIntoFile_ShouldReturnATrueAsValue() {
		String fileType = "csv"; 
		boolean expectedReturnValue = true;
		person.setName("Robert");
		person.setLastName("Kaehler");
		person.setZipCode("88888");
		person.setCity("Muenchen");
		person.setColor("blau");
		persons.add(person);
		boolean returnValue = fileLoadUtil.setDataIntoFile(persons, false, fileType, filePath);
		Assertions.assertEquals(expectedReturnValue, returnValue,()-> "The writeDataIntoCsv() methode did not produce expected result.");
	}
	

}
