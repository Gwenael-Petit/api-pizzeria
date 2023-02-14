package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;
import dto.Pizza;
import utils.DS;

public class PizzaIngredientDAO {
	
	private DS ds = new DS();
	private IngredientDAO ingredientDAO = new IngredientDAO();
	
	public List<Ingredient> findByPizzaId(int pizzaId) {
		List<Ingredient> res = new ArrayList<Ingredient>();
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from pizzas_ingredients where pizza=?");
			ps.setInt(1, pizzaId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				res.add(ingredientDAO.findById(rs.getInt("ingredient")));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public void addIngredient(Pizza pizza, Ingredient ingredient) {
		try(Connection con = ds.getConnection()){
			PreparedStatement ps = con.prepareStatement("insert into pizzas_ingredients values(?,?)");
			ps.setInt(1, pizza.getId());
			ps.setInt(2, ingredient.getId());
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void removeIngredient(Pizza pizza, Ingredient ingredient) {
		try(Connection con = ds.getConnection()){
			PreparedStatement ps = con.prepareStatement("delete from pizzas_ingredients where pizza=? and ingredient=?");
			ps.setInt(1, pizza.getId());
			ps.setInt(2, ingredient.getId());
			ps.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void save(Pizza pizza) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into pizzas_ingredients values (?,?)");
			for(Ingredient ingredient : pizza.getIngredients()) {
				ps.setInt(1, pizza.getId());
				ps.setInt(2, ingredient.getId());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(Pizza pizza) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("delete from pizzas_ingredients where pizza=?");
			for(Ingredient ingredient : pizza.getIngredients()) {
				ps.setInt(1, pizza.getId());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Pizza pizza) {
		delete(pizza);
		save(pizza);
	}

}
