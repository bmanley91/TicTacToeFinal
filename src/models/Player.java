package models;
import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

import models.Game;

@Entity
public class Player {

	public List<Game> games;
	private long id;
	public String username;
	private String password;
	public int wins, losses, draws;
	public List<String[]> moveList = new ArrayList<>();
	public int diff;
	
	public Player(String username, String password,  long id) {
		
		//this.name = name;   for now we are just going to use username
		this.setPassword(password);
		this.username = username;
		this.setId(id);
		games = new ArrayList<Game>();
	}
	
	
	public Player(String name, int id) {
		this.username = name;
		this.setId(id);
	}
	
	public Player(){
		username = null;
		this.setId(0);
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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setLastMove(String row, String col){
		moveList.add(new String[] {row, col});
	}
	
	public String[] getLastMove(){
		return this.moveList.size() > 0 ? this.moveList.get(this.moveList.size()-1) : new String[2];
	}
	
	public String[] getFirstMove(){
		return this.moveList.size() > 0 ? this.moveList.get(0) : new String[2];
	}
	
	public int getNumOfGames(){
		return this.wins + this.losses + this.draws;
	}

	public boolean isComputer() {
		return this instanceof Computer;
	}
lic List<Game> getAllGames() {
		return Database.getAllOfPlayersGames(id);
	}
	
	@Override
	public String toString() {
		return this.username;
	}
	
	@Override
	public boolean equals(Object o) {
		System.out.println("my .equals");
		if(o instanceof Player)
			return ((Player)o).getId() == this.getId();
		return false;
	}
}
