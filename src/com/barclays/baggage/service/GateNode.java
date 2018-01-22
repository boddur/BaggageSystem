package com.barclays.baggage.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * Created on Jan 22, 2018tim12:08:50 AM by @author rboddu
 * 
 */

public class GateNode implements Comparable<GateNode> {

	private String nodeId;
	private String nodeName;
	private double travelTime = Double.MAX_VALUE;// travel Time from source
	// initialize to maxvalue
	private List<GateNode> shortestPath = new LinkedList<>();
	private Map<GateNode, Double> adjacentGates = new HashMap<>();

	public void addAdjacentGate(GateNode gate, double time) {
		this.adjacentGates.put(gate, time);
	}

	public GateNode(String nodeId) {
		this.nodeId = nodeId;
	}

	public GateNode(String nodeId, String nodeName) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof GateNode)) {
			return false;
		}
		GateNode gate = (GateNode) obj;
		return (this.nodeId.equalsIgnoreCase(gate.nodeId));
	}

	@Override
	public int hashCode() {
		return nodeId.hashCode();
	}

	@Override
	public int compareTo(GateNode other) {
		return Double.compare(travelTime, other.travelTime);
	}

	// Getters and Setters
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public double getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(double travelTime) {
		this.travelTime = travelTime;
	}

	public List<GateNode> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<GateNode> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Map<GateNode, Double> getAdjacentGates() {
		return adjacentGates;
	}

	public void setAdjacentGates(Map<GateNode, Double> adjacentGates) {
		this.adjacentGates = adjacentGates;
	}

}
