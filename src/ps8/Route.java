package ps8;

import java.util.*;

public class Route {
	
	public int[] order;
	
	// ===========================
	// 0. Initialise random route
	// ===========================
	public Route(int N) {
		order = new int[N+1];
		
		ArrayList<Integer> cities = new ArrayList<>();
		for(int i = 1; i < N; i++) {
			cities.add(i);
		}
		
		Collections.shuffle(cities);
		order[0] = 0;
		order[N] = 0;
		
		for(int i = 1; i < N; i++) {
			order[i] = cities.get(i - 1);
		}
	}
	
	// ===========================
	// 1. Copy constructor to prevent overwite
	// ===========================
	public Route copy() {
		Route r = new Route(order.length - 1);
		r.order = this.order.clone();
		return r;
		}
	
	// ===========================
	// 2. Compute total distance
	// ===========================
	public static double computeDistance(int[] order, double[] x, double[] y) {
		
		double dist = 0.0;
		
		for(int i = 0; i < order.length - 1; i++) {
			int a = order[i];
			int b = order[i+1];
			
			double dx = x[a] - x[b];
			double dy = y[a] - y[b];
			
			dist += Math.sqrt(dx * dx + dy * dy);
		}
		
		return dist;
	}
	
	
	

}
