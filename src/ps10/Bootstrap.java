package ps10;

import java.util.*;

public class Bootstrap {
	
	// ========================================
	// Part (a): blood pressure mean
	// ========================================
	public static double[] bootstrapA(double[] bp, int B) {
		
		// ====================
		// 0. Random generator
		// ====================
		Random rand = new Random();
		
		int n = bp.length;
		double[] bootMeans = new double[B];
		
		// ====================
		// 1. Bootstrap loop
		// ====================
		for(int bt = 0; bt < B; bt++) {
			
			// New sample of same size
			double[] sample = new double[n];
			
			// Drawing
			for(int i = 0; i < n; i++) {
				int idx = rand.nextInt(n); // randomly pick an index
				sample[i] = bp[idx]; // sample with replacement
			}
			
			// Compute bootstrapped mean
			bootMeans[bt] = ComputeMean.calcMean(sample);
		}
		return bootMeans;
	}
	
	// ============================================================
	// Part (b): blood pressure mean accounting for sample weight
	// ============================================================
	public static double[] bootstrapB(double[] bp, double[] wts, int B) {
		
		// ====================
		// 0. Random generator
		//====================
		Random rand = new Random();
		
		int n = bp.length;
		double[] bootMeans = new double[B];
		
		// ====================
		// 1. Compute sum of weights
		// ====================
		double sumW = 0;
		for(double w : wts) {
			sumW += w;
		};
		
		// ====================
		// 2. Compute probability of each weight
		// ====================
		double[] Probs = new double[n];
		for(int i = 0; i < n; i++) {
			Probs[i] = wts[i] / sumW;
		};
		
		// ====================
		// 3. Compute cumulative probability for sampling
		// ====================
		double[] cumProbs = new double[n];
		cumProbs[0] = Probs[0]; // sets the first cumulative value = the first probability
		
		for(int i = 1; i < n; i++) {
			// compute cumulative probability
			// by adding the current probability (Probs[i]) 
			// to the previous cumulative value (cumProbs[i - 1])
			cumProbs[i] = cumProbs[i - 1] + Probs[i];
		}
		
		// ====================
		// 4. Bootstrap loop
		// ====================
		for(int bt = 0; bt < B; bt++) {
			
			
			// Store bootstrap sample, same size as n (bp.length)
			double[] sampleBP = new double[n];
			double[] sampleWts = new double[n];
			
			for(int i = 0; i < n; i++) {
				
				// Generate uniform random number [0,1)
				double ur = rand.nextDouble(); // nextDouble() ensures within the range
				int idx = 0;
				
				// Select observations
				while (cumProbs[idx] < ur) { // when cumulative probability is less than 1
					idx++;
				}
				
				sampleBP[i] = bp[idx]; // Get bootstrapped BP data
				sampleWts[i] = wts[idx]; // Get bootstrapped weight data
			}
			
			// Compute bootstrapped weighted mean
			bootMeans[bt] = ComputeMean.calcWtMean(sampleBP, sampleWts);
		}
		return bootMeans;
		
	}
	
	// ================================================================================
	// Part (c): blood pressure mean accounting for weights, PSU, and stratum
	// ================================================================================
	public static double[] bootstrapC(double[] bp, double[] wts, ArrayList<Integer>[][] indices, 
										int B) {
		
		// =====================
		// 0. Random generator
		// =====================
		Random rand = new Random();
		
		// ArrayList<Integer>[][] indices
		int numStrata = indices.length; // number of rows
		int numPSU = indices[0].length; // number of columns in the first row
		double[] bootMeans = new double[B];
		
		// =====================
		// 1. Bootstrap loop
		// =====================
		for(int bt = 0; bt < B; bt++) {
			
			// Sample storage
			ArrayList<Double> sampleBP = new ArrayList<>();
			ArrayList<Double> sampleWts = new ArrayList<>();
			
			// Loop over strata, preventing mixing across strata
			for(int s = 0; s < numStrata; s++) {
				
				// Loop over PSUs
				// Sample numPSU per strata
				for(int p = 0; p < numPSU; p++) {
					
					// Randomly pick a PSU index
					int sampledPSU = rand.nextInt(numPSU);
					
					// Get all individuals in the current PSU
					for(int idx : indices[s][sampledPSU]) {
						sampleBP.add(bp[idx]); // Add BP data of the current individuals
						sampleWts.add(wts[idx]); // Add weight data of the current individuals
					}
				}
			}
			
			// Convert to arrays
			double[] b = sampleBP.stream().mapToDouble(Double::doubleValue).toArray();
			double[] w = sampleWts.stream().mapToDouble(Double::doubleValue).toArray();
			
			bootMeans[bt] = ComputeMean.calcWtMean(b, w);
		}
		
		return bootMeans;
	}

}













