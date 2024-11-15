import org.junit.Before;
import org.junit.Test;
import java.util.*;
import org.junit.Assert;

import static org.junit.Assert.*;

public class LoginControllerTest {

    private LoginController controller = new LoginController();

    @Test
    public void testValidator() {    
        String u = "a b";
        String p = "1 3 5";
        assertTrue(!controller.isValidInput(u, p));

        String username = "ab";   
        String password = "123";
        assertTrue(controller.isValidInput(username, password));
    }
}
