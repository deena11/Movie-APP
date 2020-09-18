package com.example.movieinventoryservice.entity;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Cast {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ApiModelProperty(notes="Name of the cast", example="Rajesh, Rocky,....")
    private String name;
	
	@ApiModelProperty(notes="Role of the Cast", example="villain, Hero, Comedian,...")
    private String role;
    
	@ApiModelProperty(notes="Picture of the actor as bytes")
	@Lob
    private byte[] picture;
	
	public Cast() {
		super();
	}
	public Cast(int id, String name, String role, byte[] picture) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.picture = picture;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "Cast [id=" + id + ", name=" + name + ", role=" + role + ", picture=" + Arrays.toString(picture) + "]";
	}
		
}
