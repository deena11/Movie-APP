package com.example.movieinventoryservice.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ApiModelProperty(notes="name of the Genre", example="Action,comedy,romance,...")
	private String name;
	public Genre() {
		super();
	}
	public Genre(String name) {
		super();
		this.name = name;
	}
	public Genre(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Genre [id=" + id + ", name=" + name + "]";
	}
	
	
	

}
