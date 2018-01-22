package com.barclays.baggage.service;

import java.util.List;

import com.barclays.baggage.model.Bag;
import com.barclays.baggage.model.ConveyorSystem;
import com.barclays.baggage.model.FlightDeparture;
/**
 * 
 * Created on Jan 22, 2018tim12:08:08 AM by @author rboddu
 *	Utility class to Build the Graph from the Inputs
 */

public class ConveyorUtil {
	
	public static ConveyorGraph buildConveyorGraph(List<ConveyorSystem> conveyorList){
		ConveyorGraph conveyorGraph = new ConveyorGraph();
		GateNode from, to;
		for(ConveyorSystem conveyorSystem:conveyorList){
			if(!conveyorGraph.containsNode(conveyorSystem.getFrom())){
				from = new GateNode(conveyorSystem.getFrom());
				conveyorGraph.addGateNode(conveyorSystem.getFrom(), from);
			}else{
				from = conveyorGraph.getNodeById(conveyorSystem.getFrom());
			}
			
			if(!conveyorGraph.containsNode(conveyorSystem.getTo())){
				to = new GateNode(conveyorSystem.getTo());
				conveyorGraph.addGateNode(conveyorSystem.getTo(), to);
			}else{
				to = conveyorGraph.getNodeById(conveyorSystem.getTo());
			}
			//bidirectional graph
			from.addAdjacentGate(to, conveyorSystem.getTravelTime());
			to.addAdjacentGate(from, conveyorSystem.getTravelTime());
			
		}
		return conveyorGraph;
	}
	
	public static GateNode findSource(Bag bag, ConveyorGraph graph){
		return graph.getNodeById(bag.getGate());
		
	}
	public static GateNode findDestination(String flightGate, ConveyorGraph graph){
		return graph.getNodeById(flightGate);
		
	}
	
}
