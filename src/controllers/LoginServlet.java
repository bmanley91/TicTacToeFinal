package controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Database;
import models.Player;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String username = request.getParameter("username"); 
		String name = request.getParameter("name");
		String password = request.getParameter("password"); 
		
		String url = "/views/home.jsp"; 
		String msg = null;
		
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) 
			msg="Please fill out all fields";
		else {
			//User user = new User(name, password, username);
			Player player = new Player(name, password, username);
			session.setAttribute("user", player);
			session.setAttribute("loggedIn", "true");
			msg=player.name+", you are logged in!";
			
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
		request.setAttribute("msg", msg);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
	}

}
