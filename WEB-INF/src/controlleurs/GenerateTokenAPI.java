package controlleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UsersDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.Base64UrlCodec;
import utils.DS;
import utils.JwtManager;

@WebServlet("/users/token")
public class GenerateTokenAPI extends HttpServlet {
	
	private UsersDAO usersDao = new UsersDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String login = req.getParameter("login");
		String pwd = req.getParameter("pwd");
		
		if(login == null || pwd ==  null) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String token = "";
		if(usersDao.exist(login, pwd)) {
			token = JwtManager.createJWT(login, pwd);
		} else {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.println("{\"token\": \"" + token + "\"}");
	}

}
