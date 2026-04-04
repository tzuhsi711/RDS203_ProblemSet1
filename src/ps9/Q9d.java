package ps9;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class Q9d {
	
	// =======================
	// 0. Define function
	// =======================
	static double f(double x) {
		return 5 * Math.cos(Math.pow(x, 2)) - 2 * x + 25;
	}
	
	public static void main(String[] args) {
		
		// =======================
		// 1. Random generator
		// =======================
		Random rand = new Random();
		List<Double> samples = new ArrayList<>(); // store accepted x values
		
		double xmin = -10, xmax = 10; // sampling range
		
		// =======================
		// 2. Estimate max f(x)
		// Imagine a rectangle with width (x-axis) [-10,10] and height (y-axis) [0,max_f(x)]
		// =======================
		double maxF = Double.NEGATIVE_INFINITY; // start with the smallest possible value
		
		// Find maximum value of f(x)
		for(double x = xmin; x <= xmax; x += 0.01) {
			maxF = Math.max(maxF, f(x));
		}
		
		// =======================
		// 3. Generate random x, y coordinates
		// =======================
		while(samples.size() < 1000) {
			
			// Starting from xmin = -10 because we want [-10,10]
			// rand.nextDouble() returns random number between [0,1]
			// (xmax - xmin) stretches the random number to interval [0,20] (10 - (-10) = 20)
			double x = xmin + (xmax - xmin) * rand.nextDouble();
			
			// rand.nextDouble() returns random number between [0,1]
			// maxF stretches the random number to interval [0, max_f(x)] 
			double y = maxF * rand.nextDouble();
			
			// Ensure y coordinates are within range [0, max_f(x)]
			if(y < f(x)) {
				samples.add(x);
			}
		}
		
		// =======================
		// 4. Check number of samples generated
		// =======================
		System.out.println("Samples generated: " + samples.size());
		
		// =======================
		// 5. Output CSV file
		// =======================
		try(FileWriter writer = new FileWriter("output_data/Q9d.csv")){
			
			// header
			writer.write("x\n");
			
			// values
			for(double val : samples) {
				writer.write(val + "\n");
			}
			
			System.out.println("Q9d.csv created.");
		} catch(IOException e) {
			e.printStackTrace();
			}
		}
	}




























