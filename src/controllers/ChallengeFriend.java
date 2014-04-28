package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Database;
import models.Game;
import models.Player;

/**
 * Servlet implementation class ChallengeFriend
 */
@WebServlet("/ChallengeFriend")
public class ChallengeFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		Player user = (Player)session.getAttribute("user");
		String url = "/views/gameBoard.jsp"; 
		String msg = null;
		long friendId = 0;
		if(request.getParameter("friendId") != null)
			friendId = Long.parseLong(request.getParameter("friendId"));
		if(friendId == 0) {
			url = "/views/friends.jsp";
			msg = "Could not find friend, please try gain";
		}
		else {
			Player friend = Database.findPlayerById(friendId);
			Game g = new Game(user, friend);
			g = Database.createGame(g);
			if(g != null) {
				request.setAttribute("game", g);
				msg="New Game! "+ g.getMsg();
			}
			else
				msg="Error creating game. Please try again";
		}
		request.setAttribute("msg", msg);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
		dispatcher.forward(request, response);
		try {
			Database.DB_Close();
		} catch (Throwable e) {
			System.out.println("error closing connection");
			e.printStackTrace();
		}
	}

}
