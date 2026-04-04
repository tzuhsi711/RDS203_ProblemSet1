package ps5;

public class Q5a {
	
	public static double[][] cholesky(double[][] A){
		
		int n = A.length;
		double[][] L = new double[n][n];
		
		// Cholesky-Banachiewicz algorithm (Lecture 3, pg192)
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				
				double sum = 0.0;
				
				// Compute sum
				for (int k = 0; k < j; k++){
					sum += L[i][k] * L[j][k];
				}
				
				if (i == j) {
					// Diagonal
					L[i][j] = Math.sqrt(A[i][i] - sum);
				} else {
					L[i][j] = (A[i][j] - sum) / L[j][j];
				}
				
			}
		}
	return L;
	}
	
	// Matrix
	public static void printMat(double[][] M) {
		for (double [] row: M) {
			for (double val: row) {
				System.out.printf("%10.5f ", val);
			}
			System.out.println();
		}
	}

}
