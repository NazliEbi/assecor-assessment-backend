package com.springboot.project.api.repository;

import org.springframework.data.repository.CrudRepository;
import com.springboot.project.api.entity.Person;


public interface PersonRepository extends CrudRepository<Person, Integer>{

}
