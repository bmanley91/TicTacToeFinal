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
		Player user = (Player) session.getAttribute("user");
		ArrayList<Player> searchResults = user.getFriendsSearch();
		String friendName = searchResults.get(0).getName();
		if(friendName==null) friendName = "";

		String url = "/views/friendAdded.jsp";
		String msg = "";
		
		if(user!=null){
			long pId = user.getId();
			boolean newFriendship = Database.addFriend(pId, friendName);
			msg = "Friend added successfully!";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher(url).forward(request, response);
		}
	}

}
