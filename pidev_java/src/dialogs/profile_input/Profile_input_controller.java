package dialogs.profile_input;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pages.profile.Profile_controller;
import services.User_service;
import utils.Router;

public class Profile_input_controller {
    private static User_service user_service = new User_service();
    User user;
    @FXML
    private Text age_error;

    @FXML
    private TextField age_input;

    @FXML
    private Text bio_error;

    @FXML
    private TextField bio_input;

    @FXML
    private Text first_name_error;

    @FXML
    private TextField first_name_input;

    @FXML
    private Text last_name_error;

    @FXML
    private TextField last_name_input;

    @FXML
    private Button modify_button;

    public void hydrate(User user) {
        this.user = user;
        last_name_input.setText(user.get_last_name());
        first_name_input.setText(user.get_first_name());
        age_input.setText(Integer.toString(user.get_age()));
        bio_input.setText(user.get_bio());

    }

    public void Modify_profile_button_pressed(ActionEvent event) {
        if (check_input()) {
            User new_user = new User(user.get_id(),
                    first_name_input.getText(),
                    last_name_input.getText(),
                    bio_input.getText(),
                    Integer.parseInt(age_input.getText()),
                    user.get_score(),
                    user.get_email(),
                    user.get_avatar_path(),
                    user.get_type().toString());

            Router.render_user_template("Profile", (Profile_controller controller) -> {
                controller.hydrate(user_service.update(new_user));
            });

        }
    }

    public boolean check_input() {
        initialize_errors();
        String first_name = first_name_input.getText();
        String last_name = last_name_input.getText();
        String bio = bio_input.getText().trim();
        String age = age_input.getText();
        int error = 0;
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

            age_error.setText("l'Age doit être un nombre.");
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
