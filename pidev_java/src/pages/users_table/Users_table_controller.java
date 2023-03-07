package pages.users_table;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import services.User_service;

public class Users_table_controller implements Initializable {

    @FXML
    private VBox table_wrapper;

    static private User_service user_service = new User_service();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
