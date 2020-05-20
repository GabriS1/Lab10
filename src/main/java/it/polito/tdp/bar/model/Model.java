package it.polito.tdp.bar.model;

public class Model {
	
	public static void main(String[] args) {
		
		Simulator simulator = new Simulator();
		
		
		simulator.setTolleranza(0.6);
		simulator.setTavoli4(6);
		simulator.setTavoli6(6);
		simulator.setTavoli8(6);
		simulator.setTavoli10(3);
		
		simulator.run();
		
		System.out.println("Clienti totali: "+simulator.getClienti());
		System.out.println("Clienti soddisfatti: "+simulator.getSoddisfatti());
		System.out.println("Clienti insoddisfatti: "+simulator.getInsoddisfatti());
    }

}
