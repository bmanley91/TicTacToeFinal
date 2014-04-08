package controllers;

import java.io.IOException;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Database;



/**
 * Servlet implementation class FriendServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		
		String url = "/views/friends.jsp";
		String msg = null;
		
		String playerId = null;
		PreparedStatement findPId = null;
		PreparedStatement getFriendId = null;
		PreparedStatement getFriends = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String[] friends = null;
		String selectStatement = 
				"SELECT p_id FROM Player WHERE p_username = ?";
		try{
			findPId = Database.getConnection().prepareStatement(selectStatement);	// Get connection and prepare statement
			findPId.setString(1, username);											// Put username into statement
			rs = findPId.executeQuery();											// execute the statement
			
			playerId = rs.getString(1);
		}
		catch(SQLException | ClassNotFoundException s){
			System.out.println("SQL statement is not executed:");
			System.out.println(s);
		}
		finally{
			if( findPId != null){
				try{
					findPId.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		String selectStatement2 = 
				"SELECT DISTINCT p_username FROM Player, Player_Friend  WHERE friendid = ?";
		try{
			getFriends = Database.getConnection().prepareStatement(selectStatement2);
			getFriends.setString(1,playerId);
			rs = getFriends.executeQuery();
			while(rs.next()){
				Array f = rs.getArray("p_id");
				friends = (String[])f.getArray();
			}
		}
		catch(SQLException | ClassNotFoundException s){
			System.out.println("SQL statement is not executed:");
			System.out.println(s);	
		}
		finally{
			if( getFriends != null){
				try{
					getFriends.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		
		session.setAttribute("friends", friends);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
		dispatcher.forward(request, response);
	}
    
 /**
  * @see HttpServlet#HttpServlet()
  */
 public FriendServlet() {
     super();
     // TODO Auto-generated constructor stub
 }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
