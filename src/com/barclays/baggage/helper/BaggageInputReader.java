package com.barclays.baggage.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import com.barclays.baggage.model.Bag;

/**
 * 
 * Created on Jan 21, 2018tim2:04:00 AM by @author rboddu
 *
 */
public class BaggageInputReader implements InputReader {
	
	private Map<String, Bag> bagIdToBagMap;
	public Map<String, Bag> getBagIdToBagMap() {
		return bagIdToBagMap;
	}
	public void setBagIdToBagMap(Map<String, Bag> bagIdToBagMap) {
		this.bagIdToBagMap = bagIdToBagMap;
	}
	public void readSection() throws Exception{
	    ClassLoader classLoader = getClass().getClassLoader();
	    File file = new File(classLoader.getResource("res/input.txt").getFile());
	
	    Scanner scanner = null;
	    try{
	        scanner = new Scanner(file);
	        boolean startBagSection = false;
	        boolean endBagSection = false;
	
	        while (scanner.hasNextLine() && !endBagSection) {
	            String line = scanner.nextLine();
	
	            if (line.trim().equals("")){
	                continue;
	            }
	
	            if (line.startsWith("# Section:")) { //start of a new section
	                if (!line.endsWith("Bags")) { //skip
	                    startBagSection = false;
	                    continue;
	                } else if (line.endsWith("Bags")) {
	                    startBagSection = true;
	                    bagIdToBagMap = new LinkedHashMap<>();
	                    continue;
	                } else if (startBagSection && !line.endsWith("Bags")) {
	                    endBagSection = true;
	                }
	            }
	
	            if (startBagSection && !endBagSection) {
	                //Format: <bag_number> <entry_point> <flight_id>
	                String tokens[] = line.split(" ");
	                if (tokens.length != 3) {
	                    throw new IOException("Input is not as Expected");
	                }
	                String bagId = tokens[0];
	                String entryGate = tokens[1];
	                String flightId = tokens[2];
	
	
	                if (entryGate == null) {
	                    throw new IOException("Gate not found");
	                }
	
	                bagIdToBagMap.put(bagId, new Bag(bagId, entryGate, flightId));
	            }
	
	        }
	    }catch(FileNotFoundException ex){
			throw ex;
		}
	    finally {
	        scanner.close();
	    }
	}


}
