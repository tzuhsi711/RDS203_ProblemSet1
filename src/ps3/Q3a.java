package ps3;

import org.apache.commons.math3.linear.*;

public class Q3a {

    public static void main(String[] args) {

        // Data
        double[] x = {0, 5, 7, 13, 19, 25, 32};
        double[] y = {0, 3, 4, 9, 14, 11, 5};

        int n = x.length - 1;        // 6 splines/ intervals
        int unknowns = 4 * n;        // cubic spline = 4 coefficients

        double[][] matrix = new double[unknowns][unknowns];
        double[] constraints = new double[unknowns];

        int row = 0;

        // ---------------------------
        // (1) Interpolation conditions
        // ---------------------------
        for (int i = 0; i < n; i++) {

            int col = 4 * i;

            // S_i(x_i) = y_i
            matrix[row][col] = 1;
            constraints[row] = y[i];
            row++;

            // S_i(x_{i+1}) = y_{i+1}
            double h = x[i+1] - x[i];

            matrix[row][col]     = 1;
            matrix[row][col + 1] = h;
            matrix[row][col + 2] = h*h;
            matrix[row][col + 3] = h*h*h;

            constraints[row] = y[i+1];
            row++;
        }

        // ---------------------------
        // (2) First derivative continuity
        // ---------------------------
        for (int i = 0; i < n - 1; i++) {

            double h = x[i+1] - x[i];

            int col = 4 * i;
            int next = 4 * (i+1);

            matrix[row][col + 1] = 1;
            matrix[row][col + 2] = 2*h;
            matrix[row][col + 3] = 3*h*h;

            matrix[row][next + 1] = -1;

            constraints[row] = 0;
            row++;
        }

        // ---------------------------
        // (3) Second derivative continuity
        // ---------------------------
        for (int i = 0; i < n - 1; i++) {

            double h = x[i+1] - x[i];

            int col = 4 * i;
            int next = 4 * (i+1);

            matrix[row][col + 2] = 2;
            matrix[row][col + 3] = 6*h;

            matrix[row][next + 2] = -2;

            constraints[row] = 0;
            row++;
        }

        // ---------------------------
        // (4) Natural boundary conditions
        // ---------------------------

        // S0''(x0) = 0
        matrix[row][2] = 2;
        constraints[row] = 0;
        row++;

        // Sn-1''(xn) = 0
        double h = x[n] - x[n-1];
        int col = 4 * (n - 1);

        matrix[row][col + 2] = 2;
        matrix[row][col + 3] = 6*h;
        constraints[row] = 0;
        row++;

        // ---------------------------
        // Solve system
        // ---------------------------
        RealMatrix A = MatrixUtils.createRealMatrix(matrix);
        DecompositionSolver solver = new LUDecomposition(A).getSolver();

        RealVector b = new ArrayRealVector(constraints);
        RealVector solution = solver.solve(b);

        double[] coeffs = solution.toArray();

        // ---------------------------
        // Print coefficients
        // ---------------------------
        for (int i = 0; i < n; i++) {
            System.out.println("Spline " + i);
            System.out.println("a=" + coeffs[4*i] +
                               ", b=" + coeffs[4*i+1] +
                               ", c=" + coeffs[4*i+2] +
                               ", d=" + coeffs[4*i+3]);
            System.out.println();
        }

        // ---------------------------
        // Output points for plotting
        // ---------------------------
        System.out.println("x,y");

        for (int i = 0; i < n; i++) {

            for (double xi = x[i]; xi <= x[i+1]; xi += 0.1) {

                double dx = xi - x[i];

                double yi = coeffs[4*i]
                          + coeffs[4*i+1]*dx
                          + coeffs[4*i+2]*dx*dx
                          + coeffs[4*i+3]*dx*dx*dx;

                System.out.println(xi + "," + yi);
            }
        }
    }
}
