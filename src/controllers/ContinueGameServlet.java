package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Database;
import models.Game;

/**
 * Servlet implementation class ContinueGameServlet
 */
@WebServlet("/ContinueGameServlet")
public class ContinueGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String url = "/views/gameBoard.jsp"; 
		String msg = null;
		long gameId = Long.parseLong(request.getParameter("gameId"));
		if(gameId == 0) {
			msg="Error Finding Game, Please Try Again";
			url = "/views/myGames.jsp";
		}
		else {
			Game g = Database.findGameById(gameId);
			request.setAttribute("game", g);
			msg = g.getMsg();
			System.out.println("GAME MSG: " +g.getMsg());
		}
			
		System.out.println("IN CONTINUE GAME" + gameId);
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
