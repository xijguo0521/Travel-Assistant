import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Haoran Geng, Xijie Guo
 * 
 * Each time finding the min path along the nodes
 * store the nodes into the stack
 *
 */
public class ShortestRoute {

	public static List<String> findNearestNeighbor(SpotsCollection sc) {
		if (!sc.isDistanceMatrixValid())
			return null;

		Double[][] distances = sc.getDistanceMatrix();
		List<Spot> spots = sc.getSpots();
		int numberOfNodes = sc.getNumSpots();

		Stack<String> stack = new Stack<String>();
		List<String> result = new ArrayList<String>();
		int[] visited = new int[numberOfNodes + 1];
		visited[0]= 1;
		int element = 0, dst = 0, i = 0;
		double min = Double.MAX_VALUE;
		boolean minFlag = false;

		stack.push(spots.get(0).getName());
		result.add(spots.get(0).getName());

		while(!stack.isEmpty()) {

			String name = stack.peek();
			for (Spot spot: spots) {
				if (spot.getName().equals(name))
					element = spots.indexOf(spot);
			}

			i = 0;
			min = Double.MAX_VALUE;
			while(i < numberOfNodes) {
				if(distances[element][i] > 1 && visited[i] == 0 && min > distances[element][i]) {
					min  = distances[element][i];
					dst = i;
					minFlag = true;
				}
				i++;
			}
			if(minFlag) {
				visited[dst] = 1;
				stack.push(spots.get(dst).getName());
				result.add(spots.get(dst).getName());
				minFlag = false;
				continue;
			}
			stack.pop();
		}

		return result;
	}

}
