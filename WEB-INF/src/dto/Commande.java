package dto;

import java.sql.Date;
import java.util.List;

public class Commande {
	
	private int id;
	private int userId;
	private Date dateCommande;
	private List<Pizza> pizzas;
	
	public Commande() {}

	public Commande(int id, int userId, Date dateCommande, List<Pizza> pizzas) {
		super();
		this.id = id;
		this.userId = userId;
		this.dateCommande = dateCommande;
		this.pizzas = pizzas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	public double calculateFinalPrice() {
		double finalPrice = 0;
		for(Pizza pizza : this.pizzas) {
			finalPrice += pizza.calculateFinalPrice();
		}
		return finalPrice;
	}
	
}
