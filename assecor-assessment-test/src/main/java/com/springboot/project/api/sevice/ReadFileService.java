package com.springboot.project.api.sevice;


import java.io.IOException;
import java.util.Optional;

import com.springboot.project.api.entity.Person;

public interface ReadFileService {

	 Iterable<Person> findAll() throws  IOException;
	 Optional<Person> findById(int id) throws  IOException;
	Iterable<Person> findAllByIdOfColor(String id) throws Exception;
	boolean saveAll(Iterable<Person> persons, boolean isSaved) throws Exception;
	 
}
