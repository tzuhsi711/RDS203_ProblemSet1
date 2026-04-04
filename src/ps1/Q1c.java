package ps1;

public class Q1c {

	public static double integrate(double rr, double mean, double sd) {

		double min = 10.0;
		double max = 100.0;
		double step = 0.1;

		// Number of intervals
		int n = (int) ((max - min) / step);

		double sum = 0.0;

		for (int i = 0; i <= n; i++) { // 10, 10.1, 10.2,...,100

			double x = min + i * step; // current x

			double fx = Q1ab.calcBMI_RR(x, rr) * Q1ab.calcBMI_Density(x, mean, sd); // function value

			// Simpson's rule (Lecture 2)
			if (i == 0 || i == n) { // endpoint

				sum += fx; // weight 1

			} else if (i % 2 == 0) { // even indices (overlapping endpoints 1+1=2)

				sum += 2 * fx; // weight 2

			} else { // odd indices

				sum += 4 * fx; // weight 4

			}
		}

		return (step / 3.0) * sum;
	}

}
