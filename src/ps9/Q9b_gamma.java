package ps9;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.apache.commons.math3.special.Gamma;

// Purpose: draw 1000 samples from binomial distribution X ~ Bin(1 000 000, 0.01)
// Lecture 3, pg11

public class Q9b_gamma {
	
	// =====================
	// 0. Function: logBinomialPMF (avoid numerical underflow for huge n)
	// =====================
    public static double logBinomialPMF(int k, int n, double p) {
    	
    	// Compute log of components: n! / ( (k! * (n-k)!) )
        double logComb = Gamma.logGamma(n + 1) // n!
                       - Gamma.logGamma(k + 1) // k!
                       - Gamma.logGamma(n - k + 1); // (n-k)!

        // n! / ( (k! * (n-k)!) )
        return logComb
             + k * Math.log(p)
             + (n - k) * Math.log(1 - p);
    }
    
    // Main
    public static void main(String[] args) {

        int n = 1000000;
        double p = 0.01;
        int numSamples = 1000;

        Random rand = new Random(207);

        // ==========================
        // 1. Compute mean and window
        // ==========================
        
        // Mean of binomial = np
        int mean = (int)(n * p); 

        // Window around the mean:
        // Variance = np(1-p) = 9900
        // sd = sqrt(9900) = 99.49 ≈ 100
        // ±5sd ~ 99.99994% probability mass in norm, capturing almost the entire dist.
        // so 5*100 = 500
        
        int lower = mean - 500;  
        int upper = mean + 500;

        int size = upper - lower + 1; // size of the ...

        // ==========================
        // 2. Compute log probabilities within the defined window (lower ~ upper)
        // ==========================
        double[] logProbs = new double[size];
        
        for (int i = 0; i < size; i++) {
            int k = lower + i; // k starts from 0, + lower to map correctly
            logProbs[i] = logBinomialPMF(k, n, p); // compute log probability
        }

        // ==========================
        // 3. Normalise 
        // ==========================
        
        // Get maximum log probability for numerical stability
        double maxLog = Arrays.stream(logProbs).max().getAsDouble();

        double sum = 0;
        double[] probs = new double[size];

        for (int i = 0; i < size; i++) {
        	// Numerical stability:
        	// Largest log prob becomes 1 because log(i) - log(i) = log(i/i) = 0
        	// Rescale all probs relative to the most likely outcome
            probs[i] = Math.exp(logProbs[i] - maxLog);
            sum += probs[i]; // sum of all probs
        }

        for (int i = 0; i < size; i++) {
            probs[i] /= sum; // normalise
        }

        // ==========================
        // 4. Cumulative distribution
        // ==========================
        double[] cumulative = new double[size];
        cumulative[0] = probs[0];

        for (int i = 1; i < size; i++) {
        	// Current prob + all previous cumulative probs
            cumulative[i] = cumulative[i - 1] + probs[i];
        }

        // ==========================
        // 5. Sample 1000 values
        // ==========================
        int[] samples = new int[numSamples];

        for (int i = 0; i < numSamples; i++) {
        	// Generate uniform random number [0,1)
            double ur = rand.nextDouble(); 
            
            // Identify where ur falls in the CDF
            for (int j = 0; j < size; j++) {
            	// Find the first index with cumulative prob GREATER than ur
                if (ur <= cumulative[j]) { 
                    samples[i] = lower + j; // Convert j to actual value 
                    break;
                }
            }
        }

        // ==========================
        // 6. Output CSV file
        // ==========================
        try (FileWriter writer = new FileWriter("output_data/Q9b_gamma.csv")){
        	
        	// header
        	writer.write("x\n");
        	
        	// values
        	for(int val : samples) {
        		writer.write(val + "\n");
        	}
        	
        	System.out.println("Q9b_gamma.csv created.");
        	} catch(IOException e) {
        	e.printStackTrace();
        }
    }
}
















