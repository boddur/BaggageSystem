package com.barclays.baggage.helper;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.barclays.baggage.model.FlightDeparture;

/**
 * 
 * Created on Jan 21, 2018tim6:48:52 PM by @author rboddu
 *
 */
public class FlightDepartureInputReader{

	Map<String, FlightDeparture> flightIdToDepartureMap;

	public void readFlightSection() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("res/input.txt").getFile());

		Scanner scanner = null;
		try{
			scanner = new Scanner(file);
			boolean startDepartureSection = false;
			boolean endDepartureSection = false;

			while (scanner.hasNextLine() && !endDepartureSection) {
				String line = scanner.nextLine();

				if (line.trim().equals("")){
					continue;
				}

				if (line.startsWith("# Section:")) { //start of a new section
					if (!line.endsWith("Departures")) { //skip
						startDepartureSection = false;
						continue;
					} else if (line.endsWith("Departures")) {
						startDepartureSection = true;
						flightIdToDepartureMap = new HashMap<>();
						continue;
					} else if (startDepartureSection && !line.endsWith("Departures")) {
						endDepartureSection = true;
					}
				}

				if (startDepartureSection && !endDepartureSection) {
					//Format: <flight_id> <flight_gate> <destination> <flight_time>
					String tokens[] = line.split(" ");
					if (tokens.length != 4) {
						throw new IOException("Input is not as Expected");
					}
					String flightId = tokens[0];
					String flightGate = tokens[1];
					String destination = tokens[2];
					String departureTime = tokens[3];


					if (flightGate == null) {
						throw new IOException("Gate Not found");
					}

					flightIdToDepartureMap.put(flightId, new FlightDeparture(flightId, flightGate, destination, departureTime));
				}

			}
		}finally {
			scanner.close();
		}
	}

	public Map<String, FlightDeparture> getFlightIdToDepartureMap() {
		return flightIdToDepartureMap;
	}

	public void setFlightIdToDepartureMap(Map<String, FlightDeparture> flightIdToDepartureMap) {
		this.flightIdToDepartureMap = flightIdToDepartureMap;
	}
}
