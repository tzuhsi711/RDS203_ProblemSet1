package ps1;

public class Q1ab {

	// Q1a: BMI density (log-normal)
	public static double calcBMI_Density(double x, double mean, double sd) {
		if (x <= 0)
			return 0;

		// mu
		double mu = Math.log((mean * mean) / Math.sqrt(sd * sd + mean * mean));

		// sigma
		double sigma = Math.sqrt(Math.log(1 + (sd * sd) / (mean * mean)));

		// coefficient
		double coef = 1.0 / (x * sigma * Math.sqrt(2 * Math.PI));

		// exponent
		double exponent = -Math.pow(Math.log(x) - mu, 2) / (2 * sigma * sigma);

		return coef * Math.exp(exponent);
	}

	// Q1b: Relative Risk
	public static double calcBMI_RR(double x, double rr) {
		return Math.max(Math.pow(rr, (x - 21)), 1.0);
	}
}
