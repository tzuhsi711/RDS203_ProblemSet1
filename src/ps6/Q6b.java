package ps6;

import java.io.*;

public class Q6b {
	// Purpose: write number of "predicted" daily cases 
	
    public static void writeOutput(double cases[], double[][] targets, String filename) {
    	
    	try {
    		FileWriter file = new FileWriter(filename);
    	    
    		// header
    		file.write("t_day,case_pred,case_obs\n");
    		
    		int n = Math.min(cases.length, targets.length);
    		
    		for (int i = 0; i < n; i++) {
    			file.write(i + "," + cases[i] + "," + targets[i][1] + "\n");
    		}
    		
    		file.close();
    		System.out.println("Output created: " + filename);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

}
