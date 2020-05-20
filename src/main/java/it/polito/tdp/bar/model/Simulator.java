package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {

	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue = new PriorityQueue<>();

	// PARAMETRI DI SIMULAZIONE
	private List<Tavolo> tavoli= new ArrayList<Tavolo>();
	private float tolleranza;

	private final LocalTime oraApertura = LocalTime.of(6, 00);
	private final LocalTime oraChiusura = LocalTime.of(22, 00);

	// MODELLO DEL MONDO

	// VALORI DA CALCOLARE--> key measures
	private int clienti;
	private int insoddisfatti;
	private int soddisfatti;

	// METODI PER IMPOSTARE I PARAMETRI
	public void setTolleranza(double d) {
		this.tolleranza = (float) d;
	}

	public void setTavoli4(int n) {
		this.tavoli.add(new Tavolo(4, n));
	}
	public void setTavoli6(int n) {
		this.tavoli.add(new Tavolo(6, n));
	}
	public void setTavoli8(int n) {
		this.tavoli.add(new Tavolo(8, n));
	}
	public void setTavoli10(int n) {
		this.tavoli.add(new Tavolo(10, n));
	}
	
	// METODI PER RESTITUIRE I RISULTATI
	public int getClienti() {
		return clienti;
	}

	public int getSoddisfatti() {
		return soddisfatti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}

	public void run() {
		// preparazione iniziale (mondo + coda eventi)
		this.clienti = this.insoddisfatti = 0;

		this.queue.clear();
		LocalTime oraArrivoCliente = this.oraApertura;

		// riempiamo la coda con gli eventi: clienti che arrivano
		do {
			int nPersone = (int) (Math.random()*10)+1;
			float tolleranza= (float) Math.random();
			Event e = new Event(oraArrivoCliente, EventType.NEW_GROUP_CLIENT, nPersone, tolleranza); // (marcatura temporale, evento)
			this.queue.add(e);
			Duration d = Duration.of( (int) (Math.random() * 10)+1, ChronoUnit.MINUTES);
			oraArrivoCliente = oraArrivoCliente.plus(d);
		} while (oraArrivoCliente.isBefore(this.oraChiusura));

		
		
		// esecuzione del ciclo di simulazione
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll(); // estrae l'evento e dalla codaPrioritaria--> estraggo eventi in ordine di
											// tempo, poichè loggetto evento ha un comparatore basato sul time
			System.out.println(e);
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch (e.getType()) {
		case NEW_GROUP_CLIENT:
			int nPersone = e.getnPersone();
			//c'è posto al tavolo?
			//1) si 
			for(int i=0; i<this.tavoli.size(); i++) {
				if(nPersone<=tavoli.get(i).getPosti() && tavoli.get(i).getQuantità()>0) {
					if(nPersone>=tavoli.get(i).getPosti()/2) {
						//clienti++
						clienti+=nPersone;
						//clientiSoddisfatti++
						soddisfatti+=nPersone;
						//assegno tavolo, decremento tavoli disponibili
						tavoli.get(i).setQuantità(tavoli.get(i).getQuantità()-1);
						
						int minuti = (int) (Math.random()*61)+60; // [0,1)
						//creo evento Table_Returned
						Event nuovo = new Event(e.getTime().plusMinutes(minuti), EventType.TABLE_RETURNED, nPersone, 0);
						nuovo.setTavoloAssociato(tavoli.get(i).getPosti());
						this.queue.add(nuovo);
						return;
					}
						
				}
			}
			//se esco dal for non ci sono tavoli disponibili 
			//2) no-->valuto bancone
			//valuto tolleranza
			double tol = e.getTolleranza();
			if(tol <= this.tolleranza) {
				//1) tolleranza<tolleranza_input--> clientiInsoddisfatti++
				clienti+=nPersone;
				insoddisfatti+=nPersone;
				System.out.println("Insoddisfatto ;"+tol);
			}else {
				//2)tolleranza>tolleranza_input--> clientiSoddisfatti++
				clienti+=nPersone;
				soddisfatti+=nPersone;
			}
			break;

		case TABLE_RETURNED:
			//aumento quantità tavoli disponibili del tavolo corrispondende
			for(Tavolo t: tavoli) {
				if(t.getPosti()==e.getTavoloAssociato()) {
					t.setQuantità(t.getQuantità()+1);
					break;
				}
			}
			
			
			break;
		}
	}
			
			
		

}
