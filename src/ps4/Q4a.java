package ps4;

import java.io.*; // read files (FileReader, BufferedReader)
import java.util.*; // ArrayList

// View the file first

public class Q4a {
	
	public static void main(String[] args) throws Exception {

		// Create temp lists for storing rows of x and y
		List<double[]> X_list = new ArrayList<>(); // predictors (data type)
		List<Double> y_list = new ArrayList<>();   // outcome (object)

		// try-with-resources (auto closes file)
		// new: create a new object in memory
		try (BufferedReader file = new BufferedReader(new FileReader("input_data/data_NHANES.csv"))) {

			// Read the header
			String line = file.readLine();

			// Loop through each line
			while ((line = file.readLine()) != null) {

				// Split lines by comma
				String[] tokens = line.split(",");

				// Convert data type to double
				double sex = Double.parseDouble(tokens[1]);
				double age = Double.parseDouble(tokens[2]);
				double bmi = Double.parseDouble(tokens[3]);
				double waist = Double.parseDouble(tokens[4]);
				double meanBP_sys = Double.parseDouble(tokens[5]);

				// Add predictors (1 = intercept)
				X_list.add(new double[] {1, age, sex, bmi, waist});

				// Add outcome
				y_list.add(meanBP_sys);
			}
		}

		// Print number of rows read
		System.out.println("Rows read: " + X_list.size());
	}
}
