package dto;

import java.util.List;

public class Pizza {
	
	private int id;
	private String name;
	private double price;
	private List<Ingredient> ingredients;
	
	public Pizza(int id, String name, double price, List<Ingredient> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.ingredients = ingredients;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
}
