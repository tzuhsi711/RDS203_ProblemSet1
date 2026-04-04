package ps6;

public class Q6_main {
	
	public static void main(String[] args) throws Exception {
		
		// ================
		// 1. Read data
		// ================
		double[][] targets = Q6c.readCSV("input_data/data_covid-MA_2020.csv");
		
		// ================
		// 2. Run model
		// ================
		double[] initialCases = Q6a.runSIR(0.6, 0.4, 0.0, 0.0);
		
		// ================
		// 3. Write output
		// ================
		Q6b.writeOutput(initialCases, targets, "output_data/Q6b.csv");
		
		// check number of cases
		System.out.println("Observed cases (targets):" + targets.length);
		System.out.println("Predicted cases (cases):" + initialCases.length);
	

		// ================
		// 4. Compute score
		// ================
		double score = Q6c.scoreModel(targets, initialCases);
		System.out.println("Initial score:" + score);
		
		// ================
		// 5. Compute gradient for current parameter values
		// ================
		double[] grad = Q6d.calcGradient(0.6, 0.4, 0.0, 0.0, targets);
		
		System.out.println("Gradient:");
		System.out.println("beta: " + grad[0]);
		System.out.println("gamma: " + grad[1]);
		System.out.println("alpha: " + grad[2]);
		System.out.println("theta: " + grad[3]);
		
		// ================
		// 6. Gradient descent
		// ================
		Q6e.gradientDesc(targets);
	}

}
