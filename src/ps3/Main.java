package ps3;

import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.math3.linear.*;

// (a) Purpose: construct a linear system to calculate the spline coefficients
// (b) Purpose: solve the system, step size 0.1
// output csv file: cubic spline, original points, coefficients for each spline
// Lecture 2, pg179

public class Main {

    public static void main(String[] args) {

    	// ================
        // 0. Input data
    	// ================
        double[] x = {0, 5, 7, 13, 19, 25, 32};
        double[] y = {0, 3, 4, 9, 14, 11, 5};

        int n = x.length - 1; // 6 splines (intervals)
        int unknowns = 4 * n; // cubic spline = 3 + 1 = 4 coefficients/spline

        double[][] matrix = new double[unknowns][unknowns];  // matrix
        double[] rhs = new double[unknowns]; // vector

        int row = 0;

        // ================
        // 1. Interpolation conditions: (Lecture 2, pg175)
        // 	each spline much pass through the end points (x_i,y_i)
        //	S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
        // ================
        for (int i = 0; i < n; i++) {
        		
        	// each spline: 4 consecutive columns
            int col = 4 * i;

            // a) S_i(x_i) = y_i
            // S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
            // At x = x_i
            // S_i(x) = a_i*(0)^3 + b_i*(0)^2 + c_i*(0) + d_i
            // S_i(x) = d_i
            matrix[row][col+3] = 1; // coefficient for the variable in this column (d_i)
            // no matrix[][] for other variables because their coefficients are 0 (a_i, b_i, c_i)
            rhs[row] = y[i]; 
            row++;

            // b) S_i(x_{i+1}) = y_{i+1}
            // S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
            // At x = x_{i+1}
            // S_i(x_{i+1}) = a_i*(x_{i+1}-x_i)^3 + b_i*(x_{i+1}-x_i)^2 + c_i*(x_{i+1}-x_i) + d_i
            double h = x[i+1] - x[i]; // as in a_i*(x_{i+1}-x_i)^3 + ...
            
            // Plug in: S_i(x_{i+1}) = a_i*(h)^3 + b_i*(h)^2 + c_i*(h) + d_i
            matrix[row][col] = h*h*h; // a_i*(h)^3
            matrix[row][col + 1] = h*h; // b_i*(h)^2
            matrix[row][col + 2] = h; //  c_i*(h)
            matrix[row][col + 3] = 1; // d_i

            rhs[row] = y[i+1]; 
            row++;
        }

        // ================
        // 2. Continuous conditions: (Lecture 3, pg176)
        // 	first derivative of splines must match where they meet (left spline = right spline)
        // ================
        for (int i = 0; i < n - 1; i++) {

            int col = 4 * i;
            int next = 4 * (i+1);
            
            // Spline: S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
            
            // Left spline: 
            // S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
            // 1st derivative: S'_i(x) = 3a_i*(x-x_i)^2 + 2b_i*(x-x_i) + c_i
            
            // Right spline:
            // S_{i+1}(x) = a_{i+1}*(x-x_{i+1})^3 + b_{i+1}*(x-x_{i+1})^2 + c_{i+1}*(x-x_{i+1}) + d_{i+1}
            // 1st derivative: S'_{i+1}(x) = 3a_{i+1}*(x-x_{i+1})^2 + 2b_{i+1}*(x-x_{i+1}) + c_{i+1}
            
            // At x = x_{i+1} : end point of left spline, start point of right spline
            // Left 1st derivative: 
            // S'_i(x_{i+1}) = 3a_i*(x_{i+1}-x_i)^2 + 2b_i*(x_{i+1}-x_i) + c_i
            // Right 1st derivative: 
            // S'_{i+1}(x_{i+1}) = 3a_{i+1}*(x_{i+1}-x_{i+1})^2 + 2b_{i+1}*(x_{i+1}-x_{i+1}) + c_{i+1}
            // S'_{i+1}(x_{i+1}) = 3a_{i+1}*(0)^2 + 2b_{i+1}*(0) + c_{i+1} = c_{i+1}
            
            // Continuous conditions: S'_i(x_{i+1}) = S'_{i+1}(x_{i+1}) 
            // Assign x_{i+1}-x_i = h
            // 3a_i*h^2 + 2b_i*h + c_i = c_{i+1}
            // 3a_i*h^2 + 2b_i*h + c_i - c_{i+1} = 0
            
            double h = x[i+1] - x[i];
            
            matrix[row][col] = 3*h*h; // 3a_i*h^2
            matrix[row][col + 1] = 2*h; // 2b_i*h
            matrix[row][col + 2] = 1; // c_i
            matrix[row][next + 2] = -1; // -c_{i+1}
            
            // 3a_i*h^2 + 2b_i*h + c_i - c_{i+1} = 0 = rhs
			rhs[row] = 0;
            row++;
        }

        // ================
        // 3. Smoothness conditions: (Lecture 3, pg176)
        //	second derivative of splines must match where        
        // ================
        
        for (int i = 0; i < n - 1; i++) {

            int col = 4 * i;
            int next = 4 * (i+1);
            
            // Spline: S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
            
            // Left spline: 
            // S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
            // 1st derivative: S'_i(x) = 3a_i*(x-x_i)^2 + 2b_i*(x-x_i) + c_i
            // 2nd derivative: S''_i(x) = 6a_i*(x-x_i) + 2b_i
            
            // Right spline:
            // S_{i+1}(x) = a_{i+1}*(x-x_{i+1})^3 + b_{i+1}*(x-x_{i+1})^2 + c_{i+1}*(x-x_{i+1}) + d_{i+1}
            // 1st derivative: S'_{i+1}(x) = 3a_{i+1}*(x-x_{i+1})^2 + 2b_{i+1}*(x-x_{i+1}) + c_{i+1}
            // 2nd derivative: S''_{i+1}(x) = 6a_{i+1}*(x-x_{i+1}) + 2b_{i+1}
            
            // At x = x_{i+1} : end point of left spline, start point of right spline
            // Left 1st derivative: 
            // S''_i(x) = 6a_i*(x_{i+1}-x_i) + 2b_i
            // Right 1st derivative: 
            // S''_{i+1}(x) = 6a_{i+1}*(x_{i+1}-x_{i+1}) + 2b_{i+1} = 2b_{i+1}
            
            // Smoothness conditions: 
            // S''_i(x_{i+1}) = S''_{i+1}(x_{i+1}) 
            // 6a_i*(x_{i+1}-x_i) + 2b_i = 2b_{i+1}
            // Let h = x_{i+1}-x_i 
            // 6a_i*h + 2b_i = 2b_{i+1}
            // 6a_i*h + 2b_i - 2b_{i+1} = 0            
            
            double h = x[i+1] - x[i];
            
            matrix[row][col] = 6*h; // 6a_i*h
            matrix[row][col + 1] = 2; // 2b_i

            matrix[row][next + 1] = -2; // - 2b_{i+1} 

            rhs[row] = 0; // 6a_i*h + 2b_i - 2b_{i+1} = 0            
            row++;
        }

        // ================
        // 4. Natural boundary conditions: (Lecture 2, pg177)
        //	"free" condition
        // THIS: Natural cubic splines: Second derivatives are 0 at boundaries 
        // Clamped splines: Specify the first derivatives at the endpoints
        // Periodic splines: Second derivatives are equal at the boundaries
        // Not-a-knot: Third derivatives are equal where they meet
        // ================
        
        // S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
        // 1st derivative: S'_i(x) = 3a_i*(x-x_i)^2 + 2b_i*(x-x_i) + c_i
        // 2nd derivative: S''_i(x) = 6a_i*(x-x_i) + 2b_i
        
        // S''_0(x_0) = 0
        // S''_0(x_0) = 6a_0*(x-x_0) + 2b_0 = 0
        // At x = x0 (boundary point)
        // S''_0(x_0) = 6a_0*(x_0-x_0) + 2b_0 = 0
        // S''_0(x_0) = 2b_0 = 0
        matrix[row][1] = 2; // 2b_0
        rhs[row] = 0; // S''_0(x_0) = 2b_0 = 0
        row++;

        // S_{n-1}''(x_n) = 0
        // S''_{n-1}(x_{n-1}) = 6a_{n-1}*(x_n-x_{n-1}) + 2b_{n-1} = 0
        // Let h = x_n-x_{n-1}
        // 6a_{n-1}*h + 2b_{n-1} = 0
        double h = x[n] - x[n-1];
        int col = 4 * (n - 1);
        
        matrix[row][col] = 6*h; // 6a_{n-1}*h
        matrix[row][col + 1] = 2; // 2b_{n-1}
        rhs[row] = 0;
        row++;

        // ================
        // 5. Solve system
        // ================
        // A * x = b
        // A = matrix (spline equation), x = unknown coefficients, b = RHS vector
        
        // Convert array to matrix A
        RealMatrix A = MatrixUtils.createRealMatrix(matrix);
        
        // Factorises A = (lower triangle)*(upper triangle)
        DecompositionSolver solver = new LUDecomposition(A).getSolver();
        
        // Converts RHS into vector form (b)
        RealVector b = new ArrayRealVector(rhs);
        
        // Solves x = b / A
        RealVector solution = solver.solve(b);

        double[] coeffs = solution.toArray();
        
        // ================
        // 6. Output as CSV
        // ================
        
        // Spline points
        try (FileWriter spline_fw = new FileWriter("output_data/Q3_spline.csv")){
        	
        	// header
        	spline_fw.write("x,y\n");
        	
        	// Loop over splines
        	for(int i = 0; i < n; i++) { // int n = x.length - 1; // 6 splines (intervals)
        		// Loop over points (sample splines every 0.1 units)
        		for(double xi = x[i]; xi <= x[i+1]; xi += 0.1) {
        			// Local coordinate (spline = x - x_i)
        			double dx = xi - x[i];
        			// S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
        			double yi = coeffs[4*i]*dx*dx*dx // a_i*(x-x_i)^3
        					+ coeffs[4*i+1]*dx*dx // b_i*(x-x_i)^2
        					+ coeffs[4*i+2]*dx // c_i*(x-x_i)
        					+ coeffs[4*i+3]; // d_i
        			
        			spline_fw.write(xi + "," + yi + "\n");
        			}
        		}
        	spline_fw.close();
        	System.out.println("Q3_spline.csv created.");
        } catch(IOException e) {
        	e.printStackTrace();
        	}
        
        // Coefficients
        try (FileWriter coef_fw = new FileWriter("output_data/Q3_coefs.csv")){
        	
        	// header
        	coef_fw.write("spline,a,b,c,d\n");
        	
        	for(int i = 0; i < n; i++) {
        		
        		// S_i(x) = a_i*(x-x_i)^3 + b_i*(x-x_i)^2 + c_i*(x-x_i) + d_i
        		coef_fw.write(i + "," + 
        						coeffs[4*i] + "," +
        						coeffs[4*i+1] + "," +
        						coeffs[4*i+2] + "," +
        						coeffs[4*i+3] + "\n");
        	}
        	coef_fw.close();
        	System.out.println("Q3_coefs.csv created.");
        } catch(IOException e) {
        	e.printStackTrace();
        }
        
        
        }
    }











