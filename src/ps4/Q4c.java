package ps4;

import java.io.*;
import java.util.*;
import org.apache.commons.math3.linear.*;

public class Q4c {
	
	public static void main(String[] args) throws Exception{
		
		// =====================
		// 0. Read data
		// =====================
		List<double[]> X_list = new ArrayList<>();
		List<Double> y_list = new ArrayList<>();
		
		try(BufferedReader file = new BufferedReader (new FileReader("input_data/data_NHANES.csv"))){
			
			String line = file.readLine();
			
			while ((line = file.readLine()) != null) {
				
				String[] tokens = line.split(",");
				
				// Convert data type to double
				double sex = Double.parseDouble(tokens[1]);
				double age = Double.parseDouble(tokens[2]);
				double bmi = Double.parseDouble(tokens[3]);
				double waist = Double.parseDouble(tokens[4]);
				double meanBP_sys = Double.parseDouble(tokens[5]);
				
				X_list.add(new double[] {1, sex, age, bmi, waist});
				y_list.add(meanBP_sys);
			}
		}
		
		// =====================
		// 1. Convert lists to arrays
		// =====================
		int row = X_list.size();
		int col = 5;
		double[][] X = new double[row][col];
		double[] y = new double[row];
		
		for (int i = 0; i < row; i++) {
			X[i] = X_list.get(i);
			y[i] = y_list.get(i);
		}
				
		// =====================
		// 2. QR decomposition (Lecture 3,  pg167)
		// =====================
		// X as matrix, y as vector
		RealMatrix Xmat = MatrixUtils.createRealMatrix(X);
		RealVector yvec = new ArrayRealVector(y);
		
		// Decompose X into QxR
		QRDecomposition qr = new QRDecomposition(Xmat);
		
		// Solver for equation R*beta = Qt*y
		DecompositionSolver solver = qr.getSolver();
		
		// Beta
		RealVector beta = solver.solve(yvec);
		
		
		// =====================
		// 3. Results
		// =====================
		
		System.out.println("Coefficients:");
		for (int i = 0; i < beta.getDimension(); i++) {
			System.out.println("beta " + i + ":" + beta.getEntry(i));
		}
		
		
		
		
	}

}
