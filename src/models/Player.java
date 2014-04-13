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
	//public String[] lastMove = new String[2];
	public List<String[]> moveList = new ArrayList<>();
	public int diff;
	
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
	
	public Player(){
		username = null;
		this.setId(0);
	}
	
	/*public boolean isComp(){
		return isComp.equals("on");
	}*/
	
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
	
	public void setLastMove(String row, String col){
		String m[] = {row, col};
		//lastMove = m;
		moveList.add(m);
	}
	
	public String[] getLastMove(){
		if(this.moveList.size() > 0)
			return this.moveList.get(this.moveList.size()-1);
		return new String[2];
	}

	public boolean isComputer() {
		System.out.println("NOT Overriding");
		return this instanceof Computer;
	}
	
}
