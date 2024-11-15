import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class SpotsCollectionTest {

    private SpotsCollection spots = new SpotsCollection();
    private List<Spot> spotList = spots.getSpots();

    @Before
    public void setUp() throws Exception {
        spots.reset();
        spots.addSpot("Columbia University", "40.8075355, -73.9625727");
        spots.addSpot("MOMA", "40.7614327, -73.9776216");
        spots.addSpot("Central Park", "40.7828647, -73.9653551");
        spots.addSpot("Empire State Building", "40.7485452, -73.9857634");
        spots.addSpot("SOHO", "40.7233, -74.0029882");
    }

    @Test
    public void testSpotAdd() {

        assertTrue(spotList.get(0).getName().equals("Columbia University"));
        assertTrue(spotList.get(1).getName().equals("MOMA"));
        assertTrue(spotList.get(2).getName().equals("Central Park"));
        assertTrue(spotList.get(3).getName().equals("Empire State Building"));
        assertTrue(spotList.get(4).getName().equals("SOHO"));
        assertEquals(5, spotList.size());
    }

    @Test
    public void testSpotDelete() {
        spots.deleteSpot(spots.getNumSpots()-1);
        assertTrue(spotList.get(spotList.size()-1).getName().equals("Empire State Building"));
        assertEquals(4, spotList.size());
    }

    @Test
    public void testSaveMatrixDistance () throws Exception {
        Double[][] matrix =  {
                {0.0, 76.0, 44.0, 93.0, 132.0},
                {78.0, 0.0, 38.0, 23.0, 62.0},
                {44.0, 37.0, 0.0, 55.0, 94.0},
                {95.0, 23.0, 56.0, 0.0, 42.0},
                {135.0, 63.0, 97.0, 43.0, 0.0}
        };
        spots.saveDistancesToMatrix();
        Double[][] testMatrix = spots.getDistanceMatrix();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(matrix[i][j], testMatrix[i][j], 5);
            }
        }
    }

}
