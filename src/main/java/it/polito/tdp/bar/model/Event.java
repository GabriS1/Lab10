package it.polito.tdp.bar.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		NEW_GROUP_CLIENT, TABLE_RETURNED
	}
	
	private LocalTime time;
	private EventType type;
	private int nPersone;
	private float tolleranza;
	private int tavoloAssociato;
	
	
	
	public Event(LocalTime time, EventType type, int nPersone, float tolleranza) {
		super();
		this.time = time;
		this.type = type;
		this.nPersone=nPersone;
		this.tolleranza= tolleranza;
	}



	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.time);
	}



	public EventType getType() {
		return this.type;
	}



	public int getnPersone() {
		return nPersone;
	}


	public LocalTime getTime() {
		return time;
	}
	
	public float getTolleranza() {
		return this.tolleranza;
	}
	
	
	public void setTavoloAssociato(int n) {
		this.tavoloAssociato=n;
	}
	
	public int getTavoloAssociato() {
		return this.tavoloAssociato;
	}



	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + ", nPersone=" + nPersone + ", tavoloAssociato="
				+ tavoloAssociato + "]";
	}
	
	

}
