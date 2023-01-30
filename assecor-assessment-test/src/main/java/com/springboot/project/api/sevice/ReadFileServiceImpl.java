package com.springboot.project.api.sevice;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.project.api.config.ApplicationConfig;
import com.springboot.project.api.entity.Person;
import com.springboot.project.api.util.Color;
import com.springboot.project.api.util.FileLoadUtil;

@Service
@Transactional
public class ReadFileServiceImpl implements ReadFileService{
	
	private static Logger log = LoggerFactory.getLogger(ReadFileServiceImpl.class);

	@Autowired
	private FileLoadUtil fileLoadUtil;
	@Autowired
	private Person person;
	@Autowired
	private ApplicationConfig config;
	private String fileType;
	private String filePath= "";
	private File file;
	
	public ReadFileServiceImpl(FileLoadUtil fileLoadUtil, Person person, ApplicationConfig config) {
		this.fileLoadUtil = fileLoadUtil;
		this.person = person;
		this.config = config;
	}

	@Override
	public Iterable<Person> findAll() throws IOException  {
		List<Person> persons = new ArrayList<Person>();
		log.info("call getDataFromFile()...");
		setFileInfo();
		fileLoadUtil.getDataFromFile(persons, person, fileType, filePath);
		persons = PersonService.getListOfPersons();
		return persons;
	}
	
	@Override
	public Optional<Person> findById(int id) throws IOException{
		List<Person> persons = new ArrayList<>();
		findPersonById(id, persons, person);
		if(PersonService.getPerson().getId() == id) {
			Optional<Person> personOptional = Optional.ofNullable(PersonService.getPerson());
			return personOptional;
		}
		return Optional.empty();		
	}
	
	private void findPersonById(int id, List<Person> persons, Person person) {
		
		fileLoadUtil.getDataFromFile(persons, person, fileType, filePath);	
		persons = PersonService.getListOfPersons();		
		persons.forEach(item ->{
			if(item.getId() == id) {
				person.setId(id);
				person.setName(item.getName());
				person.setLastName(item.getLastName());
				person.setZipCode(item.getZipCode());
				person.setCity(item.getCity());
				person.setColor(item.getColor());						
			}
		});
		if(person.getId() == id) {
			PersonService.setPerson(person);
		}		
	}
	
	@Override
	public Iterable<Person> findAllByIdOfColor(String id) throws Exception {
		List<Person> persons = new ArrayList<>();
		List<Person> personsWithSameFavoriteColor = new ArrayList<>();
		Color color = Color.from(Integer.decode(id));
		if(color == null) {
			throw new Exception("The entered color id: "+ id + " did not definied. Please give a valid color id.");
		}
		fileLoadUtil.getDataFromFile(persons, person, fileType, filePath);
		persons = PersonService.getListOfPersons();
		for(Person item:persons) {
			if(item.getColor().equals(color.toString())) {
				personsWithSameFavoriteColor.add(item);
			}
		}
		return personsWithSameFavoriteColor;
	}
	
	@Override
	 public boolean saveAll(Iterable<Person> persons, boolean isSaved) throws Exception{
		isSaved = fileLoadUtil.setDataIntoFile((List<Person>) persons, isSaved, fileType, filePath);
		 if(isSaved) {
			 return true;
		 }
		return false;
	 }
	
	public void setFileInfo() throws IOException{
		fileType = config.getFiletype();
		file = config.getResourceFile().getFile();
		filePath = file.getAbsolutePath();
	}
}
