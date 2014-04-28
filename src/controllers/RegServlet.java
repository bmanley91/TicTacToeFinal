package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;





import models.Database;
import models.Player;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");		// gets player username
		String pw = request.getParameter("password");			// gets player password
		String rePw = request.getParameter("retypePassword");	// check if pw matches retyped pw
		
		String url = "/views/home.jsp"; 						// url for page
		String msg = null;										
		Long newUserId = null;
		
		if(username == null || username.isEmpty() || pw.isEmpty() || rePw.isEmpty()){
			msg="Please fill out all fields.";					// error message if fields aren't filled in
			url= "/views/login.jsp"; 							// send back to login page
		}
		else{
			PreparedStatement regPlayer = null;					// declare statement to use later
			ResultSet rs;										// declare result set to use later
			String stmt =
					"INSERT INTO Player (p_username,p_password) VALUES(?,?)";		// syntax for SQL statement
			try{
				regPlayer = Database.getConnection().prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
				regPlayer.setString(1, username);				// put username into statement
				regPlayer.setString(2, pw);						// put password into statement
				regPlayer.executeUpdate();						// execute statement
				rs = regPlayer.getGeneratedKeys();				// save the result set
				if (rs != null && rs.next()) {					
					newUserId = rs.getLong(1);
				}
			}
			catch (SQLException | ClassNotFoundException s){	// catch sql exception
				System.out.println("SQL statement is not executed:");
				System.out.println(s);
				if (s instanceof SQLIntegrityConstraintViolationException) {
					System.out.println("error creating user");
					msg = "Username already exists";
					}
				}
			finally{											
				if(regPlayer != null){
					try {
		           		regPlayer.close();						// close statement after execution
						} 
					catch (SQLException e) {
							e.printStackTrace();				
						} 
				}
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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}



}
