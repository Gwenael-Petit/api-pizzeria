package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Commande;
import dto.Pizza;
import utils.DS;

public class CommandeDAO {
	
	private DS ds = new DS();
	private CommandePizzaDAO commandePizzaDAO = new CommandePizzaDAO();
	
	public Commande findById(int commandeId) {
		Commande commande = null;
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from commandes where id=?");
			ps.setInt(1, commandeId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				List<Pizza> pizzas = commandePizzaDAO.findById(rs.getInt("id"));
				commande = new Commande(rs.getInt("id"), rs.getInt("userId"), rs.getDate("dateCommande"), pizzas);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return commande;
	}
	
	public List<Commande> findAll() {
		List<Commande> res = new ArrayList<Commande>();
		return res;
	}
	
	public void save(Commande commande) {
		
	}

}
