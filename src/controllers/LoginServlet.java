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

import models.Player;
import models.Database;

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
		Player player = null;
		
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			msg="Please fill out all fields";
			url = "/views/login.jsp"; 
		}
		else {
			PreparedStatement findPlayer = null;
	    	ResultSet rs = null;
	    	String selectStatement =			//logic for prepared statement
					 "SELECT * FROM Player WHERE p_username = ? AND p_password = ?";
	    	try{
	    		findPlayer = Database.getConnection().prepareStatement(selectStatement);	//get connection and declare prepared statement
	    		findPlayer.setString(1, username); 		// set input parameter 1
	    		findPlayer.setString(2, password); 		// set input parameter 2
	    		rs = findPlayer.executeQuery(); 					// execute statement
	           
				while(rs.next()) {
					player  = new Player(rs.getString("p_username"), rs.getString("p_password"),rs.getLong("p_id"));
					player.wins = rs.getInt("p_wins");
					player.losses = rs.getInt("p_loss");
					player.draws = rs.getInt("p_tie");
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
			if(player != null) {
				session.setAttribute("user", player);
				session.setAttribute("loggedIn", "true");
				msg=player.getName()+", you are logged in!";
			}
			else {
				msg="Incorrect username or password";
				url = "/views/login.jsp"; 
			}
		}
		
		
		request.setAttribute("msg", msg);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
	}

}
