package dto;

public class CommandePizza {
	
	private int commandeId;
	private int pizzaId;
	
	public CommandePizza() {}
	
	public CommandePizza(int commandeId, int pizzaId) {
		super();
		this.commandeId = commandeId;
		this.pizzaId = pizzaId;
	}

	public int getCommandeId() {
		return commandeId;
	}

	public void setCommandeId(int commandeId) {
		this.commandeId = commandeId;
	}

	public int getPizzaId() {
		return pizzaId;
	}

	public void setPizzaId(int pizzaId) {
		this.pizzaId = pizzaId;
	}
	
	

}
