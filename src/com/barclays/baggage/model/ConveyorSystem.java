package com.barclays.baggage.model;
/**
 * 
 * Created on Jan 21, 2018tim10:07:14 PM by @author rboddu
 *
 */
public class ConveyorSystem {

	private String from;
	private String to;
	private double travelTime;
	public ConveyorSystem(String from, String to, double travelTime) {
		this.from = from;
		this.to = to;
		this.travelTime = travelTime;
	}
	
	//Getters and Setters
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public double getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(double travelTime) {
		this.travelTime = travelTime;
	}
	
	
}
