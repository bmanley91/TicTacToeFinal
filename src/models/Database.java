package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class Database {

	
    
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	String url = "jdbc:mysql://sql2.freesqldatabase.com/sql232715";
        String username = "sql232715";
        String password = "jR3!dD8%";
        String driver = "com.mysql.jdbc.Driver";
        System.out.println("a");
    	Class.forName(driver);
    	System.out.println("b");
    	con = DriverManager.getConnection(url,username,password);
    	System.out.println("c");	
    	return con;
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
