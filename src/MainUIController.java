import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.stage.Stage;
import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class MainUIController {

    private SpotsCollection sc = new SpotsCollection();
    private List<String> listCompare = new ArrayList<String>();
    private String spotInfo = "";
    private String spotLatLng = "";
    private List<String> spotList;
    private List<String> history;

    @FXML
    private ChoiceBox TimeorDistance = new ChoiceBox();

    @FXML
    private Pane planpane = new Pane();

    @FXML
    private AnchorPane mainpane;

    @FXML
    private ChoiceBox TravelMode = new ChoiceBox();

    @FXML
    private Label text = new Label();

    @FXML
    private Label welcome = new Label();

    @FXML
    private Button save = new Button();

    @FXML
    private Button add_location = new Button();

    @FXML
    private Button delete = new Button();
    @FXML
    private Button buttonGenerate = new Button();

    @FXML
    private Pane algorithm = new Pane();

    @FXML
    private WebView htmlGmap;

    @FXML
    private Button recommendButton = new Button();


    @FXML
    private ListView<String> list = new ListView<>();

    @FXML
    private ListView<String> plan_history = new ListView<>();

    @FXML
    private ListView<String> recommendation = new ListView<>();

    @FXML
    protected void initialize(){
        WebEngine webEngine = htmlGmap.getEngine();
        URL urlGoogleMaps = getClass().getResource("search.html");
        webEngine.load(urlGoogleMaps.toExternalForm());
        welcome.setText("Welcome, " + AppInfo.getCurUsrName());
        planpane.setVisible(false);
        //progress.setVisible(false);
        save.setVisible(false);
        history = DBClient.getDocuments(DBClient.getCollectionByName("trips"),
                AppInfo.getCurUsrName());
        plan_history.getItems().add("* plan history *");
        for(String each : history){
            plan_history.getItems().add(each);
        }

        TimeorDistance.setItems(FXCollections.observableArrayList(
                "Time", "Distance"
        ));
        TimeorDistance.getSelectionModel().selectFirst();
        TimeorDistance.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        if (newValue.equals(0)){
                            sc.setDistance(false);
                        }
                        else{
                            sc.setDistance(true);
                        }

                    }
                }
        );
        TravelMode.setItems(FXCollections.observableArrayList(
                "Walking", "Driving", "Bicycling", "Transit"
        ));
        TravelMode.getSelectionModel().selectFirst();
        TravelMode.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        switch (newValue.intValue()){
                            case 0:
                                sc.setMode("walking");
                                break;
                            case 1:
                                sc.setMode("driving");
                                break;
                            case 2:
                                sc.setMode("bicycling");
                                break;
                            case 3:
                                sc.setMode("transit");
                                break;
                            default:
                                System.out.println("ChoiceBox Error!");
                        }
                    }
                }
        );

        recommendButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                recommendation.getItems().clear();
                List<String> recommendations = Recommendation.getRecommendedSpots("trips",
                        AppInfo.getCurUsrName());
                if (recommendations.isEmpty()){
                    System.out.println("No spots to recommend due to lack of users' data");
                    recommendation.getItems().add("No spots to recommend due to lack of users' data");
                }
                else {
                    System.out.println("Recommended spots: " + recommendations);
                    recommendation.getItems().add("* Recommended spots *");
                    recommendation.getItems().addAll(recommendations);
                }
            }
        }));
    }

    @FXML
    protected void handleButton1Action(ActionEvent event){
    	spotInfo = "";
        WebEngine webEngine = htmlGmap.getEngine();
    	spotInfo = (String) webEngine.executeScript("document.getElementById('status').innerHTML");
        spotLatLng = (String) webEngine.executeScript("document.getElementById('latlng').innerHTML");
        System.out.println("------------------" + spotLatLng);


        if ((!spotInfo.equals("")) && (!spotLatLng.equals(""))) {
            spotLatLng = spotLatLng.replace("(","");
            spotLatLng = spotLatLng.replace(")","");
            Double lat = Double.parseDouble(spotLatLng.split(",",0)[0]);
            Double lng = Double.parseDouble(spotLatLng.split(",",0)[1]);
            if( lat >= 40.495330 && lat <= 40.910691 && lng >= -74.265051 && lng <= -73.744805){
                if (!listCompare.contains(spotInfo)){
                    sc.addSpot(spotInfo,spotLatLng);
                    listCompare.add(spotInfo);
                    list.getItems().add(spotInfo);
                    System.out.println(listCompare);
                    text.setText("New location added!");


                }
                else{
                    System.out.println("Repeated Location!");
                    text.setText("Repeated Location!");
                }

            }else {
                text.setText("The spot is outside of New York!");
                System.out.println("The spot is outside of New York!");
            }

        }
        else {
            System.out.println("No specific spot information found! \nPlease search again!");
            text.setText("No specific spot information found! \nPlease search again!");
        }
        webEngine.executeScript("document.getElementById('status').innerHTML = '';");
    }

    @FXML
    protected void handleDeleteAction(ActionEvent event){
        spotInfo = "";
        if (listCompare.isEmpty()){
            System.out.println("Spot List is Already Empty!");
            text.setText("Spot List is Already Empty!");
        }
        else{
            final int selectedIdx = list.getSelectionModel().getSelectedIndex();
            if (selectedIdx != -1){
                sc.deleteSpot(selectedIdx);
                listCompare.remove(selectedIdx);
                //System.out.println(listCompare);
                list.getItems().remove(selectedIdx);
                text.setText("Delete the spot!");
            }

        }
    }

    @FXML
    protected void handleButtonGenerateTripPlan(ActionEvent event){
        //progress.setVisible(true);
        list.getItems().clear();
        listCompare.clear();


        try{
            sc.setNumSpots(sc.getSpots().size());
            sc.saveDistancesToMatrix();

            spotList = ShortestRoute.findNearestNeighbor(sc);

            list.getItems().addAll(spotList);
            text.setText("Success!");
            //progress.setVisible(false);
            save.setVisible(true);
            sc.reset();
            add_location.setVisible(false);
            delete.setVisible(false);
            algorithm.setVisible(false);
            buttonGenerate.setVisible(false);


        }catch (Exception e){

            System.out.println("Can not generate the route!");
            text.setText("Can not generate the route!");
        }

    }

    @FXML
    protected void handleButtonStart(ActionEvent event){
        planpane.setVisible(true);
        add_location.setVisible(true);
        delete.setVisible(true);
        algorithm.setVisible(true);
        buttonGenerate.setVisible(true);
        save.setVisible(false);
        sc.reset();
        listCompare.clear();
        spotInfo = "";
        text.setText("Please search spots in map.");
        list.getItems().clear();
    }

    @FXML
    protected void handleButtonSave(ActionEvent e){
        MongoCollection<Document> trips = DBClient.getCollectionByName("trips");
        String username = AppInfo.getCurUsrName();
        System.out.println(username);
        try {
            Document trip = new Document("username", username).append("trip", spotList);
            trips.insertOne(trip);
            text.setText("Saved to database!");
            save.setVisible(false);
            plan_history.getItems().clear();
            history = DBClient.getDocuments(DBClient.getCollectionByName("trips"),
                    AppInfo.getCurUsrName());
            for(String each : history){
                plan_history.getItems().add(each);
            }

        }
        catch (Exception ex) {
			System.out.println(ex.toString());
			text.setText("Can not save the plan!");
		}
    }

    @FXML
    protected void handleButtonLogOut(ActionEvent event) throws IOException {
        //progress.setVisible(true);
        planpane.setVisible(false);
        sc.reset();
        listCompare.clear();
        spotInfo = "";
        text.setText("");
        list.getItems().clear();
        Stage primaryStage = new Stage();
        LoginController lc = new LoginController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.setController(lc);
        Parent root = loader.load();

        primaryStage.setTitle("TravelAssistant");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        mainpane.getScene().getWindow().hide();
    }


}
