package ps9;

import java.io.FileWriter;

import java.io.IOException;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.MersenneTwister;

// Purpose: draw 1000 random samples from Beta distribution X ~ Beta(9,3)

public class Q9c {
	
	public static void main(String[] args) {
		
		// ======================
		// 0. Beta distribution
		// ======================
		MersenneTwister rand = new MersenneTwister(207);
		BetaDistribution beta = new BetaDistribution(rand, 9, 3);
		
		// ======================
		// 1. Draw 1000 samples
		// ======================
		double[] samples = new double[1000];
		
		for(int i = 0; i < 1000; i++) {
			samples[i] = beta.sample();
		}
		
		// ======================
		// 2. Print values
		// ======================
		for(int i = 0; i < 10; i++) {
			System.out.println(samples[i]);
		}
		
		// ======================
		// 3. Output CSV file
		// ======================
		try(FileWriter writer = new FileWriter("output_data/Q9c.csv")){
			
			// header
			writer.write("x\n");
			
			// values (outputs are continuous -> decimals -> double)
			for(double val : samples) {
				writer.write(val + "\n");
			}
			
			System.out.println("Q9c.csv created.");
		} catch(IOException e) {
			e.printStackTrace();
			}
		}
	}
