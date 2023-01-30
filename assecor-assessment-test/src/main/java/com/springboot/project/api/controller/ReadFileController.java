package com.springboot.project.api.controller;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.springboot.project.api.entity.Person;
import com.springboot.project.api.repository.PersonRepository;
import com.springboot.project.api.sevice.ReadFileService;
import com.springboot.project.api.util.Color;


@RestController
public class ReadFileController {
	
	private static Logger log = LoggerFactory.getLogger(ReadFileController.class);
	@Autowired
	private ReadFileService readFileService;
	@Autowired
	private PersonRepository personRepository;
	
	public ReadFileController(ReadFileService readFileService) {
		this.readFileService= readFileService;
	}
	
//	--------------rest request for a file----------------------
	
	//endpoint --> GET /persons
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/persons", produces="application/json")
	public ResponseEntity<Iterable<Person>> getPersons() {
		Iterable<Person> persons = new ArrayList<>();
		try {
			log.info("Get all persons from the file...");
			persons = readFileService.findAll();
			log.info("There are "+ persons.spliterator().getExactSizeIfKnown() + " persons in this file.");
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		//check if the the list is empty
		if( persons.spliterator().getExactSizeIfKnown()!=0) {
			return new ResponseEntity<Iterable<Person>>(persons, HttpStatus.OK);
		}
		return new ResponseEntity("The list is empty.", HttpStatus.NO_CONTENT);
	}

	//endpoint --> GET /persons/{id}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value ="/persons/{id}", produces="application/json")
	public ResponseEntity<Person> getPersonById(@PathVariable("id") int id){
		
		Optional<Person> optinalPerson = Optional.empty();
		try {
			log.info("Get person from the file by id: "+ id);
			optinalPerson = readFileService.findById(id);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		if(optinalPerson.isPresent()) {
			return new ResponseEntity<Person>(optinalPerson.get(),HttpStatus.OK);
		}
		return new ResponseEntity("There is no person in the list with id= "+ id,HttpStatus.NOT_FOUND );
	}
	
	//endpoint --> GET /persons/color/{color}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value ="/persons/color/{color}", produces ="application/json")
	public ResponseEntity<Iterable<Person>> getAllPersonsWithTheSameFavouriteColor(@PathVariable("color") String color){
		Iterable<Person> persons = new ArrayList<>();
		 try {
			persons = readFileService.findAllByIdOfColor(color);
			//check if the the list is empty
			if(persons.spliterator().getExactSizeIfKnown()!=0) {			
			    return new ResponseEntity<Iterable<Person>>(persons, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return new ResponseEntity(e.getMessage() ,HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity("There is no persons in the list with color id = "+ color,HttpStatus.NOT_FOUND); 
	}
	
	//endpoint --> POST /persons
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "/persons", consumes="application/json") 
	public ResponseEntity<Iterable<Person>> addPersons(@RequestBody Iterable<Person> persons) {
		boolean isSaved = false;
		try {
			isSaved = readFileService.saveAll(persons, isSaved);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		if(isSaved) {
			 return new ResponseEntity("Items added succesfully ",HttpStatus.CREATED);
		}
	    return new ResponseEntity("Items did not add succesfully ",HttpStatus.NOT_ACCEPTABLE); 
	}
	
//	--------------rest request for database----------------------
	
	//endpoint --> POST /persons
	@PostMapping(value = "db/persons", consumes="application/json")
	public ResponseEntity<Iterable<Person>> CreatePersons(@RequestBody Iterable<Person> persons){
		log.info("Post persons into database.. ");
		personRepository.saveAll(persons);
		return new ResponseEntity<Iterable<Person>>(persons,HttpStatus.OK);			
	}
	
	//endpoint --> GET /persons
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "db/persons", produces="application/json")
	public ResponseEntity<Iterable<Person>> getAllPersons() {
		
		log.info("Get all persons from Database...");
		Iterable<Person> persons = personRepository.findAll();
		//check if the the list is empty
		if( persons.spliterator().getExactSizeIfKnown()!=0) {
			return new ResponseEntity<Iterable<Person>>(persons, HttpStatus.OK);
		}
		return new ResponseEntity("The list is empty.", HttpStatus.NO_CONTENT);
	}
	
	//endpoint --> GET /persons/{id}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value ="db/persons/{id}", produces="application/json")
	public ResponseEntity<Person> getById(@PathVariable("id") int id){
		
		Optional<Person> optinalPerson= personRepository.findById(id);

		if(optinalPerson.isPresent()) {
			return new ResponseEntity<Person>(optinalPerson.get(),HttpStatus.OK);
		}
		return new ResponseEntity("There is no person in the database with id= "+ id,HttpStatus.NOT_FOUND );
	}
	
	//endpoint --> GET /persons/color/{color}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value ="db/persons/color/{color}", produces ="application/json")
	public ResponseEntity<Iterable<Person>> getWithSameFavouriteColor(@PathVariable("color") String scolor){
	//	Iterable<Person> personsWithSameFavouriteColor = new ArrayList<Person>();
		ArrayList<Person> personsWithSameFavouriteColor = new ArrayList<Person>();
		Iterable<Person> persons = personRepository.findAll();
		Color color = Color.from(Integer.decode(scolor));
		for(Person item: persons) {
			if(item.getColor().equals(color.toString())) {
				//((ArrayList<Person>) personsWithSameFavouriteColor).add(item);
				 personsWithSameFavouriteColor.add(item);
			}
		}
		//check if the the list is empty
		//if(personsWithSameFavouriteColor.spliterator().getExactSizeIfKnown()!=0) {
		if(personsWithSameFavouriteColor.size()!=0) {	
		    return new ResponseEntity<Iterable<Person>>(personsWithSameFavouriteColor, HttpStatus.OK);
		}
	    return new ResponseEntity("There is no persons in the list with color id = "+ scolor,HttpStatus.NOT_FOUND); 
	}

}
