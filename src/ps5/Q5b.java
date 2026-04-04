package ps5;

public class Q5b {
	
	public static void main(String[] args) {
		
		double[][] A = {
				{5, 3, 1},
				{3, 2, 1},
				{1, 1, 2}
		};
		
		double[][] L = Q5a.cholesky(A);
		System.out.println("L: ");
		Q5a.printMat(L);
				
	}

}
