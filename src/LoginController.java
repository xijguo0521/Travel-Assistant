import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.regex.Pattern;

public class LoginController {
	@FXML
	private Label lblStatus;

	@FXML
	private AnchorPane loginpane;

	@FXML
	private TextField txtUsername;

	@FXML
	private TextField txtPassword;

	private MongoCollection<Document> users;

	public LoginController() {
        users = DBClient.getCollectionByName("users");
	}

	public Boolean isValidInput(String username, String pw) {
		boolean usernameValid = (!username.contains(" ")) && (!username.contains("\t"))
				&& (username.length() != 0);
		boolean passwordValid = Pattern.compile("\\S").matcher(pw).find();
		if (! usernameValid || ! passwordValid) {
			return false;
		}
		return true;
	}

	public void signup() {
 		try {
			String username = txtUsername.getText();
			String pw = txtPassword.getText();

			Document user = DBClient.getUser(users, username);

			if (user != null) {
				// user exists
				lblStatus.setText("Username taken, please choose a new one.");
			}

			// validator
			else if (!isValidInput(username, pw)) {
				lblStatus.setText("Username and/or password is not valid.\nPlease try again");
				return;
			}

			else {
				// create new user document
				user = new Document("username", username)
	                .append("password", pw);

                users.insertOne(user);
                lblStatus.setText("Registration success! Signin to continue.");
			}

			txtUsername.clear();
			txtPassword.clear();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void login() {
		try {
			String username = txtUsername.getText();
			String pw = txtPassword.getText();

			int status = this.authenticate(username, pw);

			switch(status)
			{
				case 0:
					lblStatus.setText("Invalid username/password!");
					break;

				case 1:
					lblStatus.setText("Login success!");
					AppInfo.setCurUsrName(username);
					this.displayApp();
					break;

				case -1:
					lblStatus.setText("User does not exist! Sign up first");
					break;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	public int authenticate(String username, String password) {
		try {
			// fetch remote username and password
			Document user = DBClient.getUser(users, username);

			// handle no user
			if (user == null) {
				return -1;
			}

			// compare
			else if (password.equals(user.get("password"))) {
				return 1;
			}

		}
		catch (Exception e) {
			System.out.println(e.toString());
		}

		return 0;
	}

	private void displayApp() throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setTitle("TravelAssistant");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        loginpane.getScene().getWindow().hide();
        //now.close();
	}

}