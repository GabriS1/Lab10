package it.polito.tdp.bar.model;

public class Tavolo {
	
	
	private int posti;
	private int quantità;
	
	public Tavolo(int posti, int quantità) {
		super();
		this.posti = posti;
		this.quantità = quantità;
	}
	
	public int getPosti() {
		return posti;
	}
	public void setPosti(int posti) {
		this.posti = posti;
	}
	public int getQuantità() {
		return quantità;
	}
	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}
	

}
