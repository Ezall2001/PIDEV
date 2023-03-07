package pages.login;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.User_service;
import utils.Router;

public class Login_controller {
    private static User_service user_service = new User_service();

    @FXML
    private TextField email_input;

    @FXML
    private Label error_label;

    @FXML
    private PasswordField password_input;

    @FXML
    void on_login_button_pressed(ActionEvent event) {
        if (!validate_input())
            return;

        User user = new User();
        user.set_email(email_input.getText());
        user.set_password(password_input.getText());
        user.set_hashed_password();
        User logged_in_user = user_service.login(user);

        if (logged_in_user == null) {
            error_label.setText("utilisateur introuvable");
            return;
        }

        if (logged_in_user.get_type() == User.Type.STUDENT)
            Router.render_user_template("Profile", null);
        else
            Router.render_admin_template("Dashboard", null);

    }

    @FXML
    void on_sign_up_button_pressed(ActionEvent event) {
        Router.render_user_template("Signup", null);
    }

    private Boolean validate_input() {
        if (email_input.getText().isEmpty() || password_input.getText().isEmpty()) {
            error_label.setText("** il y'a des champs vides");
            return false;
        }

        if (!email_input.getText().endsWith("@esprit.tn")) {
            error_label.setText("** le mail doit appartenir à esprit");
            return false;
        }

        return true;
    }

}
