package models;

import java.util.ArrayList;
import java.util.List;

public class Player {

	public Player user;
	public List<Game> games;
	private int id;
	public String name;
	public String username;
	private String password;
	public int wins, losses, draws;
	
	public Player(String name, String password, String username) {
		
		//this.name = name;   for now we are just going to use username
		this.name = username;
		this.setPassword(password);
		this.username = username;
		games = new ArrayList<Game>();
	}
/*
	public Player(User user, int id) {
		this.user = user;
		this.id = id;
	}*/
	
	public Player(String name, int id) {
		this.name = name;
		this.setId(id);
	}
	
	public String getName() {
		if(user != null)
			return user.name;
		return name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/* Methods to return number of wins, losses, draws -Brian */
	public int getWins(){
		return this.wins;
	}
	public int getLoss(){
		return this.losses;
	}
	public int getDraw(){
		return this.draws;
	}
	
	/* Methods to  add wins, losses, draws -Brian */
	public void addWin(){
		this.wins++;
	}
	public void addLoss(){
		this.losses++;
	}
	public void addDraw(){
		this.draws++;
	}
}
