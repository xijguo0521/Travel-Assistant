import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class RecommendationTest {


    @Test
    public void testRecommendation1() {
        Recommendation.getRecommendedSpots("testdatabase1", "Xijie");
        assertTrue(Recommendation.getRecommendationList().isEmpty());
    }

    @Test
    public void testRecommendation2() {
        List<String> result = Recommendation.getRecommendedSpots("testdatabase2", "Mousse");
        assertEquals(result.get(0), "Chinatown");
        assertEquals(result.get(1), "The Museum of Modern Art");
        assertEquals(result.get(2), "108 Food Dried Hot Pot");
    }
}
