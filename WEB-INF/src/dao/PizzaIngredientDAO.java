package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import dto.Ingredient;
import dto.Pizza;
import utils.DS;

public class PizzaIngredientDAO {
	
	private DS ds = new DS();
	
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

}
