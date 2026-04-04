package ps10;

//Purpose (a): calculate the mean of blood pressure distribution, 
//ignoring sample weights and survey design variables
//Bootstrap 1000 times, estimate 95% CI

// Purpose (b): calculate the mean while accounting for each individual's sample weight

public class ComputeMean {
	
	// Part (a): blood pressure mean
	public static double calcMean(double bp[]) {
		
		double sum = 0.0;
		for(double v : bp) sum += v;
		return sum / bp.length;
		
	}
	
	// Part (b): sample weight mean
	public static double calcWtMean(double bp[], double wts[]) {
		
		double num = 0.0, denom = 0.0;
		
		for(int i = 0; i < bp.length; i++) {
			num += bp[i] * wts[i]; // accounts for weight 
			denom += wts[i];
		}
		return num / denom;
	}

}
