package ps1;

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// ==============
		// 0. Input dataset
		// ==============
		// Effect size
		double effectSize = 1.0;

		// Age groups
		String[] ageGroups = { "35-39", "40-44", "45-49", "50-54", "55-59", "60-64", "65-69", "70-74", "75-79",
				"80-100" };

		// Cohort size
		int[] cohortSize = { 4567000, 4214000, 3690000, 3092000, 2534000, 1979000, 1493000, 1112000, 791000, 1010000 };

		// Mean BMI
		double[] meanBMI = { 29.43, 29.90, 30.28, 30.70, 30.30, 29.79, 29.31, 28.82, 27.62, 26.21 };

		// BMI SD
		double[] sdBMI = { 5.73, 5.51, 5.27, 5.47, 4.91, 5.19, 4.90, 5.04, 5.17, 5.52 };

		// BMI RR CRC
		double[] rrCRC = { 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03 };

		// CVA
		double[] rrCVA = { 1.14, 1.14, 1.10, 1.10, 1.10, 1.08, 1.08, 1.05, 1.05, 1.03 };

		// Baseline CRC incidence
		double[] incCRC = { 0.00002, 0.00004, 0.00008, 0.00013, 0.00018, 0.00025, 0.00032, 0.00042, 0.00055, 0.00116 };

		// Baseline CVA incidence
		double[] incCVA = { 0.00073, 0.00084, 0.00087, 0.00107, 0.00125, 0.00149, 0.00164, 0.00236, 0.00378, 0.01073 };
		
		
		// ==============
		// 1. Compute averted cases
		// ==============
		int n = ageGroups.length;
		double totalCRC = 0.0;
		double totalCVA = 0.0;
		
		double[] crcAverted = new double[n];
		double[] cvaAverted = new double[n];
		
		for (int i = 0; i < ageGroups.length; i++) {

			// CRC
			double casesCRC = Q1e.calcCasesAverted(cohortSize[i], incCRC[i],
					meanBMI[i], sdBMI[i], rrCRC[i], effectSize);

			// CVA
			double casesCVA = Q1e.calcCasesAverted(cohortSize[i], incCVA[i], 
					meanBMI[i], sdBMI[i], rrCVA[i], effectSize);
			
			crcAverted[i] = casesCRC;
			cvaAverted[i] = casesCVA;

			totalCRC += casesCRC;
			totalCVA += casesCVA;
		}
		
		// ==============
		// 2. Output as CSV
		// ==============
		
		// a) Input dataset
		PrintWriter inputWriter = new PrintWriter(new File("output_data/Q1_input.csv"));
		// header
		inputWriter.println("AgeGroup,CohortSize,CRC_base,CVA_base");
		
		for(int i = 0; i < n; i++) {
			
			// convert baseline incidence to cases
			double baselineCRC = cohortSize[i] * incCRC[i];
			double baselineCVA = cohortSize[i] * incCVA[i];
			
			inputWriter.println(
					ageGroups[i] + "," +
					cohortSize[i] + "," +
					baselineCRC + "," +
					baselineCVA);
		}
		inputWriter.close();
		
		
		// b) Output dataset
		PrintWriter outputWriter = new PrintWriter(new File("output_data/Q1_output.csv"));
		// header
		outputWriter.println("AgeGroup,CRC_averted,CVA_averted");
		
		for(int i = 0; i < n; i++) {
			outputWriter.println(
					ageGroups[i] + "," +
					crcAverted[i] + "," +
					cvaAverted[i]);
		}
		outputWriter.println("Total," + totalCRC + "," + totalCVA);
		outputWriter.close();
		
		System.out.println("Q1_input.csv and Q1_output.csv created.");
		
		
	}
}










