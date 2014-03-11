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

import models.Game;
import models.GameBoard;
import models.Player;
import models.User;

/**
 * Servlet implementation class StartGame
 */
@WebServlet("/StartGame")
public class StartGame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		Player user1 = (Player)request.getAttribute("user1");
		String name1 = request.getParameter("name1"); 
		String name2 = request.getParameter("name2"); 
		
		System.out.println(user1);
		
		String url = "/views/gameBoard.jsp"; 
		String msg = null;
		System.out.println("gete hererere");
		if (name1 == null || name1.isEmpty() || name2 == null || name2.isEmpty()) 
			msg="Please fill out all fields";
		else {
			Player player1 = new Player(name1, 1);
			Player player2 = new Player(name2, 2);
			List<Player> players = new ArrayList<Player>();
			players.add(player1);
			players.add(player2);
			Game game = new Game(players);
			session.setAttribute("game", game);
			msg="New Game! "+ game.getCurrentPlayer().getName()+ ", its your turn";
		}
		request.setAttribute("msg", msg);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
	}

}
