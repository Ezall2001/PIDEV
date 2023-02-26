package pages.profile;

import utils.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main_eya extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin_profile.fxml"));
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("User_profile.fxml"));
        Parent root = loader.load();
        loader.setRoot(root);
        stage.setResizable(false);
        stage.setTitle("My JavaFX Application");
        stage.setScene(new Scene(root, 1440, 810));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
