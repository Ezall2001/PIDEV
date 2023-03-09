package pages.signup;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import dialogs.file_chooser.File_chooser_controller;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.User_service;
import services.User_session_service;
import utils.File_helpers;
import utils.Router;

public class Signup_controller implements Initializable {
    private static User_service user_service = new User_service();
    private static User_session_service user_session_service = new User_session_service();
    private static String profile_avatar_path = "server/profile_avatars/";
    private User user;

    @FXML
    private Label age_error;

    @FXML
    private TextField age_input;

    @FXML
    private TextField avatar_path_input;

    @FXML
    private Label bio_error;

    @FXML
    private TextArea bio_input;

    @FXML
    private Label email_error;

    @FXML
    private TextField email_input;

    @FXML
    private Label first_name_error;

    @FXML
    private TextField first_name_input;

    @FXML
    private Label last_name_error;

    @FXML
    private TextField last_name_input;

    @FXML
    private Label password_error;

    @FXML
    private PasswordField password_input;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = new User();

    }

    @FXML
    void on_import_button_pressed(ActionEvent event) {
        File_chooser_controller controller = new File_chooser_controller();
        controller.hydrate(src_file -> {
            String dest_file_path = profile_avatar_path + src_file.getName();
            File dest_file = new File(dest_file_path);
            File_helpers.copy_file(src_file, dest_file);
            avatar_path_input.setText(src_file.getPath());
            user.set_avatar_path(dest_file_path);
        });
    }

    @FXML
    void on_signup_button_pressed(ActionEvent event) {
        String first_name = first_name_input.getText();
        String last_name = last_name_input.getText();
        String email = email_input.getText();
        String password = password_input.getText();
        String age = age_input.getText();
        String bio = bio_input.getText().trim();

        if (!validate_input()) {
            user.set_first_name(first_name);
            user.set_last_name(last_name);
            user.set_age(Integer.parseInt(age));
            user.set_bio(bio);
            user.set_email(email);
            user.set_password(password);
            user.set_hashed_password();
            user.set_type(User.Type.STUDENT.toString());

            user_service.add(user);
            user_session_service.create_session(user);

            Router.render_user_template("Profile", null);
        }

    }

    public Boolean validate_input() {
        initialize_errors();
        String first_name = first_name_input.getText();
        String last_name = last_name_input.getText();
        String email = email_input.getText();
        String password = password_input.getText();
        String age = age_input.getText();
        String bio = bio_input.getText();
        Boolean is_error = false;

        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty()
                || bio.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vous devez remplir tout les champs vide.", ButtonType.OK);
            alert.showAndWait();
            return true;
        }
        if (!last_name.matches("[a-zA-Z ]+") || (last_name.length() < 2)) {
            last_name_error.setText("**Le nom ne doit contenir que des lettres et sa longueur > 2.");
            is_error = true;
        }
        if (!first_name.matches("[a-zA-Z ]+") || (first_name.length() < 2)) {
            first_name_error.setText("**Le prénom ne doit contenir que des lettres et sa longueur > 2.");
            is_error = true;
        }
        if (bio.length() < 50) {
            bio_error.setText("**La Bio doit avoir au moins 50 caractères .");
            is_error = true;
        }
        if (!age.matches("[0-9]+") || (Integer.parseInt(age) <= 18) || (Integer.parseInt(age) > 100)) {
            age_error.setText("**Age doit être un nombre superieure à 17.");
            is_error = true;
        }
        if ((email.isEmpty()) || (!email.endsWith("@esprit.tn"))) {
            email_error.setText("**Le mail doit appartenir à esprit.");
            is_error = true;
        }

        if (password.length() < 8) {
            password_error.setText("**Mot de passe doit avoir au moins 8 caractéres.");
            is_error = true;
        }

        return is_error;
    }

    public void initialize_errors() {
        bio_error.setText("");
        first_name_error.setText("");
        last_name_error.setText("");
        age_error.setText("");
        email_error.setText("");
        password_error.setText("");

    }

}
