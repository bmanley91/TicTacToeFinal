package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	String url = "jdbc:mysql://sql2.freesqldatabase.com/sql232715";
        String username = "sql232715";
        String password = "jR3!dD8%";
        String driver = "com.mysql.jdbc.Driver";
    	Class.forName(driver);
    	con = DriverManager.getConnection(url,username,password);	
    	return con;
    }
    
    public static boolean addFriend(long pId, String newFriend){
    	boolean worked = false;			// return value for function
    	PreparedStatement add = null;	
    	ResultSet rs = null;
    	Connection con = null;
    	String statement =				// insert statement to update friends table
    			"INSERT INTO Player_Friend (playerId, friendId) VALUES(?,(SELECT p_id FROM Player WHERE p_username = ?))";
    	try{
    		con = Database.getConnection();
    		add = con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
    		add.setLong(1, pId);			// put player's id into the statement
    		add.setString(2, newFriend);	// put new friend's username into the statement
    		add.executeUpdate();			// execute the statement
    		rs = add.getGeneratedKeys();	// get result set
    		if(rs!=null && rs.next()){		// check statement was executed correctly
    			worked = true;
    		}
    		else{
    			worked = false;
    			System.out.println("Friend not successfully added");
    		}
    	}
    	catch(SQLException | ClassNotFoundException e){
    		System.out.println("Add friend not executed:");
    		System.out.println(e);
    	}
    	finally{							// close connection
    		if(add!=null){
    			try{
    				con.close();
    				add.close();
    			}
    			catch(SQLException s){
    				s.printStackTrace();
    			}
    		}
    	}
    	return worked;						// return true if update worked, false if not
    }
    
    public static ArrayList<Player> searchFriends(String searchEntry){
    	ArrayList<Player> searchResults = new ArrayList<Player>();		// arraylist of players to store search results
    	PreparedStatement search = null;
    	ResultSet rs = null; 
    	Connection con = null;
    	String statement =												// statement to return player parameters from database based on search
    			"SELECT p_id, p_username, p_wins, p_tie, p_loss FROM Player WHERE p_username LIKE ?";
    	try{
    		con = Database.getConnection();
    		search = con.prepareStatement(statement);
    		search.setString(1, "%"+searchEntry+"%");
    		rs = search.executeQuery();
    		while(rs.next()){
    			Player friend = new Player();							// create player object based on returned values from database
    			friend.setId(rs.getLong("p_id"));
    			friend.setName(rs.getString("p_username"));
    			friend.setWins(rs.getInt("p_wins"));
    			friend.setTies(rs.getInt("p_tie"));
    			friend.setLosses(rs.getInt("p_loss"));
    			searchResults.add(friend);								// add new player object to list
    		}
    	}
    	catch(SQLException | ClassNotFoundException e){
    		System.out.println("Friend search not executed:");
    		System.out.println(e);
    	}	
    	finally{														// close connection
    		if(search!=null){
    			try{
    				search.close();
    				con.close();
    			}
    			catch(SQLException s){
    				s.printStackTrace();
    			}
    		}
    	}
    	return searchResults;
    }
    public static ArrayList<Player> showFriends(Player user){
    	ArrayList<Player> friendList = new ArrayList<Player>();
    	ArrayList<Integer> friendIds = new ArrayList<Integer>();
    	PreparedStatement getIds = null;
    	PreparedStatement showFriend = null;
    	Connection con = null;
    	ResultSet ids = null;
    	ResultSet rs = null;
    	String firstStatement=		// statement to return all friendIds linked to the player's id
    			"SELECT friendId  FROM Player AS P2 INNER JOIN Player_Friend AS F2 ON P2.p_id = F2.playerId WHERE P2.p_id = ?";
    	try{
    		con = Database.getConnection();
    		getIds =con.prepareStatement(firstStatement);
    		getIds.setLong(1,user.getId());
    		rs = getIds.executeQuery();
    		while(rs.next()){
    			int friendId = rs.getInt("friendId");
    			friendIds.add(friendId);
    		}
    	}
    	catch(SQLException | ClassNotFoundException e){
    		System.out.println("Couldn't select friend ids:");
    		System.out.println(e);
    	}
    	finally{
    		if(getIds!=null){
    			try{
    				getIds.close();
    			}
    			catch(SQLException s){
    				s.printStackTrace();
    			}
    		}
    	}
    	for(int i = 0; i< friendIds.size(); i++){
    		String statement=	// big ol' SQL statement
    			"SELECT P.p_username, P.p_wins, P.p_id, P.p_tie, P.p_loss  FROM Player AS P INNER JOIN Player_Friend AS F ON P.p_id = F.playerId WHERE P.p_id = ?";
    		try{
    		showFriend = con.prepareStatement(statement);
    		showFriend.setLong(1, friendIds.get(i));
    		rs = showFriend.executeQuery();
    		while(rs.next()){
    			Player friend = new Player();
    			friend.setId(rs.getLong("p_id"));
    			friend.setName(rs.getString("p_username"));
    			friend.setWins(rs.getInt("p_wins"));
    			friend.setTies(rs.getInt("p_tie"));
    			friend.setLosses(rs.getInt("p_loss"));
    			friendList.add(friend);
    			}
    		}
    		catch(SQLException e){
    			System.out.println("Return friends not executed:");
    			System.out.println(e);
    		}
    		finally{
    			if(showFriend!=null){
    				try{
    					showFriend.close();
    					System.out.println("close");
    				}
    				catch(SQLException s){
    					s.printStackTrace();
    				}
    			}
    		}
    		try{
    			con.close();
    		}
    		catch(SQLException s){
    			s.printStackTrace();
    		}
    	}
    	return friendList;
    }
    
    public static void saveUser(Player player) {
    	PreparedStatement savePlayer = null;
    	ResultSet generatedKeys = null;
    	String updateStatement =			//logic for prepared statement
				 "insert into Player(p_username, p_password) values(?, ?)";
    	try{
    		savePlayer = Database.getConnection().prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);	//get connection and declare prepared statement
    		savePlayer.setString(1, player.username); 		// set input parameter 1
    		savePlayer.setString(2, player.getPassword()); 		// set input parameter 2
    		savePlayer.executeUpdate(); 					// execute insert statement
           
    		generatedKeys = savePlayer.getGeneratedKeys();
			if (generatedKeys.next()) {
				player.setId(generatedKeys.getInt(1));
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
           	if (s instanceof SQLIntegrityConstraintViolationException) {
           		//validation.addError("employee.username", "Username already exists");
           		//validation.keep();
           		//createEmployee();
           		System.out.println("error creating user");
           	}
       }
       finally {
           if (savePlayer != null) { 
           	try {
           		savePlayer.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
    }
}