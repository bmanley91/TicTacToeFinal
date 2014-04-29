package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Database;
import models.Player;

import java.sql.*;
import java.util.ArrayList;


@WebServlet("/AddFriendServlet")
public class AddFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
<<<<<<< HEAD
		Player user = (Player) session.getAttribute("user");			// Get player from session
		ArrayList<Player> searchResults = user.getFriendsSearch();		// Get friend search results from player object
		String friendName = searchResults.get(0).getName();
		if(friendName==null) friendName = "";							

		String url = "/views/friendAdded.jsp";
		String msg = "";
		
		if(user!=null){
			long pId = user.getId();
			boolean newFriendship = Database.addFriend(pId, friendName);	// Call addFriend method from database object
			msg = "Friend added successfully!";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher(url).forward(request, response);	// Redirect to success page
			try {
				Database.DB_Close();
			} catch (Throwable e) {
				System.out.println("error closing connection");
				e.printStackTrace();
			}
=======
		Player user = (Player) session.getAttribute("user");
		ArrayList<Player> searchResults = user.getFriendsSearch();
		long friendId = Long.parseLong(request.getParameter("friendId"));
		String url = "friendAdded.jsp";
		String msg = "Friend added successfully!";
		
		if(user == null) {
			msg = "You must be logged in to perform this action";
			url = "login.jsp";
		}
		else if(friendId == 0) {
			msg = "Error finding friend, please try again";
			url = "findFriends.jsp";
		} 
		else if(!Database.addFriend(user.getId(), friendId)) 
			msg = "Error adding friend, please try again";
		
		request.setAttribute("msg", msg);
		request.getRequestDispatcher(url).forward(request, response);
		try {
			Database.DB_Close();
		} catch (Throwable e) {
			System.out.println("error closing connection");
			e.printStackTrace();
>>>>>>> FETCH_HEAD
		}
	}

}
