package ps1;

public class Q1d {

	public static double calcPIF(double mean, double sd, double rr, double effectSize) {

		// baseline BMI distribution (disease risk with original BMI)
		double risk_noIntervention = Q1c.integrate(rr, mean, sd);

		// new mean : mean - effect size (disease risk after BMI reduction)
		double newMean = mean - effectSize;
		double risk_Intervention = Q1c.integrate(rr, newMean, sd);

		return 1.0 - (risk_Intervention / risk_noIntervention);
	}

}
