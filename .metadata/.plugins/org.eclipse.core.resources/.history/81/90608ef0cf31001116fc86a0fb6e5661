package ps6;

public class Q6a {
	
	// Given real COVID case data, identify SIR parameters that make the simulated cases match
	// the real data as closely as possible
	
	// Purpose: model prediction
	
	public static double[] runSIR(double beta, double gamma, double alpha, double theta) {

	    double N = 6894000;
	    double I = 1;
	    double S = N - I;
	    
	    int T = 150;
	    double h = 1.0;

	    double[] cases = new double[T + 1];

	    for (int t = 0; t <= T; t++) {

	        // Function pi_t: account for under-reported cases, 
	    	// proportion of reported cases over time
	    	// i.e., time varying reporting probability
	        double pi = 1.0 / (1.0 + Math.exp(-(theta * t - 2)));

	        // Record number of cases at the beginning 
	        // Size of I adjusted for under-reporting
	        cases[t] = pi * I;

	        // Function beta_t: 
	        // people vary their contact rate with others based on the number of current infectious cases
	        // i.e., behavioural response to infection prevalence
	        double beta_t = beta + alpha * Math.max(Math.log(Math.max(I, 1e-10)), 0); // prevents NaN when gradient descent

	        // RK4 steps
	        double dS1 = -beta_t * S * I / N;
	        double dI1 = beta_t * S * I / N - gamma * I;

	        double S2 = S + h * dS1 / 2;
	        double I2 = I + h * dI1 / 2;

	        double dS2 = -beta_t * S2 * I2 / N;
	        double dI2 = beta_t * S2 * I2 / N - gamma * I2;

	        double S3 = S + h * dS2 / 2;
	        double I3 = I + h * dI2 / 2;

	        double dS3 = -beta_t * S3 * I3 / N;
	        double dI3 = beta_t * S3 * I3 / N - gamma * I3;

	        double S4 = S + h * dS3;
	        double I4 = I + h * dI3;

	        double dS4 = -beta_t * S4 * I4 / N;
	        double dI4 = beta_t * S4 * I4 / N - gamma * I4;

	        S += h * (dS1 + 2*dS2 + 2*dS3 + dS4) / 6.0;
	        I += h * (dI1 + 2*dI2 + 2*dI3 + dI4) / 6.0;
	    }

	    return cases;
	}

}
