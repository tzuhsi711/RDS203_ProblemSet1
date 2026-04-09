package ps8;

import java.io.*;

import util.MersenneTwisterFast;

public class Main {
    
    public static void main(String[] args) throws Exception {
    	
    	MersenneTwisterFast rand = new MersenneTwisterFast(207);
        
        ReadCSV data = new ReadCSV("input_data/data_TSP.csv");
        Route route = new Route(data.N);
        
        // Initial distance
        double initialDist = Route.computeDistance(route.order, data.x, data.y);
        System.out.println("Initial distance: " + initialDist);
        
        // Find best distance (shortest)
        Route best = SimulatedAnnealing.optimise(route, data.x, data.y, rand);
        double finalDist = Route.computeDistance(best.order, data.x, data.y);
        System.out.println("Final distance: " + finalDist);
        
        // Identify route
        System.out.println("Route: ");
        for(int i : best.order) {
            System.out.print(i + "->" );
        }
        System.out.println();
        
        // Save route info
        PrintWriter writer = new PrintWriter("output_data/Q8.csv");
        writer.println("x,y,City");
        
        for (int i : best.order) {
        	writer.println(data.x[i] + "," + data.y[i] + "," + i);
        }
        
        writer.close();
        
        System.out.println("Q8.csv created.");
    }
}
