package controllers;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Database;

/**
 * Servlet implementation class FindFriendServlet
 */
@WebServlet("/FindFriendServlet")
public class FindFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loggedInString = (String)session.getAttribute("loggedIn");
		boolean loggedIn = (loggedInString != null && loggedInString.equals("true"));
		PreparedStatement Search = null;
		ArrayList<String> searchResultNames = new ArrayList<String>(); 
		ArrayList<Integer> searchResultWins = new ArrayList<Integer>();
		String searchName = null;
		int searchWins;
		ResultSet rs = null;
		String msg = null;
		String url = null;
		
		if(loggedIn){
			String friendSearch = (String)request.getParameter("friendSearch");
			if(friendSearch.length()==0){
				msg = "Please fill in search field.";
				url = "/views/findfriends.jsp";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher(url).forward(request, response);
			}
			else{
				String SearchStatement = 
						"SELECT p_username, p_wins FROM Player WHERE p_username LIKE ?";
				try{
					Search = Database.getConnection().prepareStatement(SearchStatement);
					Search.setString(1,friendSearch);
					rs = Search.executeQuery();
					while(rs.next()){
						searchName = rs.getString("p_username");
						searchWins = rs.getInt("p_wins");
						searchResultNames.add(searchName);
						searchResultWins.add(searchWins);
					}
					url = "/views/searchResults.jsp";
				}
				catch(SQLException | ClassNotFoundException e){
					System.out.println("SQL statement not executed:");
					System.out.println(e);
				}
				finally{
					if(Search!=null){
						try{
							Search.close();
						}
						catch(SQLException e){
							e.printStackTrace();
						}
					}
				}
				session.setAttribute("searchNames", searchResultNames);
				session.setAttribute("searchWins", searchResultWins);
				
				request.getRequestDispatcher(url).forward(request, response);
			}
		}
		else{
			msg = "You are not logged in!";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/views/login.jsp").forward(request, response);	// If not logged in send back to login page
		}
	}

}
