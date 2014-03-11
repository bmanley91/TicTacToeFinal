package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	
    
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	String url = "jdbc:mysql://localhost/ticTacToe";
        String username = "mfauser";
        String password = "secret";
        String driver = "com.mysql.jdbc.Driver";
        System.out.println("a");
    	Class.forName(driver);
    	System.out.println("b");
    	con = DriverManager.getConnection(url,username,password);
    	System.out.println("c");	
    	return con;
    }
}
