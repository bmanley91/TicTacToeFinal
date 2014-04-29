package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;


public class Database {
	private static Connection conn;
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
    	if(conn == null || conn.isClosed()) {
	    	String url = "jdbc:mysql://sql2.freesqldatabase.com/sql232715";
	        String username = "sql232715";
	        String password = "jR3!dD8%";
	        String driver = "com.mysql.jdbc.Driver";
	        System.out.println("a");
	    	Class.forName(driver);
	    	System.out.println("b");
	    	conn = DriverManager.getConnection(url,username,password);
	    	System.out.println("c");	
    	}
    	return conn;
    }
    
    public static boolean addFriend(long pId, String newFriend){
    	boolean worked = false;				// return value for function
    	PreparedStatement add = null;	
    	ResultSet rs = null;
    	Connection con = null;
    	String statement =					// insert statement to update friends table
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
    	Player player = null;
    	Connection con = null;
    	ResultSet ids = null;
    	ResultSet rs = null;
    	String firstStatement=		// statement to return all friendIds linked to the player's id
    			"SELECT p.* FROM Player p INNER JOIN Player_Friend F2 ON p.p_id = F2.friendId WHERE F2.playerId = ?";
    	try{
    		con = Database.getConnection();
    		getIds =con.prepareStatement(firstStatement);
    		getIds.setLong(1,user.getId());
    		rs = getIds.executeQuery();
    		while(rs.next()){		// save returned friend info in new player object
    			player  = new Player(rs.getString("p_username"), rs.getString("p_password"),rs.getLong("p_id"),
						rs.getInt("p_wins"), rs.getInt("p_loss"), rs.getInt("p_tie"));
    			friendList.add(player);
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
    	return friendList;
    }
    
    public static void saveUser(Player player) {
    	PreparedStatement savePlayer = null;
    	ResultSet generatedKeys = null;
    	String updateStatement =			//logic for prepared statement
				 "insert into Player(p_username, p_password) values(?, ?)";
    	try{
    		conn = getConnection();
    		savePlayer = conn.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);	//get connection and declare prepared statement
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
    
    public static Player findPlayer(String username, String password) {
    	Player player = null;
    	PreparedStatement findPlayer = null;
    	ResultSet rs = null;
    	String selectStatement =			//logic for prepared statement
				 "SELECT * FROM Player WHERE p_username = ? AND p_password = ?";
    	try{
    		conn = getConnection();
    		findPlayer = conn.prepareStatement(selectStatement);	//get connection and declare prepared statement
    		findPlayer.setString(1, username); 		// set input parameter 1
    		findPlayer.setString(2, password); 		// set input parameter 2
    		rs = findPlayer.executeQuery(); 					// execute statement
           
			while(rs.next()) {
				player  = new Player(rs.getString("p_username"), rs.getString("p_password"),rs.getLong("p_id"),
						rs.getInt("p_wins"), rs.getInt("p_loss"), rs.getInt("p_tie"));
				//System.out.println("here "+rs.getInt("p_loss"));
			}
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
           	if (s instanceof SQLIntegrityConstraintViolationException) {
           		System.out.println("error creating user");
           	}
       }
       finally {
           if (findPlayer != null) { 
           	try {
           		findPlayer.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
		return player;
    }
    
    public static void updateWins(Game game){
    	PreparedStatement update1 = null;
		PreparedStatement update2 = null;
		//Connection con = null;
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();
		
    	ResultSet rs = null;
    	String selectStatement =			//logic for prepared statement
				 "UPDATE Player SET p_wins = p_wins + 1 WHERE p_username = ?;";
    	String selectStatement2 =			//logic for prepared statement
    			"UPDATE Player SET p_loss = p_loss + 1 WHERE p_username = ?;";
    	if(game.isTie){
    		selectStatement = "UPDATE Player SET p_tie = p_tie + 1 WHERE p_username = ?;";
    		selectStatement2 = "UPDATE Player SET p_tie = p_tie + 1 WHERE p_username = ?;";
    	}
    	else if(!player1.equals(game.getWinner())){
    		selectStatement = "UPDATE Player SET p_loss = p_loss + 1 WHERE p_username = ?;";
    		selectStatement2 = "UPDATE Player SET p_wins = p_wins + 1 WHERE p_username = ?;";
    	}
    	
    	
    	try{
    		conn = Database.getConnection();
    		update1 = conn.prepareStatement(selectStatement);	//get connection and declare prepared statement
    		update2 = conn.prepareStatement(selectStatement2);
    		
    		update1.setString(1, player1.username); 		// set input parameter 1
    		update2.setString(1, player2.username); 		// set input parameter 2
    		
    		update1.executeUpdate();					// execute statement
    		update2.executeUpdate();					// execute statement
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
           	if (s instanceof SQLIntegrityConstraintViolationException) {
           		System.out.println("error creating user");
           	}
       }
       finally {
           if (update1 != null || update2 != null) { 
           	try {
           		conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
    }
    
    public static Player findPlayerById(long id) {
    	Player player = null;
    	PreparedStatement findPlayer = null;
    	ResultSet rs = null;
    	String selectStatement =			//logic for prepared statement
				 "SELECT * FROM Player WHERE p_id = ?";
    	try{
    		conn = getConnection();
    		findPlayer = conn.prepareStatement(selectStatement);	//get connection and declare prepared statement
    		findPlayer.setLong(1, id); 		// set input parameter 1
    		rs = findPlayer.executeQuery(); 					// execute statement
           
			while(rs.next()) {
				player  = new Player(rs.getString("p_username"), rs.getString("p_password"),rs.getLong("p_id"),
						rs.getInt("p_wins"), rs.getInt("p_loss"), rs.getInt("p_tie"));
				//System.out.println("here "+rs.getInt("p_loss"));
			}
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
           	if (s instanceof SQLIntegrityConstraintViolationException) {
           		System.out.println("error creating user");
           	}
       }
       finally {
           if (findPlayer != null) { 
           	try {
           		findPlayer.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
		return player;
    }
    
    public static Game createGame(Game g) {
    	PreparedStatement createGame = null;
    	ResultSet generatedKeys = null;
    	String updateStatement =			//logic for prepared statement
				 "insert into Game(g_player1, g_player2, g_playersTurn) values(?, ?, ?)";
    	Game savedGame = null;
    	
    	try{
    		conn = getConnection();
    		createGame = conn.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);	//get connection and declare prepared statement
    		createGame.setLong(1, g.getPlayer1().getId()); 		// set input parameter 1
    		createGame.setLong(2, g.getPlayer2().getId()); 		// set input parameter 2
    		createGame.setInt(3, g.playersTurn); 		// set input parameter 3
    		createGame.executeUpdate(); 					// execute insert statement
           
    		generatedKeys = createGame.getGeneratedKeys();
			if (generatedKeys.next()) {
				g.setId(generatedKeys.getInt(1));
				savedGame = Database.createTilesForGame(g);
				
	        } else {
	            throw new SQLException("Creating game failed, no generated key obtained.");
	        }
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
       }
       finally {
           if (createGame != null) { 
           	try {
           		createGame.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
    	return savedGame;
    }

	private static Game createTilesForGame(Game g) {
		PreparedStatement createTiles = null;
    	ResultSet generatedKeys = null;
    	String updateStatement =			//logic for prepared statement
				 "insert into Tiles(row, colNum, gameId) values(?, ?, ?)";
    	Game savedGame = null;
    	try{
    		conn = getConnection();
    		createTiles = conn.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);	//get connection and declare prepared statement
    		for (String key: g.board.tiles.keySet()) {
    			int colNum = 0;
    			for(Integer i: g.board.tiles.get(key))
    			{
    				createTiles.setString(1, key);
    				createTiles.setInt(2, colNum);
    				createTiles.setLong(3, g.getId());
    				createTiles.addBatch();
    				colNum++;
    			}
    		}
    		createTiles.executeBatch();
    		generatedKeys = createTiles.getGeneratedKeys();
			if (generatedKeys.next()) {
				savedGame = g;
	        } else {
	            throw new SQLException("Creating Tiles failed, no generated key obtained.");
	        }
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
       }
       finally {
           if (createTiles != null) { 
           	try {
           		createTiles.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
    	return savedGame;
	}

	public static Game findGameById(Long gameId) {
		Game game = null;
    	PreparedStatement findGame = null;
    	ResultSet rs = null;
    	String selectStatement =			//logic for prepared statement
				 "SELECT * FROM Game WHERE g_id = ?";
    	try{
    		conn = getConnection();
    		findGame = conn.prepareStatement(selectStatement);	//get connection and declare prepared statement
    		findGame.setLong(1, gameId); 		// set input parameter 1
    		rs = findGame.executeQuery(); 					// execute statement
    		System.out.println("Just executed");
			while(rs.next()) {
				long p1Id = rs.getLong("g_player1");
				long p2Id = rs.getLong("g_player2");
				
				if(p1Id == 0 || p2Id == 0) 
					System.out.println("Error finding players, or I guess p2 can be a computer");
				
				int playersTurn = rs.getInt("g_playersTurn");
				long winnderId = rs.getLong("g_winnerId"); //TODOD
				Player p1 = findPlayerById(p1Id);
				Player p2 = findPlayerById(p2Id);
				Map<String, ArrayList<Integer>> tiles = Database.getTilesForGame(gameId);
				System.out.println("&&&&&"+p1);
				game  = new Game(p1, p2, tiles,playersTurn, gameId);
			}
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
           	if (s instanceof SQLIntegrityConstraintViolationException) {
           		System.out.println("error creating user");
           	}
       }
       finally {
           if (findGame != null) { 
           	try {
           		findGame.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
    	System.out.println("game =" + game);
		return game;
	}
    
	private static Map<String, ArrayList<Integer>> getTilesForGame(Long gameId) {
		Map<String, ArrayList<Integer>> tiles = new LinkedHashMap<String, ArrayList<Integer>>();
    	PreparedStatement findTiles = null;
    	ResultSet rs = null;
    	String selectStatement =			//logic for prepared statement
				 "SELECT row, value FROM Tiles WHERE gameId = ? ORDER BY id ASC";
    	try{
    		conn = getConnection();
    		findTiles = conn.prepareStatement(selectStatement);	//get connection and declare prepared statement
    		findTiles.setLong(1, gameId); 		// set input parameter 1
    		rs = findTiles.executeQuery(); 					// execute statement
    		
			while(rs.next()) {
				String key = rs.getString("row");
				Integer val = rs.getInt("value");
				
				if(tiles.containsKey(key))
					tiles.get(key).add(val);
				else {
					ArrayList<Integer> tempList = new ArrayList<Integer>();
					tempList.add(val);
					tiles.put(key, tempList);
				}
			}
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
           	if (s instanceof SQLIntegrityConstraintViolationException) {
           		System.out.println("error creating user");
           	}
       }
       finally {
           if (findTiles != null) { 
           	try {
           		findTiles.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
		return tiles;
	}

	public static Game updateGame(Game g) {
		System.out.println("updating game");
    	PreparedStatement updateGame = null;
    	String updateStatement;
    	if(g.isWinner() || g.isTie)
    		updateStatement =			//logic for prepared statement
			 "UPDATE Game SET g_winner = ?, g_playersTurn = ? WHERE g_id = ?";
    	else
    		updateStatement =			//logic for prepared statement
			 "UPDATE Game SET g_playersTurn = ? WHERE g_id = ?";
    	try{
    		conn = getConnection();
    		updateGame = conn.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);	//get connection and declare prepared statement
    		int count = 1;
    		if(g.isWinner() || g.isTie) {
    			updateGame.setLong(count, g.getWinnerId()); 		// set input parameter 1
    			count++;
    		} 
    		updateGame.setInt(count, g.playersTurn); 		// set input parameter 2
    		count++;
    		updateGame.setLong(count, g.getId());
    		updateGame.executeUpdate(); 					// execute insert statement
    		
    		updateTilesForGame(g);
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
       }
       finally {
           if (updateGame != null) { 
           	try {
           		updateGame.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
    	return g;
    }
	
	
	
	private static void updateTilesForGame(Game g) {
		PreparedStatement updateTiles = null;
    	String updateStatement =			//logic for prepared statement
				 "UPDATE Tiles SET value= ? WHERE gameid = ? AND colNum = ? AND row = ?";
    	try{
    		conn = getConnection();
    		updateTiles = conn.prepareStatement(updateStatement, Statement.RETURN_GENERATED_KEYS);	//get connection and declare prepared statement
    		
    		for(String key: g.board.tiles.keySet()) {
    			int colNum = 0;
    			for(Integer i : g.board.tiles.get(key)) {
    				updateTiles.setInt(1, i);
					updateTiles.setLong(2, g.getId());
					updateTiles.setInt(3, colNum);
					updateTiles.setString(4, key);
					updateTiles.addBatch();
					colNum++;
    			}
    		}
		updateTiles.executeBatch();
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
       }
       finally {
           if (updateTiles != null) { 
           	try {
           		updateTiles.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
	}

	public static void DB_Close() throws Throwable { 
		try { 
			if(conn!=null) 
				conn.close(); 
			} 
		catch(SQLException e) 
			 { 
				System.out.println(e); 
			 } 
 	}

	public static List<Game> getAllOfPlayersGames(long id) {
		List<Game> games = new ArrayList<Game>();
    	PreparedStatement findGame = null;
    	ResultSet rs = null;
    	String selectStatement =			//logic for prepared statement
				 "SELECT * FROM Game WHERE g_player1 = ? OR g_player2 = ?";
    	try{
    		conn = getConnection();
    		findGame = conn.prepareStatement(selectStatement);	//get connection and declare prepared statement
    		findGame.setLong(1, id); 		// set input parameter 1
    		findGame.setLong(2, id); 		// set input parameter 2
    		rs = findGame.executeQuery(); 					// execute statement
			while(rs.next()) {
				long p1Id = rs.getLong("g_player1");
				long p2Id = rs.getLong("g_player2");
				long gameId = rs.getLong("g_id");
				if(p1Id == 0 || p2Id == 0) 
					System.out.println("Error finding players, or I guess p2 can be a computer");
				
				int playersTurn = rs.getInt("g_playersTurn");
				Player p1 = findPlayerById(p1Id);
				Player p2 = findPlayerById(p2Id);
				Map<String, ArrayList<Integer>> tiles = Database.getTilesForGame(gameId);
				games.add(new Game(p1, p2, tiles,playersTurn, gameId));
			}
       }
       catch (SQLException | ClassNotFoundException s){
           	System.out.println("SQL statement is not executed:");
           	System.out.println(s);
           	if (s instanceof SQLIntegrityConstraintViolationException) {
           		System.out.println("error creating user");
           	}
       }
       finally {
           if (findGame != null) { 
           	try {
           		findGame.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
           }
       }
    	System.out.println("game =" + games);
		return games;
	} 
}
