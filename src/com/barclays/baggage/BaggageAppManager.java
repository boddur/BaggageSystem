package com.barclays.baggage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.barclays.baggage.helper.BaggageInputReader;
import com.barclays.baggage.helper.ConveyorSystemInputReader;
import com.barclays.baggage.helper.FlightDepartureInputReader;
import com.barclays.baggage.model.Bag;
import com.barclays.baggage.model.FlightDeparture;
import com.barclays.baggage.service.ConveyorGraph;
import com.barclays.baggage.service.ConveyorUtil;
import com.barclays.baggage.service.GateNode;

/**
 * 
 * Created on Jan 21, 2018tim7:12:32 PM by @author rboddu
 *	Main class to run the Application by invoking the Helpers to Read the inputs
 *	and Construct the Graph to compute the Shortest Path.
 *	The input is read from res/input.txt and output is created in bin/res/output.txt
 *
 */
public class BaggageAppManager {

    public static void main(String[] args) {

        ConveyorGraph conveyorGraph = null;
        Map<String, FlightDeparture> flightIdToDepartureMap = null;
        Map<String, Bag> bagIdToBagMap = null;

        ConveyorSystemInputReader conveyorInputReader = new ConveyorSystemInputReader();
        try {
        	conveyorInputReader.readConveyorSystem();
            conveyorGraph = conveyorInputReader.getConveyorGraph();
            //System.out.println(conveyorGraph);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        FlightDepartureInputReader flightDepartureInputReader = new FlightDepartureInputReader();
        try {
        	flightDepartureInputReader.readFlightSection();
            flightIdToDepartureMap = flightDepartureInputReader.getFlightIdToDepartureMap();
            //System.out.println(flightIdToDepartureMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        BaggageInputReader bagInputReader = new BaggageInputReader();
        try {
        	bagInputReader.readBagSection();
            bagIdToBagMap = bagInputReader.getBagIdToBagMap();
            //System.out.println(bagIdToBagMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        StringBuffer output = new StringBuffer();

        for (Map.Entry<String, Bag> entry : bagIdToBagMap.entrySet()) {
            Bag bag = entry.getValue();
            String bagId = bag.getBagId();
            String flightId = bag.getFlightId();
            String sourceGate = bag.getGate();

            output.append(bagId + " ");


            String departureGate = null;
            if (flightId.equalsIgnoreCase("ARRIVAL")) {
                departureGate = "BaggageClaim";
            } else {
                departureGate = flightIdToDepartureMap.get(flightId).getFlightGate();
            }

            GateNode sourceNode = ConveyorUtil.findSource(bag, conveyorGraph);
            GateNode targetNode = ConveyorUtil.findDestination(departureGate, conveyorGraph);
            List<GateNode> shortestPath = conveyorGraph.findShortestPath(sourceNode, targetNode);

            if (!shortestPath.isEmpty()) {

                for (int i = 0; i < shortestPath.size(); i++) {
                	GateNode current = shortestPath.get(i);
                    output.append(current.getNodeId()+ " ");
                }
                output.append(targetNode.getNodeId()+ " ");
                output.append(": " + targetNode.getTravelTime());
                output.append(System.lineSeparator());
            } else { //no path found
                output.append("Path doesnot exist");
                output.append(System.lineSeparator());
            }
        }

        System.out.println(output.toString());
        //Write output to output.txt file
        ClassLoader classLoader = BaggageAppManager.class.getClassLoader();
	    File file = new File(classLoader.getResource("res/output.txt").getFile());
		try {
			Files.write(Paths.get(file.getPath()), output.toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
