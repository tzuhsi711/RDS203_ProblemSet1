package ps6;

// Purpose: calculate gradient using finite differences (central difference approach) 
// i.e., how the loss changes when each parameter changes
// Lecture 4, pg 66

public class Q6d {
	
	public static double[] calcGradient(double beta, double gamma, double alpha, double theta,
										double targets[][]) {
		
		// =================
		// 0. Preparation
		// =================
		// Step size
		double h = 0.001;
		
		// Output: array of size 4, estimated gradient for the current parameter values
		double[] grad = new double[4];
		
		double[] cases_plus;
		double[] cases_minus;
		double loss_plus;
		double loss_minus;
		
		// =================
		// 1. Compute beta
		// =================
		cases_plus = Q6a.runSIR(beta+h, gamma, alpha, theta);
		cases_minus = Q6a.runSIR(beta-h, gamma, alpha, theta);
		
		loss_plus = Q6c.scoreModel(targets, cases_plus);
		loss_minus = Q6c.scoreModel(targets, cases_minus);
		
		grad[0] = (loss_plus - loss_minus) / (2 * h);
		
		// =================
		// 2. Compute gamma
		// =================
		cases_plus = Q6a.runSIR(beta, gamma+h, alpha, theta);
		cases_minus = Q6a.runSIR(beta, gamma-h, alpha, theta);
		
		loss_plus = Q6c.scoreModel(targets, cases_plus);
		loss_minus = Q6c.scoreModel(targets, cases_minus);
		
		grad[1] = (loss_plus - loss_minus) / (2 * h);
		
		// =================
		// 3. Compute alpha
		// =================	
		cases_plus = Q6a.runSIR(beta, gamma, alpha+h, theta);
		cases_minus = Q6a.runSIR(beta, gamma, alpha-h, theta);
		
		loss_plus = Q6c.scoreModel(targets, cases_plus);
		loss_minus = Q6c.scoreModel(targets, cases_minus);
		
		grad[2] = (loss_plus - loss_minus) / (2 * h);
		
		// =================
		// 4. Compute theta
		// =================	
		cases_plus = Q6a.runSIR(beta, gamma, alpha, theta+h);
		cases_minus = Q6a.runSIR(beta, gamma, alpha, theta-h);
		
		loss_plus = Q6c.scoreModel(targets, cases_plus);
		loss_minus = Q6c.scoreModel(targets, cases_minus);
		
		grad[3] = (loss_plus - loss_minus) / (2 * h);
		
		// =================
		// 5. Output
		// =================	
		return grad;
				
	}

}














