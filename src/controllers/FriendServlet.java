package controllers;

import java.io.IOException;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Database;
import models.Player;




@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Player user = (Player)session.getAttribute("user");					// Get player from session
		String msg = null;
		String url = "/views/friends.jsp";
		if(user!=null){														// Check if user is logged in
			ArrayList<Player> friendList = Database.showFriends(user);		// Call show friends method from database object
			user.setPlayerFriends(friendList);								// Save friendList on player object
			request.setAttribute("playerList", friendList);					// Save friendList on request
		}
	else{
		msg = "You are not logged in!";
		url = "/views/login.jsp";
		request.setAttribute("msg", msg);
		}
		
		request.getRequestDispatcher(url).forward(request, response);		// Redirect to friend page to display friends
		try {
			Database.DB_Close();
		} catch (Throwable e) {
			System.out.println("error closing connection");
			e.printStackTrace();
		}
	}
}
