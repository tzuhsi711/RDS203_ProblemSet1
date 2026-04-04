package ps10;

import java.io.*;
import java.util.*;

public class ReadCSV {
	
	// Variables
    public ArrayList<Double> bpList = new ArrayList<>(); // bpSys
    public ArrayList<Double> wtList = new ArrayList<>(); // sampwt
    public ArrayList<Integer> psuList = new ArrayList<>(); // psu
    public ArrayList<Integer> strataList = new ArrayList<>(); // strata
    
    public void load(String filename) throws Exception{
    	
    	BufferedReader file = new BufferedReader(new FileReader(filename));
    	String line = file.readLine(); // skip header
    	
    	while((line = file.readLine()) != null) {
    		String[] tokens = line.split(",");
    		
    		bpList.add(Double.parseDouble(tokens[1]));
    		wtList.add(Double.parseDouble(tokens[2]));
    		psuList.add(Integer.parseInt(tokens[3]));
    		strataList.add(Integer.parseInt(tokens[4]));
    	}
    	file.close();
    }
    
    // Convert to arrays
    public double[] getBP() {
    	return bpList.stream().mapToDouble(Double::doubleValue).toArray();
    }
    public double[] getWT() {
    	return wtList.stream().mapToDouble(Double::doubleValue).toArray();
    }
    public int[] getPSU() {
    	return psuList.stream().mapToInt(Integer::intValue).toArray();
    }
    public int[] getSTRATA() {
    	return strataList.stream().mapToInt(Integer::intValue).toArray();
    }


}
