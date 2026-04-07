package ps6;

import java.io.*;

public class Q6b {
	// Purpose: write number of "predicted" daily cases 
	
    public static void writeOutput(double cases[], double[][] targets, String filename) {
    	
    	try {
    		FileWriter file = new FileWriter(filename);
    	    
    		// header
    		file.write("t_day,case_pred,case_obs\n");
    		
    		for (int i = 0; i < targets.length; i++) {
    			
    			int t = (int) targets[i][0];
    			
    			// safety check
    			if (t >= 0 && t < cases.length) {
    				file.write(t + "," + cases[t] + "," + targets[i][1] + "\n");
    			}
    		}
    		
    		file.close();
    		System.out.println("Q6b.csv created.");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
