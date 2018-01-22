package com.barclays.baggage.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 
 * Created on Jan 22, 2018tim12:06:59 AM by @author rboddu
 *
 */
public class ConveyorGraph {

	private Map<String, GateNode> gates = new HashMap<>();

	public boolean containsNode(String nodeId) {
		if (getNodeById(nodeId) == null) {
			return false;
		}
		return true;
	}

	public GateNode getNodeById(String nodeId) {
		return gates.get(nodeId);
	}

	public void addGateNode(String gateId, GateNode gate) {
		gates.put(gateId, gate);
	}

	/*
	 * Dijkstra's Shortest Path algorithm
	 * 
	 */
	public List<GateNode> findShortestPath(GateNode source, GateNode destination) {

		PriorityQueue<GateNode> gateQueue = new PriorityQueue<>();
		source.setTravelTime(0D);
		for (GateNode gate : gates.values()) {
			if (!gate.equals(source)) {
				gate.setTravelTime(Double.MAX_VALUE);
			}
			gate.setShortestPath(new LinkedList<>());
			gateQueue.add(gate);
		}

		while (!gateQueue.isEmpty() || Double.compare(gateQueue.poll().getTravelTime(), Double.MAX_VALUE) == 0) {
			GateNode minGate = gateQueue.peek();

			if (minGate.equals(destination)) {
				return minGate.getShortestPath();// Shortest Path

			}
			gateQueue.poll();

			for (Map.Entry<GateNode, Double> entry : minGate.getAdjacentGates().entrySet()) {
				GateNode adjGate = entry.getKey();
				double adjTime = entry.getValue();
				double newTime = minGate.getTravelTime() + adjTime;
				if (newTime < adjGate.getTravelTime()) {
					adjGate.setTravelTime(newTime);
					LinkedList<GateNode> shortestPath = new LinkedList<>(minGate.getShortestPath());
					shortestPath.add(minGate);
					adjGate.setShortestPath(shortestPath);
					// Remove and reinsert this gate to update the PriorityQueue
					gateQueue.remove(adjGate);
					gateQueue.add(adjGate);
				}

			}

		}
		return null;// no path Exists

	}

}
