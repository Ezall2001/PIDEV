package dialogs.profile_input;

import java.io.IOException;

import utils.Log;
import utils.Router;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pages.profile.Profile_controller;
import services.User_service;

public class Profile_input_controller {
    User user;
    User_service user_service;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TextField last_name_update_field;
    @FXML
    TextField first_name_update_field;
    @FXML
    TextField age_update_field;
    @FXML
    TextArea bio_update_area;
    @FXML
    Label last_name_error;
    @FXML
    Label first_name_error;
    @FXML
    Label age_error;
    @FXML
    Label bio_error;

    public void hydrate(User user) {
        this.user = user;
        // last_name_update_field.setText("armen");
        last_name_update_field.setText(user.get_last_name());
        first_name_update_field.setText(user.get_first_name());
        age_update_field.setText(Integer.toString(user.get_age()));
        bio_update_area.setText(user.get_bio());

        // Router.render_user_template("Profile", (Profile_controller controller) -> {
        //     hydrate(new_user);
        // });
    }

    public User get_user() {
        return this.user;
    }

    public void initialize_errors() {
        bio_error.setText("");
        first_name_error.setText("");
        last_name_error.setText("");
        age_error.setText("");
    }

    //TODO: last_name_update_field.getText() hot'hom f variable w if it's empty gued'ha f method wahad'ha
    public boolean check_input() {
        initialize_errors();
        if (!last_name_update_field.getText().isEmpty()) {
            // not allowed to accept special characters or spaces and length at least 3 characters
            if (!last_name_update_field.getText().matches("[a-zA-Z]+")
                    || (last_name_update_field.getText().length() < 2)) {
                last_name_error.setText("Last name must contain only letters and length > 2.");
                Log.console("Last name must contain only letters.");
                return false;
            }
        } else
            last_name_error.setText("last name will stay the same");
        Log.console("last name will stay the same");

        if (!first_name_update_field.getText().isEmpty()) {

            // not allowed to accept special characters or spaces and length at least 3 characters
            if (!first_name_update_field.getText().matches("[a-zA-Z]+")
                    || (first_name_update_field.getText().length() < 2)) {
                first_name_error.setText("First name must contain only letters and length > 2.");
                return false;

            }
        } else
            first_name_error.setText("first name will stay the same");
        Log.console("first name will stay the same");
        if (!bio_update_area.getText().isEmpty()) {

            // not allowed to accept special characters
            String bio = bio_update_area.getText().trim();
            if (!bio.matches("^[a-zA-Z0-9,.!?\\s]+$")) {
                bio_error.setText(
                        "Bio must contain only letters, numbers, commas, periods, exclamation marks, question marks, and spaces");
                return false;

            } else if (bio.length() < 50) {
                bio_error.setText("Bio must contain at least 50 characters long.");
                return false;
            }
        } else
            bio_error.setText("BIO will remain the same.");
        Log.console("BIO will remain the same");
        if (!age_update_field.getText().isEmpty()) {

            // age must be a number
            String age = age_update_field.getText().trim();
            if (!age.matches("[0-9]+")) {

                age_error.setText("Age must be a number.");
                return false;
            }
        } else
            age_error.setText("age will remain the same");
        return true;
    }

    public void return_to_profile(ActionEvent actionEvent) {
        Log.console("eya bagra");
        if (set_input_user(get_user())) {
            Log.console("input set");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("User_profile.fxml"));
                root = loader.load();
                User_profile_controller user_profil_controller = loader.getController();
                user_profil_controller.display_user(user);
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Log.file(e);
            }
        } else {
            Log.console("mochekla houni !"); //meme pas weslet houni
        }
    }

    //TODO: Add alert (profile updated or something went wrong )
    public boolean set_input_user(User user) {

        if (check_input()) {

            if (!last_name_update_field.getText().isEmpty()) {
                user.set_last_name(last_name_update_field.getText());
            }
            if (!first_name_update_field.getText().isEmpty()) {
                user.set_first_name(first_name_update_field.getText());
            }
            if (!age_update_field.getText().isEmpty()) {
                int age_converted = Integer.parseInt(age_update_field.getText());
                user.set_age(age_converted);
            }
            if (!bio_update_area.getText().isEmpty()) {
                user.set_bio(bio_update_area.getText());
            }
            this.user = user;
            user_service.update(user);

            Log.console(" user.toString()");
            return true;
        }
        Log.console("false");
        return false;
    }
    // public void check_input(ActionEvent event) {
    //     initialize_errors();
    //     if (!last_name_update_field.getText().isEmpty()) {
    //         // not allowed to accept special characters or spaces and length at least 3 characters
    //         if (!last_name_update_field.getText().matches("[a-zA-Z]+")
    //                 || (last_name_update_field.getText().length() < 2)) {
    //             last_name_error.setText("Last name must contain only letters and length > 2.");
    //             Log.console("Last name must contain only letters.");

    //         }

    //     } else
    //         last_name_error.setText("last name will stay the same");
    //     Log.console("last name will stay the same");

    //     if (!first_name_update_field.getText().isEmpty()) {

    //         // not allowed to accept special characters or spaces and length at least 3 characters
    //         if (!first_name_update_field.getText().matches("[a-zA-Z]+")
    //                 || (first_name_update_field.getText().length() < 2)) {
    //             first_name_error.setText("First name must contain only letters and length > 2.");
    //             // return false;

    //         }
    //     } else
    //         first_name_error.setText("first name will stay the same");
    //     Log.console("first name will stay the same");
    //     if (!bio_update_area.getText().isEmpty()) {

    //         // not allowed to accept special characters
    //         String bio = bio_update_area.getText().trim();
    //         if (!bio.matches("^[a-zA-Z0-9,.!?\\s]+$")) {
    //             bio_error.setText(
    //                     "Bio must contain only letters, numbers, commas, periods, exclamation marks, question marks, and spaces");
    //             // return false;

    //         } else if (bio.length() < 50) {
    //             bio_error.setText("Bio must contain at least 50 characters long.");
    //             // return false;
    //         }
    //     } else
    //         bio_error.setText("BIO will remain the same.");
    //     Log.console("BIO will remain the same");
    //     if (!age_update_field.getText().isEmpty()) {

    //         // age must be a number
    //         String age = age_update_field.getText().trim();
    //         if (!age.matches("[0-9]+")) {

    //             age_error.setText("Age must be a number.");
    //             // return false;
    //         }
    //     } else
    //         age_error.setText("age will remain the same");
    //     // return true;
    // }

}
