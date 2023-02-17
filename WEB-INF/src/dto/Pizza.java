package dto;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
	
	private int id;
	private String name;
	private double basicPrice;
	private String dough;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	public Pizza() {}
	
	public Pizza(int id, String name, double basicPrice, String dough, List<Ingredient> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.basicPrice = basicPrice;
		this.dough = dough;
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

	public double getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(double basicPrice) {
		this.basicPrice = basicPrice;
	}
	
	public String getDough() {
		return dough;
	}
	
	public void setDough(String dough) {
		this.dough = dough;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public double calculateFinalPrice() {
		double finalPrice = this.basicPrice;
		for(Ingredient ingredient : this.ingredients) {
			finalPrice += ingredient.getPrice();
		}
		return finalPrice;
	}
	
}
