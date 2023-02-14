package controlleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.PizzaDAO;
import dao.PizzaIngredientDAO;
import dto.Ingredient;
import dto.Pizza;

@WebServlet("/pizzas/*")
public class PizzaRestAPI extends MyServlet {
	
	private PizzaDAO pizzaDAO = new PizzaDAO();
	private PizzaIngredientDAO pizInDAO = new PizzaIngredientDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")) {
			out.println(mapper.writeValueAsString(pizzaDAO.findAll()));
			return;
		}
		
		String[] infos = req.getPathInfo().split("/");
		if(infos.length < 2 || infos.length >3) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		try {
			Integer pizzaId = Integer.parseInt(infos[1]);
			Pizza pizza = pizzaDAO.findById(pizzaId);
			if(pizza == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			if(infos.length ==3 && infos[2].equalsIgnoreCase("name")) {
				out.println(mapper.writeValueAsString(pizzaDAO.calculateFinalPrice(pizza)));
			}else {
				out.println(mapper.writeValueAsString(pizza));
			}
			
		}catch(NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		String parameter = readParameter(req, res);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")) {
			Pizza pizza = mapper.readValue(parameter, Pizza.class);
			pizzaDAO.save(pizza);
			out.println(parameter);
			return;
		}
		String[] infos = req.getPathInfo().split("/");
		
		try {
			Integer id = Integer.parseInt(infos[1]);
			Pizza foundPizza = pizzaDAO.findById(id);
			Ingredient ingredient = mapper.readValue(parameter, Ingredient.class);
			if(foundPizza == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			pizInDAO.addIngredient(foundPizza, ingredient);
			out.println(foundPizza);
		}catch(NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		out.close();
	}

	@Override
	public void doPatch(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}
	
	protected String readParameter(HttpServletRequest req, HttpServletResponse res)throws IOException  {
		StringBuilder sb = new StringBuilder();
		try(BufferedReader reader = req.getReader()) {
			String line;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return sb.toString();
	}

}
