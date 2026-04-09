package ps7;

import java.io.*;
import util.MersenneTwisterFast;
import java.util.*;


// Purpose: find a,b,c,d that minimise the mean squared-error
// 10 000 iterations, exponential cooling schedule 0.999^t

public class Q7a {
	
	// ===================
	// 0. MSE function (Lecture 4, pg26)
	// ===================
	public static double computeMSE(double[] x, double[] y,
									double a, double b, double c, double d) {
		double mse = 0.0;
		
		for(int i = 0; i < x.length; i++) {
			
			// Polynomial function from the question
			double pred = a*Math.pow(x[i], 3) + b*Math.pow(x[i], 2) + c*x[i] + d;
			
			// MSE = sum of (pred - obs)^2
			mse += Math.pow(pred - y[i], 2);
		}
		return mse / x.length;
	}
	
	// ===================
	// Main
	// ===================
	public static void main(String[] args) throws Exception {
		
		// ===================
		// 1. Read data
		// ===================
		List<Double> xList = new ArrayList<>();
		List<Double> yList = new ArrayList<>();
		
		BufferedReader file = new BufferedReader(new FileReader("input_data/data_Polynomial.csv"));
		String line;
		
		// skip header
		file.readLine();
		
		while ((line = file.readLine()) != null) {
			String[] tokens = line.split(",");
			xList.add(Double.parseDouble(tokens[0]));
			yList.add(Double.parseDouble(tokens[1]));
		}
		
		file.close();
		
		double[] x = xList.stream().mapToDouble(Double::doubleValue).toArray();
		double[] y = yList.stream().mapToDouble(Double::doubleValue).toArray();
		
		// ===================
		// 2. Initial sampling distribution for each parameters
		// ===================
		MersenneTwisterFast rng = new MersenneTwisterFast(207);
		
        double a = rng.nextGaussian();
        double b = rng.nextGaussian();
        double c = rng.nextGaussian();
        double d = rng.nextGaussian();
        
        double bestMSE = computeMSE(x, y, a, b, c, d);
        double bestA = a, bestB = b, bestC = c, bestD = d;
        
        // ===================
        // 3. Simulated annealing
        // ===================
        for (int t = 0; t < 10000; t++) {
        	
        	double T = Math.pow(0.999, t);
        	
        	// new parameters
            double a_new = a + rng.nextGaussian() * T;
            double b_new = b + rng.nextGaussian() * T;
            double c_new = c + rng.nextGaussian() * T;
            double d_new = d + rng.nextGaussian() * T;
            
            double newMSE = computeMSE(x, y, a_new, b_new, c_new, d_new);
            
            if (newMSE < bestMSE) {
                a = a_new;
                b = b_new;
                c = c_new;
                d = d_new;

                bestMSE = newMSE;
                bestA = a;
                bestB = b;
                bestC = c;
                bestD = d;
            }
        }
        // ===================
        // 5. Results
        // ===================	
        System.out.println("a = " + bestA);
        System.out.println("b = " + bestB);
        System.out.println("c = " + bestC);
        System.out.println("d = " + bestD);
        System.out.println("MSE = " + bestMSE);

        // ===================
        // 6. Save results
        // ===================
        PrintWriter writer = new PrintWriter("output_data/Q7a.csv");
        writer.println("x,y_true,y_pred");

        for (int i = 0; i < x.length; i++) {
            double pred = bestA*Math.pow(x[i],3)
                        + bestB*Math.pow(x[i],2)
                        + bestC*x[i]
                        + bestD;

            writer.println(x[i] + "," + y[i] + "," + pred);
        }

        writer.close();

        System.out.println("Q7.csv created");
        
	}

}



















