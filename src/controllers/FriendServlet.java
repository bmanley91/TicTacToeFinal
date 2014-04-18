package controllers;

import java.io.IOException;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * Servlet implementation class FriendServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loggedInString = (String)session.getAttribute("loggedIn");
		boolean loggedIn = (loggedInString != null && loggedInString.equals("true"));
		String msg = null;
		
		if(loggedIn){		// Check if user is logged in
		
			String url = "/views/friends.jsp";
		
			/*	Local Variables for database lookup	*/
			String playerId = null;
			PreparedStatement findPId = null;
			PreparedStatement findFriends = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			String friends = null;
		
			/*	Friend list	*/
			ArrayList<String> friendList = new ArrayList<String>();
		
			/*	Player information	*/
			Player player = (Player)session.getAttribute("user");		
			String username = player.getName();							
		
		
			/*	Get player id from Database	*/
			String selectStatement = 
					"SELECT p_id FROM Player WHERE p_username = ?";
			try{
				findPId = Database.getConnection().prepareStatement(selectStatement);	// Get connection and prepare statement
				findPId.setString(1, username);											// Put username into statement
				rs = findPId.executeQuery();											// execute the statement
				if(rs.next()){
					playerId = rs.getString(1);
				}
				else{
					System.out.println("didn't work");
				}
			}
			catch(SQLException | ClassNotFoundException s){
				System.out.println("SQL statement is not executed:");
				System.out.println(s);
			}
			finally {
				if (findPId != null) { 
	        	   try {
	        		   findPId.close();
	        	   } catch (SQLException e) {
	        		   e.printStackTrace();
	        	   } 
				}
			}  
		
			/*	Get player friends based on player id	*/
			String friendStatement = 
					"SELECT p_username FROM Player JOIN Player_Friend WHERE friendId = ?";
			try{
				findFriends = Database.getConnection().prepareStatement(friendStatement);	// get connection and prepare statement
				findFriends.setString(1, playerId);											// put player id into statement
				rs2 = findFriends.executeQuery();											// execute statement
				while(rs2.next()){
					friends = rs2.getString("p_username");									// get friend names from result set
					friendList.add(friends);												// add names to friend list
				}
			}
			catch(SQLException | ClassNotFoundException s){
				System.out.println("SQL statement is not executed:");
				System.out.println(s);
			}
		
			session.setAttribute("friendList", friendList);
		
			request.getRequestDispatcher("/views/friends.jsp").forward(request, response);
	}
	else{
		msg = "You are not logged in!";
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);	// If not logged in send back to login page
		}
	}
}
