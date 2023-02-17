package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Commande;
import dto.Pizza;
import utils.DS;

public class CommandePizzaDAO {
	
	private DS ds = new DS();
	private PizzaDAO pizzaDAO = new PizzaDAO();
	
	public List<Pizza> findById(int commandeId) {
		List<Pizza> res = new ArrayList<Pizza>();
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from commandes_pizzas where commandeId=?");
			ps.setInt(1, commandeId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				res.add(pizzaDAO.findById(rs.getInt("pizzaId")));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public void save(Commande commande) {
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into commandes_pizzas values(?,?)");
			for(Pizza pizza : commande.getPizzas()) {
				ps.setInt(1, commande.getId());
				ps.setInt(2, pizza.getId());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
