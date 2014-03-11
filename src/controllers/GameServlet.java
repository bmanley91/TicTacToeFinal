package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Game;
import models.GameBoard;

/**
 * Servlet implementation class GameServlet
 */
@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String url = "/views/gameBoard.jsp"; 
		String msg = null;
		Game game = (Game) session.getAttribute("game");
		String xChoice =  request.getParameter("xChoice");
		String yChoice =  request.getParameter("yChoice");	
		int playersTurn = Integer.valueOf(request.getParameter("playersTurn"));	
		game.takeTurn(xChoice,yChoice,playersTurn);
		if(!game.isOver())
			msg=game.getCurrentPlayer().getName()+ ", its your turn";
		else if(game.isWinner())
			msg=game.getWinner().getName()+ " Wins!";
		else
			msg="It's a Tie!";
		request.setAttribute("msg", msg);
		/*if (name == null || name.isEmpty()) 
			msg="Goodjob";
		else {
			session.setAttribute("user", name);
			session.setAttribute("gameBoard", new GameBoard());
			msg="New Game! "+ name+ ", its your turn";
		}
		
		*/
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
	}

}
