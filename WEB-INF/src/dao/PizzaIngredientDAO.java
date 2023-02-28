package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;
import dto.PizzaIngredient;
import utils.DS;

public class PizzaIngredientDAO {
	
	private DS ds = new DS();
	private IngredientDAO ingredientDAO = new IngredientDAO();
	
	public List<Ingredient> findByPizzaId(int pizzaId) {
		List<Ingredient> res = new ArrayList<Ingredient>();
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from pizzas_ingredients where pizzaId=?");
			ps.setInt(1, pizzaId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				res.add(ingredientDAO.findById(rs.getInt("ingredientId")));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public void addIngredient(PizzaIngredient pizzaIngredient) {
		try(Connection con = ds.getConnection()){
			PreparedStatement ps = con.prepareStatement("insert into pizzas_ingredients values(?,?)");
			ps.setInt(1, pizzaIngredient.getPizzaId());
			ps.setInt(2, pizzaIngredient.getIngredientId());
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void removeIngredient(PizzaIngredient pizzaIngredient) {
		try(Connection con = ds.getConnection()){
			PreparedStatement ps = con.prepareStatement("delete from pizzas_ingredients where pizzaId=? and ingredientId=?");
			ps.setInt(1, pizzaIngredient.getPizzaId());
			ps.setInt(2, pizzaIngredient.getIngredientId());
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void saveAll(List<PizzaIngredient> pizzaIngredients) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into pizzas_ingredients values (?,?)");
			for(PizzaIngredient pizzaIngredient : pizzaIngredients) {
				ps.setInt(1, pizzaIngredient.getPizzaId());
				ps.setInt(2, pizzaIngredient.getIngredientId());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteAll(int pizzaId) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("delete from pizzas_ingredients where pizzaId=?");
			ps.setInt(1, pizzaId);
			ps.executeUpdate();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
