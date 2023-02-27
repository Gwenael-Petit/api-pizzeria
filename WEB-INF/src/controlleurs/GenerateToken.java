package controlleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DS;

@WebServlet("/users/token")
public class GenerateToken extends HttpServlet {
	
	private DS ds = new DS();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String login = req.getParameter("login");
		String pwd = req.getParameter("pwd");
		
		if(login == null || pwd ==  null) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String token = "";
		try(Connection con = ds.getConnection()) {
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.println("{\"token\": \"" + token + "\"}");
	}

}
