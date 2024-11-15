import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    //MyBrowser myBrowser;
    private WebView htmlGmap;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("TravelAssistant");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        */

        LoginController lc = new LoginController(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.setController(lc);
        Parent root = loader.load();

        primaryStage.setTitle("TravelAssistant");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

