package models;

import java.util.ArrayList;
import java.util.List;

import models.Game;

public class Player {

	public List<Game> games;
	private long id;
	public String username;
	private String password;
	public int wins, losses, draws;
	
	public Player(String username, String password,  long id) {
		
		//this.name = name;   for now we are just going to use username
		this.setPassword(password);
		this.username = username;
		this.setId(id);
		games = new ArrayList<Game>();
	}
/*
	public Player(User user, int id) {
		this.user = user;
		this.id = id;
	}*/
	
	public Player(String name, int id) {
		this.username = name;
		this.setId(id);
	}
	
	public String getName() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
