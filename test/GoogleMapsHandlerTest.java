import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

public class GoogleMapsHandlerTest {

	@Test
	public void testDistance() throws IOException {
		double test1 = GoogleMapsHandler.Distance(true, "Metropolian Museum","Time Squre", "walking");
		double test2 = GoogleMapsHandler.Distance(false, "MOMA","Columbia University", "walking");
		
		double test3 = GoogleMapsHandler.Distance(true, "Metropolian Museum","Time Squre", "driving");
		double test4 = GoogleMapsHandler.Distance(false, "MOMA","Columbia University", "driving");
		
		double test5 = GoogleMapsHandler.Distance(true, "Metropolian Museum","Time Squre", "bicycling");
		double test6 = GoogleMapsHandler.Distance(false, "MOMA","Columbia University", "bicycling");
		
		double test7 = GoogleMapsHandler.Distance(true, "Metropolian Museum","Time Squre", "transit");
		double test8 = GoogleMapsHandler.Distance(false, "MOMA","Columbia University", "transit");

		assertNotEquals(test1,0.0);
		assertNotEquals(test2,0.0);
		assertNotEquals(test3,0.0);
		assertNotEquals(test4,0.0);
		assertNotEquals(test5,0.0);
		assertNotEquals(test6,0.0);
		assertNotEquals(test7,0.0);
		assertNotEquals(test8,0.0);

	}
}
