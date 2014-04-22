package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.GameBoard;
import models.Game;
import models.Computer;

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
		//Computer comp = game.comp;
		//String[] move = new String[2];
		String xChoice, yChoice;
		
		//response.setIntHeader("Refresh", 5);
		
		/*if(game.isComputer()){
			//comp.difficulty = game.getPlayer2().diff;
			move = comp.compTurn();
			
			//while( game.board.isValidMove(move[0], move[1]) )
			
			System.out.println("game servlet compturn "+move[0]+", "+move[1]);
			xChoice = move[0];
			yChoice = move[1];
		}
		else{*/
			xChoice =  request.getParameter("xChoice");
			yChoice =  request.getParameter("yChoice");
		//}
		int playersTurn = Integer.valueOf(request.getParameter("playersTurn"));	
		game.takeTurn(xChoice,yChoice,playersTurn);
		if(!game.isOver())
			msg=game.getCurrentPlayer().getName()+ ", its your turn";
		else if(game.isWinner())
			msg=game.getWinner().getName()+ " Wins!";
			// update getWinner().getName() win count and game count
			// UPDATE Player SET p_wins = p_wins + 1 
			// WHERE p_username = 
		else
			msg="It's a Tie!";
		request.setAttribute("msg", msg);
		// update both players game count
		
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
