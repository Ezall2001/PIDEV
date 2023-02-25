package pages.profile;

import java.io.IOException;

import utils.Log;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.User_service;

public class Login_controller {
    User_service user_service;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField email_field;
    @FXML
    private TextField password_field;
    @FXML
    Label error_login;

    public Login_controller() {
        user_service = new User_service();
    }

    //TODO: check type is student 
    //TODO: SESSION CREATED
    //TODO: Login mehtod
    // public void login(ActionEvent event) throws IOException {
    //     if (email_field.getText().isEmpty() || password_field.getText().isEmpty()) {
    //         error_login.setText("** il y'a des champs vide");
    //         return;
    //     }

    //     if (!email_field.getText().endsWith("@esprit.tn")) {
    //         error_login.setText("** le mail doit appartenir à esprit");
    //         return;
    //     }
    //     User user;
    //     String email = email_field.getText();
    //     String password = password_field.getText();
    //     user = user_service.login(email, password);
    //     if (user != null) {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("User_profile.fxml"));
    //         root = loader.load();
    //         User_profile_controller user_profile_controller = loader.getController();
    //         user_profile_controller.display_user(user);
    //         stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    //         scene = new Scene(root);
    //         stage.setScene(scene);
    //         stage.show();
    //     } else {
    //         error_login.setText("*** verifier vos parametre");
    //     }
    // }

}
