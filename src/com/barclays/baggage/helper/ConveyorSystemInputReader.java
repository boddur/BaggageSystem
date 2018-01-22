package com.barclays.baggage.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.barclays.baggage.model.ConveyorSystem;
import com.barclays.baggage.service.ConveyorGraph;
import com.barclays.baggage.service.ConveyorUtil;

/**
 * 
 * Created on Jan 21, 2018tim6:53:32 PM by @author rboddu
 *
 */
public class ConveyorSystemInputReader {

	private ConveyorGraph conveyorGraph;

	public void readConveyorSystem() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("res/input.txt").getFile());

		Scanner scanner = null;
		List<ConveyorSystem> conveyorList = new ArrayList<>();
		try {
			scanner = new Scanner(file);
			boolean startGraphSection = false;
			boolean endGraphSection = false;

			while (scanner.hasNextLine() && !endGraphSection) {
				String line = scanner.nextLine();

				if (line.trim().equals("")) {
					continue;
				}

				if (line.startsWith("# Section:")) { // start of a new section
					if (!line.endsWith("Conveyor System")) { // skip
						startGraphSection = false;
						continue;
					} else if (line.endsWith("Conveyor System")) {
						startGraphSection = true;
						continue;
					} else if (startGraphSection && !line.endsWith("Conveyor System")) {
						endGraphSection = true;
					}
				}

				if (startGraphSection && !endGraphSection) {
					// format :: <Node 1> <Node 2> <travel_time>
					String tokens[] = line.split(" ");
					if (tokens.length != 3) {
						throw new IOException("Input is not as Expected");
					}
					String from = tokens[0];
					String to = tokens[1];
					double travelTime = Double.parseDouble(tokens[2]);

					if (from == null || to == null) {
						throw new IOException("Gate Not found");
					}

					ConveyorSystem conveyor = new ConveyorSystem(from, to, travelTime);
					conveyorList.add(conveyor);

				}

			}

			// add the bi-directional link in the barclays
			conveyorGraph = ConveyorUtil.buildConveyorGraph(conveyorList);
		} catch (Exception ex) {
			// Handle IO exception
			throw ex;
		} finally {
			scanner.close();
		}
	}

	public ConveyorGraph getConveyorGraph() {
		return conveyorGraph;
	}

	public void setConveyorGraph(ConveyorGraph conveyorGraph) {
		this.conveyorGraph = conveyorGraph;
	}
}
