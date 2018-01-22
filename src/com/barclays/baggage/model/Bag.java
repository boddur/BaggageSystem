package com.barclays.baggage.model;
/**
 * 
 * Created on Jan 22, 2018tim12:13:41 AM by @author rboddu
 *
 */
public class Bag {
	private String bagId;
	private String gate;
	private String flightId;
	
	public Bag(String bagid, String gate, String flight){
		this.bagId = bagid;
		this.gate = gate;
		this.flightId = flight;
	}
	public String getBagId() {
		return bagId;
	}
	public void setBagId(String bagId) {
		this.bagId = bagId;
	}
	
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	
	

}
