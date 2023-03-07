package dialogs.profile_input;

import java.util.Optional;

import dialogs.Base_dialog_controller;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.User_service;
import utils.Router;

public class Profile_input_controller extends Base_dialog_controller {
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

    @FXML
    private void on_modify_button_pressed(ActionEvent event) {
        if (check_input())
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer modification");
        alert.setContentText("Êtes-vous sûr de votre choix ?");
        Optional<ButtonType> is_confirmed = alert.showAndWait();

        if (is_confirmed.get() != ButtonType.OK)
            return;

        user.set_first_name(first_name_input.getText());
        user.set_last_name(last_name_input.getText());
        user.set_age(Integer.parseInt(age_input.getText()));
        user.set_bio(bio_input.getText());

        user_service.update(user);

        Router.render_user_template("Profile", null);

        close();
    }

    private Boolean check_input() {
        initialize_errors();
        String first_name = first_name_input.getText();
        String last_name = last_name_input.getText();
        String bio = bio_input.getText().trim();
        String age = age_input.getText();
        Boolean is_error = false;

        if (first_name.isEmpty() || last_name.isEmpty() || age.isEmpty() || bio.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vous devez remplir tout les champs vides.", ButtonType.OK);
            alert.showAndWait();
            return true;
        }
        if (!last_name.matches("[a-zA-Z ]+") || (last_name.length() <= 2)) {
            last_name_error.setText("**Le nom ne doit contenir que des lettres et sa longueur > 2.");
            is_error = true;
        }
        if (!first_name.matches("[a-zA-Z ]+") || (first_name.length() <= 2)) {
            first_name_error.setText("**Le prénom ne doit contenir que des lettres et sa longueur > 2.");
            is_error = true;
        }
        if (bio.length() < 50) {
            bio_error.setText("**La Bio doit avoir au moins 50 caractères .");
            is_error = true;
        }
        if (!age.matches("[0-9]+") || (Integer.parseInt(age) <= 18) || (Integer.parseInt(age) > 100)) {
            age_error.setText("**Age doit être un nombre superieure à 18.");
            is_error = true;
        }

        return is_error;
    }

    public void initialize_errors() {
        bio_error.setText("");
        first_name_error.setText("");
        last_name_error.setText("");
        age_error.setText("");
    }

}
