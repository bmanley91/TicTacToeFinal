package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import models.Database;

/*import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;*/

import models.*;
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
		String isLocalGame = request.getParameter("isLocalGame");
		Game game = null;
		if(isLocalGame != null && isLocalGame.equals("true"))
			game = (Game)session.getAttribute("localGame");
		else {
			Long gameId = Long.parseLong(request.getParameter("gameId"));
			game = Database.findGameById(gameId);
		}
		String xChoice, yChoice;
		
		xChoice =  request.getParameter("xChoice");
		yChoice =  request.getParameter("yChoice");
		int playersTurn = Integer.valueOf(request.getParameter("playersTurn"));	
		
		if(game.takeTurn(xChoice,yChoice,playersTurn) && !game.isLocalGame()) {
			Database.updateGame(game);
			// update both players game count
			if(game.isOver() && game.isLocalGame()){
				Database.updateWins(game);
			}
		}
		msg=game.getMsg();
		request.setAttribute("msg", msg);
		request.setAttribute("game", game);
		
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
