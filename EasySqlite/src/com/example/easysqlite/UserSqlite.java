package com.example.easysqlite;

import lib.interfaces.Persistence;
import lib.interfaces.PrimaryKey;

public class UserSqlite {
	@PrimaryKey(autoIncrement = true)
	@Persistence
	private long _id;
	
	@Persistence
	private String firstName;
	@Persistence
	private String lastName;
	
	
	private String email; 
	
	@Persistence
	private int age;
	
	@Persistence
	private boolean isMale;

	
	
	
	
	public UserSqlite() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	public UserSqlite(long _id, String firstName, String lastName, String email,
			int age, boolean isMale) {
		super();
		this._id = _id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
		this.isMale = isMale;
	}




	public long get_id() {
		return _id;
	}


	public void set_id(long _id) {
		this._id = _id;
	}


	

	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}
	
	
	
	
}
