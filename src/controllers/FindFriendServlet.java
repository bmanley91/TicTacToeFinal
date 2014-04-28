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
import models.Player;

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
		Player user = (Player) session.getAttribute("user");
		String msg = null;
		String url = null;
		
		if(user!=null){
			String friendSearch = (String)request.getParameter("friendSearch");
			if(friendSearch.length()==0){
				msg = "Please fill in search field.";
				url = "/views/findfriends.jsp";
			}
			else{
				ArrayList<Player> searchResults = Database.searchFriends(friendSearch);
				request.setAttribute("playerSearch", searchResults);
				user.setFriendsSearch(searchResults);
				url = "/views/searchResults.jsp";
			}
		}
		else{
			msg = "You are not logged in!";
			request.setAttribute("msg", msg);
			url = "/views/login.jsp";
		}
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
