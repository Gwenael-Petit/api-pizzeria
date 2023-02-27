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

import dao.IngredientDAO;
import dto.Ingredient;
import utils.JwtManager;

@WebServlet("/ingredients/*")
public class IngredientRestAPI extends HttpServlet {
	
	private IngredientDAO ingredientDAO = new IngredientDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")) {
			out.println(mapper.writeValueAsString(ingredientDAO.findAll()));
			return;
		}
		
		String[] infos = req.getPathInfo().split("/");
		if(infos.length < 2 || infos.length >3) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		
		try {
			Integer id = Integer.parseInt(infos[1]);
			Ingredient ingredient = ingredientDAO.findById(id);
			if(ingredient == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			if(infos.length ==3 && infos[2].equalsIgnoreCase("name")) {
				out.println("{\"name\":\"" + ingredient.getName()+ "\"}");
			}else {
				out.println(mapper.writeValueAsString(ingredient));
			}
		} catch(NumberFormatException e) {
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
		Ingredient ingredient = mapper.readValue(sb.toString(), Ingredient.class);
		
		if(ingredientDAO.exist(ingredient)) {
			res.sendError(HttpServletResponse.SC_CONFLICT);
			return;
		}
		
		ingredientDAO.save(ingredient);
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.println(sb.toString());
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(!JwtManager.verifToken(req.getHeader("Authorization"))) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String[] infos = req.getPathInfo().split("/");
		if(infos.length != 2) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		try {
			Integer id = Integer.parseInt(infos[1]);
			Ingredient ingredient = ingredientDAO.findById(id);
			if(ingredient == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			out.println(mapper.writeValueAsString(ingredient));
			ingredientDAO.delete(ingredient);
		} catch(NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		out.close();
	}

}
