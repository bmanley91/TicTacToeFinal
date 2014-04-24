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
		Player user1 = (Player)request.getAttribute("user1");
		String name1 = request.getParameter("name1"); 
		String name2 = request.getParameter("name2");
		int difficulty = Integer.parseInt(request.getParameter("compDifficulty"));
		Player player1 = new Player();
		Player player2 = new Player();
		
		String url = "/views/gameBoard.jsp"; 
		String msg = null;
		if (name1 == null || name1.isEmpty() || name2 == null || name2.isEmpty()) 
			msg="Please fill out all fields";
		else {
			player1.username = name1;
			player1.setId(1);
			player2.username = name2;
			player1.setId(2);
			List<Player> players = new ArrayList<Player>();
			Game game = new Game();
			
			if(difficulty != 0){
				game = new Game(player1, difficulty);
				
				players.add(player1);
				players.add(game.comp);
				
				if(game.getCurrentPlayer().isComputer() ){
					//String m[] = game.comp.compTurn();
					game.takeTurn(null, null, game.playersTurn);
				}
			}
			else{
				players.add(player1);
				players.add(player2);
				game = new Game(players);
				
			}
			

			session.setAttribute("game", game);
			msg="New Game! "+ game.getCurrentPlayer().getName()+ ", its your turn";
		}
		request.setAttribute("msg", msg);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
	}

}
