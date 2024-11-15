import org.junit.Test;
import java.util.*; 

import static org.junit.Assert.*;

/**
 * Junit test for back end
 * @Author: Xijie Guo
 */
public class ShortestRouteTest {

    private SpotsCollection spots = new SpotsCollection();

    /**
     * Test if the algorithm can find the shortest path
     */
    @Test
    public void testShortestRoute() {
        spots.reset();
        spots.addSpot("Columbia University", "40.8075355, -73.9625727");
        spots.addSpot("MOMA", "40.7614327, -73.9776216");
        spots.addSpot("Central Park", "40.7828647, -73.9653551");
        spots.addSpot("Empire State Building", "40.7485452, -73.9857634");
        spots.addSpot("SOHO", "40.72330099, -74.00298829");

        Double[][] distanceMatrix = {
                {0.0, 10.0, 8.0, 7.0, 2.0},
                {10.0, 0.0, 9.0, 11.0, 19.0},
                {8.0, 9.0, 0.0, 4.0, 18.0},
                {7.0, 11.0, 4.0, 0.0, 27.0},
                {2.0, 19.0, 18.0, 27.0, 0.0}
        };

        spots.setDistanceMatrix(distanceMatrix);

        List<String> res = ShortestRoute.findNearestNeighbor(spots);
        assertEquals(res.get(0), "Columbia University");
        assertEquals(res.get(1), "SOHO");
        assertEquals(res.get(2), "Central Park");
        assertEquals(res.get(3), "Empire State Building");
        assertEquals(res.get(4), "MOMA");
    }

    @Test
    public void testDistanceInvalid() {
        spots.setNumSpots(3);
        Double[][] matrix = {
                {0.0, null, 3.4},
                {1.1, 0.0, 5.0},
                {2.3, 8.9, 0.0}
        };
        spots.setDistanceMatrix(matrix);
        List<String> res = ShortestRoute.findNearestNeighbor(spots);
        assertTrue(res == null);
    }
}
