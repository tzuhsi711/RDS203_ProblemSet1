package ps9;

//import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.math3.distribution.BinomialDistribution;

// Purpose: draw 1000 samples from binomial distribution X ~ Bin(1 000 000, 0.01)

public class Q9b {
	
	public static void main(String[] args) {
		
		BinomialDistribution binom = new BinomialDistribution(1000000, 0.01);
		
		int[] samples = new int[1000];
		
		// =======================
		// 1. Draw 1000 random samples from binomial distribution
		// =======================
		for(int i = 0; i < 1000; i++) {
			samples[i] = binom.sample();
		}
		
		// =======================
		// 2. Print values
		// =======================
		for(int i = 0; i < 10; i++) {
			System.out.println(samples[i]);
		}
		
		// =======================
		// 3. Output CSV file
		// =======================
		try(FileWriter writer = new FileWriter("output_data/Q9b.csv")){
			
			// header
			writer.write("x\n");
			
			// values (outcomes are counts -> whole number -> integer)
			for(int val : samples) {
				writer.write(val + "\n");
			}
			
			System.out.println("Q9b.csv created.");
		} catch(IOException e) {
			e.printStackTrace();
			}
	}
	

}
