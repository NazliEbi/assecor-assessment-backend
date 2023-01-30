package com.springboot.project.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "first_name")
	private String name;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "zip_code")
	private String zipCode;
	@Column(name = "city")
	private String city;
	@Column(name = "color")
	private String color;
	
	public Person() {
	}
	
	public Person(Integer id, String name, String lastName, String zipCode, String city,String color) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.name = name;
		this.zipCode = zipCode;
		this.city = city;
		this.color = color;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", lastName=" + lastName + ", zipCode=" + zipCode + ", city="
				+ city + ", color=" + color + "]";
	}

}
