package ps2;

public class Q2b {
	
	/**
	 * Calculate forward Euler for the SIR model (Q2a)
	 * @param beta
	 * @param gamma
	 * @param S
	 * @param I
	 * @param R: recovered or removed 
	 * @param N
	 * @param h: step size
	 * @return
	 */
	public static double[] euler(double beta, double gamma, double S, double I,
								double R, double N, double h) {
		
		double newCase = Q2a.newCases(beta, S, I, N);
		double recovCase = Q2a.recoveredCases(gamma, I);
		
		double S_updated = S - h * newCase;
		double I_updated = I + h * (newCase - recovCase);
		double R_updated = R + h * recovCase;
		
		return new double[] {S_updated, I_updated, R_updated};
				
		
	}

}
