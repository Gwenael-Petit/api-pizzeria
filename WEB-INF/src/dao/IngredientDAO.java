package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;
import utils.DS;

public class IngredientDAO {
	
	private DS ds = new DS();
	
	public Ingredient findById(int id) {
		Ingredient res = null;
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from ingredients where id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				res = new Ingredient(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public List<Ingredient> findAll() {
		List<Ingredient> res = new ArrayList<>();
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from ingredients");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				res.add(new Ingredient(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public void save(Ingredient ingredient) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into ingredients values(?,?,?)");
			ps.setInt(1, ingredient.getId());
			ps.setString(2, ingredient.getName());
			ps.setDouble(3, ingredient.getPrice());
			ps.executeUpdate();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean exist(Ingredient ingredient) {
		boolean res = false;
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from ingredients where name=?");
			ps.setString(1, ingredient.getName());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) res = true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public void delete(Ingredient ingredient) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("delete from ingredients where id=?");
			ps.setInt(1, ingredient.getId());
			ps.executeUpdate();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
