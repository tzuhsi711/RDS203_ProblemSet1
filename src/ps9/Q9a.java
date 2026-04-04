package ps9;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

// Purpose: draw 1000 random samples using the empirical distribution

public class Q9a {
	
	public static void main(String[] args) {
		
		// =====================
		// 0. Empirical data
		// =====================
        String[] countries = {"Nigeria","Ethiopia","Egypt",
        					"DRC","Tanzania", "South Africa",
        					"Kenya","Sudan","Uganda","Algeria"};

        double[] counts_raw = {228,129,115,106,67,63,55,50,49,46};
        
        // =====================
        // 1. Normalise weights to probabilities
        // =====================
        double total = Arrays.stream(counts_raw).sum(); // convert array into stream, sum of all counts
        
        for(int i = 0; i < counts_raw.length; i++) {
        	counts_raw[i] /= total; // convert counts to probabilities (counts/total)
        }
        
        // =====================
        // 2. Random generator
        // =====================
        Random rand = new Random();
        List<String> samples = new ArrayList<>(); // store each sampled country
        
        // =====================
        // 3. Sampling for 1000 iterations
        // =====================
        for(int i = 0; i < 1000; i++) {
        	double r = rand.nextDouble(); // random number between 0,1 to decide which country to pick
        	double prob_cumulative = 0;
        	
        	for(int j = 0; j < counts_raw.length; j++) { // loop through each country
        		prob_cumulative += counts_raw[j]; // compute cumulative probabilities
        		
        		if(r <= prob_cumulative) { // check if random number falls within interval
        			
        			samples.add(countries[j]); // store sampled country for CSV
        			break;
        		}
        	}
        }

        // =====================
        // 4. Output to CSV
        // =====================
        try (FileWriter writer = new FileWriter("output_data/Q9a.csv")) {
        	
        	writer.write("country\n"); // header
        	
        	for(String c : samples) {
        		writer.write(c + "\n");
        	}
        	
        	System.out.println("Q9a.csv created.");
        	
        } catch(IOException e) {
        	e.printStackTrace();
        }
        
	}

}