package ps10;

import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			// ==================
			// 0. Load data
			// ==================
			ReadCSV data = new ReadCSV();
			data.load("input_data/data_NHANES_bp.csv");
			
			double[] bp = data.getBP();
			double[] wt = data.getWT();
			int[] psu = data.getPSU();
			int[] strata = data.getSTRATA();
			
			int n = bp.length;
			int B = 1000;
			
			System.out.println("Sample size: " + n);
			
			// ==================
			// 1. (a) No accounting
			// ==================
			double meanBP = ComputeMean.calcMean(bp);
			double[] bootBP = Bootstrap.bootstrapA(bp, B); 
			
			// 95% CI
			Arrays.sort(bootBP);
			double lowerBP = bootBP[(int)(0.025 * B)];
			double upperBP = bootBP[(int)(0.975 * B)];
			
			System.out.println("\nPart (A):");
			System.out.printf("BP mean = %.2f\n", meanBP);
			System.out.printf("BP 95%% CI = [%.2f, %.2f]\n", lowerBP, upperBP);
			
			
			// ==================
			// 2. (b) Accounting for weight
			// ==================
			double meanBPw = ComputeMean.calcWtMean(bp, wt);
			double[] bootBPw = Bootstrap.bootstrapB(bp, wt, B);
			
			// 95% ci
			Arrays.sort(bootBPw);
			double lowerBPw = bootBPw[(int)(0.025 * B)];
			double upperBPw = bootBPw[(int)(0.975 * B)];
			
			System.out.println("\nPart (B):");
			System.out.printf("BP mean = %.2f\n", meanBPw);
			System.out.printf("BP 95%% CI = [%.2f, %.2f]\n",lowerBPw, upperBPw);
			
			
			// ==================
			// 3. (c) Accounting for PSU + strata
			// ==================
			
			// Map strata  values
			HashMap<Integer, Integer> strataMap = new HashMap<>();
			int strataIndex = 0;
			
			for (int i = 0; i < n; i++) {
				if (!strataMap.containsKey(strata[i])) {
					// Assign strata value a new index
					strataMap.put(strata[i], strataIndex);
					strataIndex++;
				}
			}
			
			int numStrata = strataMap.size();  
			int numPSU = 2; 
			
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[][] indices = (ArrayList<Integer>[][]) new ArrayList[numStrata][numPSU];
			
			// Initialise matrix
			for(int s = 0; s < numStrata; s++) {
				for(int p = 0; p < numPSU; p++) {
					indices[s][p] = new ArrayList<>();
				}
			}
			
			// Fill matrix
			for(int i = 0; i < n; i++) {
				int s = strataMap.get(strata[i]); 
				int p = psu[i] - 1; // Java: 0-indexed            
				
				indices[s][p].add(i);
			}
			
			double[] bootBPall = Bootstrap.bootstrapC(bp, wt, indices, B);
			
			Arrays.sort(bootBPall);
			double lowerBPall = bootBPall[(int)(0.025 * B)];
			double upperBPall = bootBPall[(int)(0.975 * B)];
			
			double meanBPall = ComputeMean.calcWtMean(bp, wt);
			
			System.out.println("\nPart (C):");
			System.out.println("Number of strata: " + numStrata);
			System.out.printf("BP mean = %.2f\n", meanBPall);
			System.out.printf("BP 95%% CI = [%.2f, %.2f]\n", lowerBPall, upperBPall);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}













