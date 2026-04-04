package ps8;

import java.io.*;
import java.util.*;


// Purpose: read file

public class ReadCSV {
	
	public double[] x;
	public double[] y;
	public int N;
	
	public ReadCSV(String filename) throws IOException{
		ArrayList<Double> xList = new ArrayList<>();
		ArrayList<Double> yList = new ArrayList<>();
		
		BufferedReader file = new BufferedReader(new FileReader(filename));
		String line;
		
		// skip header
		file.readLine();
		
		while((line = file.readLine()) != null) {
			String[] tokens = line.split(",");
			xList.add(Double.parseDouble(tokens[0]));
			yList.add(Double.parseDouble(tokens[1]));
		}
		file.close();
		
		N = xList.size();
		x = new double[N];
		y = new double[N];
		
		for (int i = 0; i < N; i++) {
			x[i] = xList.get(i);
			y[i] = yList.get(i);
		}
	}

}
