package ps8;

import ps7.MersenneTwisterFast;

public class SimulatedAnnealing {
	
	// Return the best Route found
	public static Route optimise(Route route, double[] x, double[] y) {
		
		// Random number generator
		MersenneTwisterFast rand = new MersenneTwisterFast();
		
		// ==================
		// 0. Parameters
		// ==================
		double temp = 1000;
		double cooling = 0.99985; // exponential decay
		
		// ==================
		// 1. Compute initial distance (total distance of current route)
		// ==================
		double currentDist = Route.computeDistance(route.order, x, y);
		
		Route bestRoute = route.copy();
		double bestDist = currentDist;
		
		// ==================
		// 2. Find best distance
		// ==================
		for(int iter = 0; iter < 100000; iter++) {
			
			Route newRoute = route.copy();
			
			// Pick two random indices
			int i1 = 1 + rand.nextInt(route.order.length - 2);
			int i2 = 1 + rand.nextInt(route.order.length - 2);
			
			// Swap cities
//			int tempCity = newRoute.order[i1]; // store original value at position i1
//			newRoute.order[i1] = newRoute.order[i2]; // move value from position i2 to i1
//			newRoute.order[i2] = tempCity; // move original value from i1 (stored in tempCity) to i2
			
			// Prevent crossings 
			// Ensure correct index order (i1 -> i2, i1 < i2)
			if (i1 > i2) { // backward: i1 index is larger than i2 index
			    int tempIndex = i1;
			    i1 = i2; // now i1 is smaller
			    i2 = tempIndex; // now i2 is larger
			}

			// Reverse the segment between i1 and i2
			int start = i1;
			int end = i2;

			while (start < end) { // correct order i1 -> i2
			    int tempCity = newRoute.order[start];
			    
			    // swap repeatedly to reverse segment
			    // .order() stores a sequence of cities in the route
			    newRoute.order[start] = newRoute.order[end]; // copy values from end to start
			    newRoute.order[end] = tempCity; // values in end now become start
			    
			    // move inward, repeat swapping, reversal
			    start++; // move right
			    end--; // move left
			}
			
			// New distance for new route
			double newDist = Route.computeDistance(newRoute.order, x, y);
			double delta = newDist - currentDist;
			
			// ==================
			// 3. Accept the swap if the distance is shorter
			// ==================
			if(delta < 0 || rand.nextDouble() < Math.exp(-delta / temp)) {
				route = newRoute;
				currentDist = newDist;
				
				if(currentDist < bestDist) {
					bestDist = currentDist;
					bestRoute = route.copy(); // newRoute
				}
			}
			
			// ==================
			// 4. Cooling schedule
			// ==================
			temp *= cooling;
		}
		System.out.println("Best distance found: " + bestDist);
		return bestRoute;
	}

}
