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
			PreparedStatement findFriendIds = null;
			PreparedStatement findFriends = null;
			PreparedStatement friendStats = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;
			ResultSet rs4 = null;
			int friendId;
			int wins;
			String friends = null;
		
			/*	Friend list	*/
			ArrayList<Integer> friendIds = new ArrayList<Integer>();
			ArrayList<String> friendList = new ArrayList<String>();
			ArrayList<Integer> friendWins = new ArrayList<Integer>();
		
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
		
			/*	Get friend id based on player id	*/
			String friendIdStatement = 
					"SELECT friendId FROM Player INNER JOIN Player_Friend ON p_id = playerId WHERE p_id = ?";
			try{
				findFriendIds = Database.getConnection().prepareStatement(friendIdStatement);	// get connection and prepare statement
				findFriendIds.setString(1, playerId);											// put player id into statement
				rs2 = findFriendIds.executeQuery();											// execute statement
				while(rs2.next()){
					friendId = rs2.getInt("friendId");										// get friend ids from result set
					friendIds.add(friendId);												// add id numbers to list
				}
			}
			catch(SQLException | ClassNotFoundException s){
				System.out.println("SQL statement is not executed:");
				System.out.println(s);
			}
			finally {
				if (findFriendIds!= null) { 
	        	   try {
	        		   findFriendIds.close();
	        	   } catch (SQLException e) {
	        		   e.printStackTrace();
	        	   } 
				}
			} 
			
			/*	Get friend name based on friend id	*/
			for(int i = 0; i<friendIds.size(); i++ ){
				String friendStatement = 
						"SELECT p_username FROM Player Inner Join Player_Friend ON p_id = playerId WHERE p_id = ?";
				try{
					findFriends = Database.getConnection().prepareStatement(friendStatement);
					findFriends.setInt(1, friendIds.get(i));
					rs3 = findFriends.executeQuery();
					while(rs3.next()){
						friends = rs3.getString("p_username");								// get friend names from result set
						friendList.add(friends);											// add friend names to list
					}
				}
				catch(SQLException | ClassNotFoundException e){
					System.out.println("SQL statement not executed:");
					System.out.println(e);
				}
				finally {
					if (findFriends!= null) { 
		        	   try {
		        		   findFriends.close();
		        	   } catch (SQLException e) {
		        		   e.printStackTrace();
		        	   } 
					}
				} 
			}
			
			session.setAttribute("friendList", friendList);
			
			
			/*	 Get friend stats	*/
			for(int j = 0; j<friendList.size();j++){
				String friendStatsStmt =
						"SELECT p_wins, p_loss, p_tie FROM Player WHERE p_username = ?";
				try{
					friendStats = Database.getConnection().prepareStatement(friendStatsStmt);
					friendStats.setString(1, friendList.get(j));
					rs4 = friendStats.executeQuery();
					while(rs4.next()){
						wins = rs4.getInt("p_wins");										// get number of wins from result set
						friendWins.add(wins);												// add number of wins to list
					}
				}
				catch(SQLException | ClassNotFoundException e){
					System.out.println("SQL statement not executed:");
					System.out.println(e);
				}
				finally {
					if (friendStats != null) { 
		        	   try {
		        		   friendStats.close();
		        	   } catch (SQLException e) {
		        		   e.printStackTrace();
		        	   } 
					}
				} 
			}
			session.setAttribute("friendWins", friendWins);
		
			request.getRequestDispatcher("/views/friends.jsp").forward(request, response);
	}
	else{
		msg = "You are not logged in!";
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);	// If not logged in send back to login page
		}
	}
}
