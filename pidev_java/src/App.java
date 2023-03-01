import java.io.File;
import java.io.FileInputStream;
import java.util.function.Consumer;

import entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pages.forum.Forum_controller;
import pages.profile.Login_controller;
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
        Consumer<Forum_controller> consumer = forumontroller -> {

        };

        Router.render_user_template("Forum", consumer);
        Log.console("00000000" + main_stage);

    }

}
