package pages.profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import entities.Current_user_data;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pages.forum.Forum_controller;
import utils.Log;
import utils.Router;
import utils.Service_Fx;

public class test_controller implements Initializable {
    //User currentUser = Current_user_data.get_current_user();

    @FXML
    private Button random_button;
    Service_Fx sf = new Service_Fx();

    public void random_action(ActionEvent event) throws IOException {
        sf.redirect(event, "/pages/forum/Forum.fxml");

        // Consumer<Forum_controller> consumer = forumontroller -> {

        // };

        // Router.render_user_template("Forum", consumer);
        // Log.console("najibaa");
        // Log.console("00000000" + main_stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }
}
