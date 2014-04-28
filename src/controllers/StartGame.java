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

import models.*;

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
		Player user = (Player)session.getAttribute("user");
		String name1 = request.getParameter("name1"); 
		String player2Name = request.getParameter("name2");
		int difficulty = Integer.parseInt(request.getParameter("compDifficulty"));
		
		String url = "/views/gameBoard.jsp"; 
		String msg = null;
		if (name1 == null || name1.isEmpty() || player2Name == null || player2Name.isEmpty()) 
			msg="Please fill out all fields";
		else {
			Game game;
			if(difficulty != 0) {
				game = new Game(user, difficulty);
				if(game.getCurrentPlayer().isComputer() )
					game.takeTurn(null, null, game.playersTurn);
			}
			else 
				game = new Game(user, player2Name);
			session.setAttribute("localGame", game);
			request.setAttribute("game", game);
			msg="New Game! "+ game.getMsg();
		}
		request.setAttribute("msg", msg);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
		try {
			Database.DB_Close();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
