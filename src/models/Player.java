package models;

public class Player {

	public User user;
	public int id;
	String name;

	public Player(User user, int id) {
		this.user = user;
		this.id = id;
	}
	
	public Player(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		if(user != null)
			return user.name;
		return name;
	}
}
