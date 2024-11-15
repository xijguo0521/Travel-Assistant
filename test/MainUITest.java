import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.testfx.api.FxAssert.*;
import org.junit.After;
import org.junit.Test;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.loadui.testfx.controls.ListViews;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.ListViewAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.LabeledMatchers.hasText;



public class MainUITest extends ApplicationTest {



    @Override
    public void start(Stage primaryStage) throws Exception{
        LoginController lc = new LoginController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.setController(lc);
        Parent root = loader.load();

        primaryStage.setTitle("TravelAssistant");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        FxToolkit.cleanupStages();
        release(new KeyCode[]{});
        release(new MouseButton[]{});

    }

    @Test
    public void loginSuccess() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,3);
        clickOn("#login");
        verifyThat("#welcome",hasText("Welcome, 111"));
    }

    @Test
    public void loginInvalid() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,4);
        clickOn("#login");
        verifyThat("#lblStatus",hasText("Invalid username/password!"));

    }

    @Test
    public void loginUserNotExist() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT8,7);
        clickOn("#txtPassword").type(KeyCode.DIGIT8,4);
        clickOn("#login");
        verifyThat("#lblStatus",hasText("User does not exist! Sign up first"));

    }

    @Test
    public void signupEmpty() throws InterruptedException{
        clickOn("#signup");
        verifyThat("#lblStatus",hasText("Username and/or password is not valid.\nThey should not be blank. Please try again"));

    }

    @Test
    public void signupUsernameTaken() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT8,4);
        clickOn("#signup");
        verifyThat("#lblStatus",hasText("Username taken, please choose a new one."));

    }

    @Test
    public void signupSuccess() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT7,8);
        clickOn("#txtPassword").type(KeyCode.DIGIT8,4);
        clickOn("#signup");
        verifyThat("#lblStatus",hasText("Registration success! Signin to continue."));

    }

    @Test
    public void logOut() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,3);
        clickOn("#login");
        Thread.sleep(2000);
        clickOn("#logout");
        verifyThat("#lblStatus",hasText("Log In or Sign Up to Continue"));
    }

    @Test
    public void StartPlan() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,3);
        clickOn("#login");
        Thread.sleep(2000);
        clickOn("#start");
        verifyThat("#text",hasText("Please search spots in map."));
    }

    @Test
    public void AddSpot() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,3);
        clickOn("#login");
        Thread.sleep(2000);
        clickOn("#start");
        clickOn("#add_location");
        verifyThat("#text",hasText("No specific spot information found! \nPlease search again!"));
        Thread.sleep(10000);  //search one spot by hand
        clickOn("#add_location");
        verifyThat("#text",hasText("New location added!"));
        Thread.sleep(10000); //search same spot
        clickOn("#add_location");
        verifyThat("#text",hasText("Repeated Location!"));
    }

    @Test
    public void DeleteSpot() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,3);
        clickOn("#login");
        Thread.sleep(2000);
        clickOn("#start");
        clickOn("#delete");
        verifyThat("#text",hasText("Spot List is Already Empty!"));
        Thread.sleep(10000);  //search one spot by hand
        clickOn("#add_location");
        clickOn("#delete");
        verifyThat("#text",hasText("Delete the last spot!"));
    }

    @Test
    public void generatePlanAndSave() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,3);
        clickOn("#login");
        clickOn("#start");
        Thread.sleep(30000); //add 3 valid spots
        clickOn("#TravelMode");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#TravelMode");
        type(KeyCode.DOWN,2);
        type(KeyCode.ENTER);
        clickOn("#TravelMode");
        type(KeyCode.DOWN,3);
        type(KeyCode.ENTER);
        clickOn("#TimeorDistance");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#buttonGenerate");
        verifyThat("#text",hasText("Success!"));
        clickOn("#save");
        verifyThat("#text",hasText("Saved to database!"));

    }

    @Test
    public void generatePlanFailure() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,3);
        clickOn("#login");
        Thread.sleep(2000);
        clickOn("#start");
        clickOn("#buttonGenerate");
        verifyThat("#text",hasText("Can not generate the route!"));
    }

    @Test
    public void RecommendationSuccess() throws InterruptedException{
        clickOn("#txtUsername").type(KeyCode.DIGIT1,3);
        clickOn("#txtPassword").type(KeyCode.DIGIT1,3);
        clickOn("#login");
        Thread.sleep(2000);
        clickOn("#recommendButton");
        ListViews.containsRow("* Recommended spots *");
    }













}




