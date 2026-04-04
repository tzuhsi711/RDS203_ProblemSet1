package ps2;

public class Q2a {
	
	/**
	 * Calculate rate of change in new and recovered cases
	 * @param beta
	 * @param S: susceptible
	 * @param I: infectious
	 * @param N: population size
	 * @return
	 */
	public static double newCases(double beta, double S, double I, double N) {
		return beta * S * (I/N);
	}
	
	public static double recoveredCases(double gamma, double I) {
		return gamma * I;
	}
}
