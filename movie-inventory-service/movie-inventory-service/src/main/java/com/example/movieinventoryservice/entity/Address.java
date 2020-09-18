package com.example.movieinventoryservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ApiModelProperty(notes="Name of the Street", example="hilton road, Baker's Street,...")
	private String street;
	
	@ApiModelProperty(notes="Name of the City", example="chennai, banglore,...")
	private String city;
	
	@ApiModelProperty(notes="Name of the State", example="Tamil Nadu, Kerala,...")
	private String state;
	
	@ApiModelProperty(notes="6 digit number indicating postal address", example="600001,610021,...")
	private String pincode;

	public Address() {
		super();
	}

	public Address(int id, String street, String city, String state, String pincode) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", pincode="
				+ pincode + "]";
	}
	
	
	

	
}
