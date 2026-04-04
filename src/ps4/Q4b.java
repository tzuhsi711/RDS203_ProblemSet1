package ps4;

import java.io.*;
import java.util.*;
import org.apache.commons.math3.linear.*;

public class Q4b {

    public static void main(String[] args) throws Exception {

        // ==========================
        // 0. Read data (Q4a)
        // ==========================

        List<double[]> X_list = new ArrayList<>();
        List<Double> y_list = new ArrayList<>();

        try (BufferedReader file = new BufferedReader(new FileReader("input_data/data_NHANES.csv"))) {

            String line = file.readLine(); // skip header

            while ((line = file.readLine()) != null) {

                String[] tokens = line.split("[,\\s]+");

                if (tokens.length < 6) continue;

                double sex = Double.parseDouble(tokens[1]);
                double age = Double.parseDouble(tokens[2]);
                double bmi = Double.parseDouble(tokens[3]);
                double waist = Double.parseDouble(tokens[4]);
                double meanBP_sys = Double.parseDouble(tokens[5]);

                X_list.add(new double[]{1, age, sex, bmi, waist});
                y_list.add(meanBP_sys);
            }
        }

        // ==========================
        // 1. Convert lists -> arrays
        // ==========================

        int row = X_list.size(); // number of data points (sample)
        int col = 5; // number of columns

        double[][] X = new double[row][col];
        double[] y = new double[row];

        for (int i = 0; i < row; i++) {
            X[i] = X_list.get(i);
            y[i] = y_list.get(i);
        }

        // ==========================
        // 2. Normal equation
        // ==========================

        RealMatrix Xmat = MatrixUtils.createRealMatrix(X);
        RealVector yvec = new ArrayRealVector(y); 

        // 1. Transpose
        RealMatrix Xt = Xmat.transpose();

        // 2. XtX
        RealMatrix XtX = Xt.multiply(Xmat);

        // 3. Inverse
        RealMatrix XtX_inv = MatrixUtils.inverse(XtX);

        // 4. Xty
        RealVector Xty = Xt.operate(yvec);

        // 5. Beta
        RealVector beta = XtX_inv.operate(Xty);

        // ==========================
        // Output
        // ==========================

        System.out.println("Coefficients:");

        for (int i = 0; i < beta.getDimension(); i++) {
            System.out.println("beta " + i + ": " + beta.getEntry(i));
        }
    }
}
