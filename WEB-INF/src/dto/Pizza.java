package dto;

public class Pizza {
	
	private int id;
	private String name;
	private double price;
	private String dough;
	
	public Pizza() {}
	
	public Pizza(int id, String name, double price, String dough) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.dough = dough;
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
	
	public String getDough() {
		return dough;
	}
	
	public void setDough(String dough) {
		this.dough = dough;
	}
	
}
