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

import models.Player;
import models.Game;

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
		int vsPC = Integer.parseInt(request.getParameter("vsPC"));
		//boolean vsPC = request.getParameter("vsPC") != null;
		Player player1 = new Player();
		Player player2 = new Player();
		
		System.out.println("vspc "+vsPC+" "+request.getParameter("vsPC"));
		
		/*if( (vsPC) && !(request.getParameter("vsPC").equals("on")) ){
			vsPC = false;
		}
		System.out.println("vspc2 "+vsPC+" "+request.getParameter("vsPC"));
		*/
		String url = "/views/gameBoard.jsp"; 
		String msg = null;
		System.out.println("gete hererere");
		if (name1 == null || name1.isEmpty() || name2 == null || name2.isEmpty()) 
			msg="Please fill out all fields";
		else {
			player1.username = name1;
			player1.setId(1);
			player2.username = name2;
			player1.setId(2);
			//Player comp = new Player("Computer", 2);
			System.out.println("vs comp = "+vsPC);
			if(vsPC != 0){
				System.out.println("vs comp");
				player2.isComp = true;
				player2.diff = vsPC;
			}
			System.out.println("p2 comp = "+player2.diff);
			
			List<Player> players = new ArrayList<Player>();
			players.add(player1);
			players.add(player2);
			//players.add(comp);
			
			Game game = new Game(players);
			session.setAttribute("game", game);
			game.comp.game = game;
			msg="New Game! "+ game.getCurrentPlayer().getName()+ ", its your turn";
		}
		request.setAttribute("msg", msg);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
	}

}
