package models;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	public String name;
	public List<Game> games;
	public String username;
	private String password;
	
	public User(String name, String password, String username) {
		
		//this.name = name;   for now we are just going to use username
		this.name = username;
		this.setPassword(password);
		this.username = username;
		games = new ArrayList<Game>();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
