import java.io.File;
import java.io.FileInputStream;

import entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pages.session.Session_controller;
import utils.Log;
import utils.Router;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage main_stage) {
        Router.init(main_stage);
        Router.render_user_template("Session", null);

    }

}
