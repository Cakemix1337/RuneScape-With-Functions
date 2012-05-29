package com.jFlip.classes.GrandExchange;

public class Item {
	private final int ID;
	private final String Name;
	private final int Price;

	public Item(String Name, int Price, int ID) {
		this.Name = Name;

		this.Price = Price;
		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return Name;
	}

	public int getPrice() {
		return Price;
	}

}
