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
	public List<String[]> moveList = new ArrayList<>();
	public ArrayList<Player> friendSearch = new ArrayList<Player>();
	public ArrayList<Player> friendsList = new ArrayList<Player>();
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
	
	public void setPlayerFriends(ArrayList<Player> friends){
		this.friendsList = friends;
	}
	
	public ArrayList<Player> getPlayerFriends(){
		return friendsList;
	}
	
	public void setFriendsSearch(ArrayList<Player> search){
		this.friendSearch = search;
	}
	
	public ArrayList<Player> getFriendsSearch(){
		return friendSearch;
	}
	
	public String getName() {
		return username;
	}
	public void setName(String name){
		this.username=name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setWins(int win){
		this.wins=win;
	}
	
	public int getWins(){
		return wins;
	}

	public void setLosses(int lose){
		this.losses=lose;
	}
	
	public int getLosses(){
		return losses;
	}
	
	public void setTies(int tie){
		this.draws=tie;
	}
	
	public int getTies(){
		return draws;
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
	
}
