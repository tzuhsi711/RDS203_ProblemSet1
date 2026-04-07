package ps6;

import java.io.*;
import java.util.*;

public class Q6c {
	
	// Purpose: read the file
	public static double[][] readCSV(String filename) throws Exception{
		
		List<double[]> data = new ArrayList<>();
		BufferedReader file = new BufferedReader(new FileReader(filename));
		
		file.readLine(); // skip header
		
		String line;
		while ((line = file.readLine()) != null) {
			
			String[] tokens = line.split(",");
			
			double t = Double.parseDouble(tokens[0]);
			double cases = Double.parseDouble(tokens[1]);
			
			data.add(new double[] {t, cases});
		}
		
		file.close();
		
		// observed data
		double [][] targets = new double[data.size()][2];
		
		for (int i = 0; i < data.size(); i++) {
			targets[i] = data.get(i);
		}
		
		return targets;
	}
	
	// Purpose: calculate loss score for the current model parameters
	// Loss score = mean squared error; re-scale by dividing by 1 000 000
	// cases = predicted cases from runSIR()
	public static double scoreModel(double targets[][], double cases[]) {
		
		
		int n = Math.min(targets.length, cases.length);
		double sum = 0;
		
		for (int i = 0; i < n; i++) {
			
			int t = (int) targets[i][0];
			double obs = targets[i][1];
			double pred = cases[t];
			
			double error = obs - pred;
			sum += error * error;
		}
		
		double mse = sum / n;
		
		return mse / 1000000.0;
	}
	
}
