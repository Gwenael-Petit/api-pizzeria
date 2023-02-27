package controlleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
			if(infos.length == 2) {
				out.println(mapper.writeValueAsString(pizza));
			}
			if(infos.length ==3 && infos[2].equalsIgnoreCase("prixfinal")) {
				out.println("{\"prix\": " + pizza.calculateFinalPrice() + "}");
			}else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
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
			if(pizzaDAO.findById(pizza.getId()) != null) {
				res.sendError(HttpServletResponse.SC_CONFLICT);
				return;
			}
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
			if(foundPizza.getIngredients().stream().anyMatch(i -> i.getId() == ingredient.getId())) {
				res.sendError(HttpServletResponse.SC_CONFLICT);
				return;
			}
			pizInDAO.addIngredient(foundPizza, ingredient);
			out.println(mapper.writeValueAsString(ingredient));
		}catch(NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		out.close();
	}

	@Override
	public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		String parameter = readParameter(req, res);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String[] infos = req.getPathInfo().split("/");
		if(infos.length < 2 && infos.length > 3) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		try {
			Integer id = Integer.parseInt(infos[1]);
			Pizza pizza = pizzaDAO.findById(id);
			if(pizza == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			 
			JsonNode jsn = mapper.readTree(parameter);
			if(jsn.get("id") != null) {
				pizza.setId(jsn.get("id").asInt());
			}
			if(jsn.get("name") != null) {
				pizza.setName(jsn.get("name").asText());
			}
			if(jsn.get("basicprice") != null) {
				pizza.setBasicPrice(jsn.get("basicprice").asDouble());
			}
			if(jsn.get("dough") != null) {
				pizza.setDough(jsn.get("dough").asText());
			}
			if(jsn.get("ingredients") != null) {
				List<Ingredient> ingredients = new ArrayList<>();
				for(JsonNode node : jsn.get("ingredients")) {
					ingredients.add(mapper.treeToValue(node, Ingredient.class));
				}
				pizza.setIngredients(ingredients);
			}
			
			out.println(mapper.writeValueAsString(pizza));
			
			
		} catch(NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		out.close();
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json;charset=req.getPathInfo()UTF-8");
		
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String[] infos = pathInfo.split("/");
		if(infos.length < 2 && infos.length > 3) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		try {
			Integer id = Integer.parseInt(infos[1]);
			Pizza pizza = pizzaDAO.findById(id);
			if(pizza == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			if(infos.length == 3) {
				Integer idIngredient = Integer.parseInt(infos[2]);
				Ingredient ingredient = null;
				for (Ingredient i : pizza.getIngredients()) {
					if(i.getId() == idIngredient) {
						ingredient = i;
						break;
					}
				}
				if(ingredient == null) {
					res.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				pizInDAO.removeIngredient(pizza, ingredient);
			}else {
				pizzaDAO.delete(pizza);
			}
		} catch(NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
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
