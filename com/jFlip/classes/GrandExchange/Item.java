package com.jFlip.classes.GrandExchange;

public class Item {
	private final String Name;
	private final int Price;
	private final int ID;

	public Item(String Name, int Price, int ID) {
		this.Name = Name;

		this.Price = Price;
		this.ID = ID;
	}

	public String getName() {
		return Name;
	}

	public int getPrice() {
		return Price;
	}

	public int getID() {
		return ID;
	}

}
