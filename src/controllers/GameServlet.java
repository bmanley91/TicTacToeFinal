package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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
		Game game = (Game) session.getAttribute("game");
		String xChoice, yChoice;
		PreparedStatement update1 = null;
		PreparedStatement update2 = null;
		Connection con = null;
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();
		
			xChoice =  request.getParameter("xChoice");
			yChoice =  request.getParameter("yChoice");
		int playersTurn = Integer.valueOf(request.getParameter("playersTurn"));	
		game.takeTurn(xChoice,yChoice,playersTurn);
		if(!game.isOver())
			msg=game.getCurrentPlayer().getName()+ ", its your turn";
		
		else if(game.isWinner() || game.isTie){
			if(game.isWinner())
				msg=game.getWinner().getName()+ " Wins!";
			else{
				msg="It's a Tie!";
			}
			
	    	ResultSet rs = null;
	    	String selectStatement =			//logic for prepared statement
					 "UPDATE Player SET p_wins = p_wins + 1 WHERE p_username = ?;";
	    	String selectStatement2 =			//logic for prepared statement
	    			"UPDATE Player SET p_loss = p_loss + 1 WHERE p_username = ?;";
	    	if(game.isTie){
	    		selectStatement = "UPDATE Player SET p_tie = p_tie + 1 WHERE p_username = ?;";
	    		selectStatement2 = "UPDATE Player SET p_tie = p_tie + 1 WHERE p_username = ?;";
	    	}
	    	else if(!player1.equals(game.getWinner())){
	    		selectStatement = "UPDATE Player SET p_loss = p_loss + 1 WHERE p_username = ?;";
	    		selectStatement2 = "UPDATE Player SET p_wins = p_wins + 1 WHERE p_username = ?;";
	    	}
	    	
	    	
	    	try{
	    		con = Database.getConnection();
	    		update1 = con.prepareStatement(selectStatement);	//get connection and declare prepared statement
	    		update2 = con.prepareStatement(selectStatement2);
	    		
	    		update1.setString(1, player1.username); 		// set input parameter 1
	    		update2.setString(1, player2.username); 		// set input parameter 2
	    		
	    		update1.executeUpdate();					// execute statement
	    		update2.executeUpdate();					// execute statement
	    		
	    		//findPlayer.addBatch(selectStatement2);		//add second update
	    		//findPlayer.setString(2, player2.username); 		// set input parameter 2
	    		//findPlayer.set
	    		//findPlayer.executeBatch();					// execute statement
	    		
	           
				//while(rs.next()) {
					//player  = new Player(rs.getString("p_username"), rs.getString("p_password"),rs.getLong("p_id"));
				//}
	       }
	       catch (SQLException | ClassNotFoundException s){
	           	System.out.println("SQL statement is not executed:");
	           	System.out.println(s);
	           	if (s instanceof SQLIntegrityConstraintViolationException) {
	           		System.out.println("error creating user");
	           	}
	       }
	       finally {
	           if (update1 != null) { 
	           	try {
	           		con.close();
	           		//findPlayer.close();
	           		//findPlayer2.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
	           }
	       }
			
		}
		request.setAttribute("msg", msg);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
		
		
	}

}
