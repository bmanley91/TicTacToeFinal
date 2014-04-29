package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
		}
	}

}
