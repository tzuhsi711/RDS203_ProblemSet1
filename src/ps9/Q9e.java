package ps9;

import java.util.*;
import java.io.*;

// Prupose: Samole 1000 pairs of random variables (A,B)
// A ~ N(27.8, 7.5) ; B ~ N(94.6, 19.6) ; pAB = 0.92

public class Q9e {
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		// ===================
		// 0. Parameters and variables
		// ===================
		int n = 1000;
		
		double rho = 0.92;
		
		double meanA = 27.8;
		double varA = 7.5;
		double sigmaA = Math.sqrt(varA);
				
		double meanB = 94.6;
		double varB = 19.6;
		double sigmaB = Math.sqrt(varB);
		
		double covAB = rho * sigmaA * sigmaB;
		double[] A = new double[n];
		double[] B = new double[n];
		
		// ===================
		// 1. Generate correlated samples
		// ===================
		for(int i = 0; i < n; i++) {
			
			// Standard normals
			double z1 = rand.nextGaussian();
			double z2 = rand.nextGaussian();
			
			// A and B
			A[i] = meanA + sigmaA * z1;
			B[i] = meanB + (covAB / sigmaA) * z1 
					+ Math.sqrt(varB - (covAB * covAB) / varA) * z2;
		}
		
		// ===================
		// 2. Output CSV file
		// ===================
		try(FileWriter writer = new FileWriter("output_data/Q9e.csv")){
			
			// header
			writer.write("A,B\n");
			
			// values
			for(int i = 0; i < n; i++) {
				writer.write(A[i] + "," + B[i] + "\n");
			}
			
			System.out.println("Q9e.csv created.");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
