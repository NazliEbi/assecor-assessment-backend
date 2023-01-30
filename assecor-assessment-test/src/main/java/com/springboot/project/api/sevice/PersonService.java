package com.springboot.project.api.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.project.api.entity.Person;

@Service
public class PersonService {
	
	private static List<Person> listOfPersons = new ArrayList<Person>();
	private static Person person = new Person();

	public static List<Person> getListOfPersons() {
		return listOfPersons;
	}

	public static void setListOfPersons(List<Person> listOfPersons) {
		PersonService.listOfPersons = listOfPersons;
	}

	public static Person getPerson() {
		return person;
	}

	public static void setPerson(Person person) {
		PersonService.person = person;
	}
}
