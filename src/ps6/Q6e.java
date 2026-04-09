package ps6;

// Purpose: estimate parameter values that minimise the loss score using gradient descent 
// Lecture 4, pg73

public class Q6e {
	
	public static void gradientDesc(double targets[][]) {
		
		// ================
		// 0. Parameters
		// ================
		double beta = 0.6;
		double gamma = 0.4;
		double alpha = 0.0;
		double theta = 0.0;
		
		double step = 1e-7; 
		double prevScore = Double.MAX_VALUE;
		
		int iter = 0;
		
		while (true) {
			
			// ================
			// 1. Run model
			// ================
			double[] cases = Q6a.runSIR(beta, gamma, alpha, theta);
			
			// ================
			// 2. Compute loss
			// ================
			double score = Q6c.scoreModel(targets, cases);
			
			// ================
			// 3. Compute gradient
			// ================
			double[] grad = Q6d.calcGradient(beta, gamma, alpha, theta, targets);
			
			// ================
			// 4. Print progress
			// ================
//			System.out.println("Progress");
//	        System.out.println("Iteration: " + iter);
//            System.out.println("Score: " + score);
//            
//            // parameters
//    		System.out.println("Parameter:");
//    		System.out.println("beta: " + beta);
//    		System.out.println("gamma: " + gamma);
//    		System.out.println("alpha: " + alpha);
//    		System.out.println("theta: " + theta);
//            
//            // gradient
//    		System.out.println("Gradient:");
//    		System.out.println("beta: " + grad[0]);
//    		System.out.println("gamma: " + grad[1]);
//    		System.out.println("alpha: " + grad[2]);
//    		System.out.println("theta: " + grad[3]);
    		
    		// ================
    		// 5. Update parameters: parameters - (step size)*(gradient) (Lecture 3, pg73)
    		// ================
            beta  -= step * grad[0];
            gamma -= step * grad[1];
            alpha -= step * grad[2];
            theta -= step * grad[3];
            
            // ================
            // 6. Stop when converged (score changes by less than 1e-6)
            // ================
            if (iter > 0 && Math.abs(prevScore - score) < 1e-6) {
            	System.out.println("Converged.");
            	break;
            }
            
            prevScore = score;
            iter++;
            
            if (iter > 1000000) {
                System.out.println("Max iterations reached.");
                break;
            }
		}
		
		// ================
		// 7. Final outputs
		// ================
	    System.out.println("Final results:");
	    System.out.println("beta: " + beta);
	    System.out.println("gamma: " + gamma);
	    System.out.println("alpha: " + alpha);
	    System.out.println("theta: " + theta);

	    double[] finalCases = Q6a.runSIR(beta, gamma, alpha, theta);
	    
	    // print final loss score 
	    double finalScore = Q6c.scoreModel(targets, finalCases);
	    System.out.println("Final score: " + finalScore);
	    
	    Q6b.writeOutput(finalCases, targets, "output_data/Q6e.csv");
	    System.out.println("Q6e.csv created.");
	}
}













