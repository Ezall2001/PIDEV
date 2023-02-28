import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
import utils.Shared_model_nour;

public class test extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage main_Stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("pages/test/test_home_course.fxml"));
        Scene scene = new Scene(root);
        main_Stage.setScene(scene);
        main_Stage.show();
        //
        // Router.init(main_Stage);
        // Router.render_user_template("Test", null);

    }
}
