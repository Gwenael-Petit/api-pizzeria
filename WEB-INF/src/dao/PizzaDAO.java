package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Pizza;
import utils.DS;

public class PizzaDAO {
	
	private DS ds = new DS();
	
	public Pizza findById(int id) {
		Pizza pizza = null;
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from pizzas where id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) pizza = new Pizza(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("dough"));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return pizza;
	}
	
	public List<Pizza> findAll() {
		List<Pizza> pizzas = new ArrayList<>();
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from pizzas");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) pizzas.add(new Pizza(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("dough")));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return pizzas;
	}
	
	public void save(Pizza pizza) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("INSERT INTO pizzas values(?,?,?,?)");
			ps.setInt(1, pizza.getId());
			ps.setString(2, pizza.getName());
			ps.setDouble(3, pizza.getPrice());
			ps.setString(4, pizza.getDough());
			ps.executeUpdate();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(Pizza pizza) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("delete from pizzas where id=?");
			ps.setInt(1, pizza.getId());
			ps.executeUpdate();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Pizza pizza) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("update pizzas set name=?,price=?,dough=? where id=?");
			ps.setString(1, pizza.getName());
			ps.setDouble(2, pizza.getPrice());
			ps.setString(3, pizza.getDough());
			ps.setInt(4, pizza.getId());
			ps.executeUpdate();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
