package dto;

import java.sql.Date;
import java.util.List;

public class Commande {
	
	private int userId;
	private Date date;
	private List<Pizza> pizzas;
	
	public Commande() {}
	
	public Commande(int userId, Date date, List<Pizza> pizzas) {
		this.userId = userId;
		this.date = date;
		this.pizzas = pizzas;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	
}
