import java.util.List;
import java.util.ArrayList;


/**
 * @author Haoran Geng, Xijie Guo
 *This class contains a collection of spots
 *It contains a list of spots and distance between each spots
 *
 *The list of each spots will store in a stack
 *
 *The distance between each spots will store in a 2D array
 *EXAMPLE:
 *  A,B,C,D
 *A:0,1,2,3
 *B:1,0,4,6
 *C:2,4,0,7
 *D:3,6,7,0
 *Each row represent different spots. The number in the row represent the distance between 
 *the spots and another spots.
 *In the example graph: the disterence between each spots are:
 *A-B:1,A-C:2,A-D:3,B-C:4,B-D:6,C-D:7
 */
public class SpotsCollection {
	private List<Spot> spots = new ArrayList<Spot>();
	private int numSpots;
	private Double[][] distanceMatrix;
	private boolean distance;
	private String Mode;

	public SpotsCollection() {
		reset();
	}

	public List<Spot> getSpots() {
		return spots;
	}

	public void setDistance(boolean distance) {
		this.distance = distance;
	}

	public void setMode(String mode){
		this.Mode = mode;
	}

	public int getNumSpots() {
		return numSpots;
	}

	public void setNumSpots(int numSpots){
		this.numSpots = numSpots;
	}

	public Double[][] getDistanceMatrix() {
		return distanceMatrix;
	}

	public void addSpot(String name, String latlng) {
		Spot newSpot = new Spot();
		newSpot.setName(name);
		newSpot.setLatlng(latlng);
		spots.add(newSpot);
		numSpots++;
    }

	/**
	 * Delete the last spot
	 */
	public void deleteSpot(int selectedIdx) {
        spots.remove(selectedIdx);
		numSpots--;
    }

    public void setDistanceMatrix(Double[][] distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
		numSpots = distanceMatrix.length;
	}

	public void saveDistancesToMatrix() throws Exception {
		distanceMatrix = new Double[numSpots][numSpots];
		for (int i = 0; i < numSpots; i++) {
			for (int j = 0;j < numSpots; j++) {
				if(i == j)
					distanceMatrix[i][j] = 0.0;
				else
					distanceMatrix[i][j]= GoogleMapsHandler.Distance(this.distance,spots.get(i).getLatlng(),spots.get(j).getLatlng(),this.Mode);
			}
		}
	}

	public boolean isDistanceMatrixValid() {
		for (int i = 0; i < numSpots; i++) {
			for (int j = 0; j < numSpots; j++) {
				if (distanceMatrix[i][j] == null)
					return false;
			}
		}
		return true;
	}

	public void reset() {
		spots.clear();
		numSpots = 0;
		distance = false;
		Mode = "walking";
	}
}
