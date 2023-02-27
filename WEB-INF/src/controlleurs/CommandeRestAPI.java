package controlleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.CommandeDAO;
import dto.Commande;
import utils.JwtManager;

@WebServlet("/commandes/*")
public class CommandeRestAPI extends HttpServlet {
	
	private CommandeDAO commandeDAO = new CommandeDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")) {
			out.println(mapper.writeValueAsString(commandeDAO.findAll()));
			return;
		}
		
		String[] infos = req.getPathInfo().split("/");
		if(infos.length < 2 || infos.length > 3) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		try {
			Integer commandeId = Integer.parseInt(infos[1]);
			Commande commande = commandeDAO.findById(commandeId);
			if(commande == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			if(infos.length == 2) {
				out.println(mapper.writeValueAsString(commande));
			}
			else if(infos.length ==3 && infos[2].equalsIgnoreCase("prixfinal")) {
				out.println("{\"prix\": " + commande.calculateFinalPrice() + "}");
			} 
			else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
		}catch(NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(!JwtManager.verifToken(req.getHeader("Authorization"))) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		try(BufferedReader reader = req.getReader()) {
			String line;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Commande commande = mapper.readValue(sb.toString(), Commande.class);
		
		if(commandeDAO.findById(commande.getId()) != null) {
			res.sendError(HttpServletResponse.SC_CONFLICT);
			return;
		}
		
		commandeDAO.save(commande);
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.println(sb.toString());
	}

}
