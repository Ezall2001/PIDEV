package pages;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.swing.plaf.LabelUI;

import config.Log;
import javafx.scene.Node;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class User_profile_controller {
    User user;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Label first_and_last_name_label;
    @FXML
    Label age_label;
    @FXML
    Label level_label;
    @FXML
    Label score_label;
    @FXML
    Label bio_text;
    @FXML
    ImageView student_profile_picture;

    public User_profile_controller() {
        user = new User();
    }

    public void display_user(User user) {
        this.user = user;
        first_and_last_name_label.setText(user.get_first_name() + " " + user.get_last_name());
        age_label.setText("Age: " + user.get_age());
        level_label.setText("Niveau: " + user.get_level_string());
        score_label.setText("Points: " + user.get_score() + "/800");
        bio_text.setText(user.get_description());
        Image image = new Image(new ByteArrayInputStream(user.get_picture_data()));
        student_profile_picture.setImage(image);

    }

    public void Update_profil(MouseEvent mouse_event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Update_profil.fxml"));
            root = loader.load();
            Update_profil_controller update_profil_controller = loader.getController();
            update_profil_controller.set_user(user);

            stage = (Stage) ((Node) mouse_event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            Log.console("azerttoyofb");
            stage.show();
        } catch (IOException e) {
            Log.file(e);
        }
    }

}
