package ps1;

public class Q1e {

	public static double calcCasesAverted(int cohortSize, double baselineInc, double mean, double sd, double rr,
			double effectSize) {

		// impact a risk factor has on a disease
		// estimates changes in disease risk for a given change in a risk factor
		double PIF = Q1d.calcPIF(mean, sd, rr, effectSize);

		// cohortSize * baselineInc = expected cases
		//
		return cohortSize * baselineInc * PIF;
	}

}
