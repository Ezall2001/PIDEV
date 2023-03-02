package pages.signup;

import java.io.File;

import javax.swing.text.StyledEditorKit.BoldAction;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pages.profile.Profile_controller;
import services.User_service;
import utils.Router;

public class Signup_controller {
    Stage stage;
    private static User_service user_service = new User_service();
    User user;
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

    @FXML
    private Button sign_up_button;

    @FXML
    private Button upload_on_button_pressed;

    private String first_name = first_name_input.getText();
    private String last_name = last_name_input.getText();
    private String email = email_input.getText();
    private String password = password_input.getText();
    private String age = age_input.getText();
    private String avatar_path = avatar_path_input.getText();
    private String bio = bio_input.getText().trim();

    @FXML
    void on_import_button_pressed(ActionEvent event) {
        FileChooser file_chooser = new FileChooser();
        file_chooser.setTitle("Choisi une image");

        file_chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selected_image = file_chooser.showOpenDialog(null);

        if (selected_image != null) {
            String path = selected_image.getAbsolutePath();
            avatar_path_input.setText(path);
        }
    }

    @FXML
    void on_signup_button_pressed(ActionEvent event) {
        if (check_input()) {
            User new_user = new User(
                    first_name,
                    last_name,
                    Integer.parseInt(age),
                    bio,
                    email,
                    password,
                    avatar_path);

            Router.render_user_template("Profile", (Profile_controller controller) -> {
                controller.hydrate(user_service.add(new_user));
            });
        }

    }

    public boolean check_input() {
        initialize_errors();
        int error = 0;
        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty()
                || avatar_path.isEmpty() || bio.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vous devez remplir tout les champs vide.", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                error = 1;
            }
        }
        if (!last_name.matches("[a-zA-Z]+") || (last_name.length() < 2)) {
            last_name_error.setText("**Le nom ne doit contenir que des lettres et sa longueur > 2.");
            error = 1;

        }
        if (!first_name.matches("[a-zA-Z]+") || (first_name.length() < 2)) {
            first_name_error.setText("**Le prénom ne doit contenir que des lettres et sa longueur > 2.");
            error = 1;

        }
        if (!bio.matches("^[a-zA-Z0-9,.!?\\s]+$") || (bio.length() < 50)) {
            bio_error.setText(
                    "La Bio ne doit pas contenir des caractères speciaux et doit avoir au moins 50 caractères .");
            error = 1;
        }
        if (!age.matches("[0-9]+")) {

            age_error.setText("**Age doit être un nombre.");
            error = 1;
        }
        if ((email.isEmpty()) || (!email.endsWith("@esprit.tn"))) {
            email_error.setText("**Le mail doit appartenir à esprit.");
            error = 1;
        }

        if ((password.length() < 8) || password.isEmpty()) {
            password_error.setText("Mot de passe doit avoir au moins 8 caractéres.");
            error = 1;
        }
        if (error == 0) {
            return true;
        } else
            return false;
    }

    public void initialize_errors() {
        bio_error.setText("");
        first_name_error.setText("");
        last_name_error.setText("");
        age_error.setText("");
    }

}
