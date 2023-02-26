package pages.profile;

import entities.Current_user_data;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.Log;

public class test_controller {
    User currentUser = Current_user_data.get_current_user();
    @FXML
    private Button random_button;

    public void random_action(ActionEvent event) {
        if (currentUser == null) {
            Log.console("NULL");
        } else {
            Log.console("ena l current user : " + currentUser);
        }
        if (currentUser.get_type().toString() == "STUDENT") {
            Log.console("ENA STUDENT");
        } else if (currentUser.get_type().toString() == "ADMIN")
            Log.console("ena admin");

    }
}
