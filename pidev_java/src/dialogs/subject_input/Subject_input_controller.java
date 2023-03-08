package dialogs.subject_input;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import dialogs.Base_dialog_controller;
import entities.Course;
import entities.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.Course_service;
import services.Subject_service;
import utils.Router;
import utils.String_helpers;

public class Subject_input_controller extends Base_dialog_controller implements Initializable {
    @FXML
    private HBox actions_wrapper;

    @FXML
    private Button add_button;

    @FXML
    private TextArea description_input;

    @FXML
    private ChoiceBox<String> classes_esprit;

    @FXML
    private Button modify_button;

    @FXML
    private TextField name_input;
    private Boolean is_modify;
    @FXML
    private Text title_label;
    private Subject subject;
    private Subject_service subject_service = new Subject_service();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    public void hydrate(Subject subject) {
        this.is_modify = true;
        this.subject = subject;

        name_input.setText(subject.get_name());
        description_input.setText(subject.get_description());

        set_title();
        set_actions();
        set_classes_esprit();

    }

    public void hydrate() {
        this.is_modify = false;
        this.subject = new Subject();

        set_title();
        set_actions();
        set_classes_esprit();

    }

    @FXML
    void on_add_button_pressed(ActionEvent event) {
        if (!validate_input())
            return;

        build_subject();
        subject_service.add(subject);

        Router.render_admin_template("Subjects_table", null);
        close();
    }

    @FXML
    void on_modify_button_pressed(ActionEvent event) {
        if (!validate_input())
            return;
        build_subject();
        subject_service.update(subject);

        Router.render_admin_template("Subjects_table", null);
        close();
    }

    private void set_classes_esprit() {
        List<String> classes_esprit_items = Arrays
                .asList(Subject.Class_esprit.values())
                .stream()
                .map(classes_esprit -> String_helpers.capitalize(classes_esprit.toString()))
                .collect(Collectors.toList());
        classes_esprit.getItems().addAll(classes_esprit_items);

        if (is_modify)
            classes_esprit.getSelectionModel()
                    .select(String_helpers.capitalize(subject.get_classes_esprit_string()));
        else
            classes_esprit.getSelectionModel().selectFirst();

    }

    private void set_title() {
        if (!is_modify)
            return;

        title_label.setText("Modifier Matiére");

    }

    private void set_actions() {
        if (is_modify)
            actions_wrapper.getChildren().remove(add_button);
        else
            actions_wrapper.getChildren().remove(modify_button);
    }

    private Boolean validate_input() {

        if (name_input.getText().isEmpty() || description_input.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vous devez remplir tout les champs vides.", ButtonType.OK);
            alert.showAndWait();
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer");
        alert.setContentText("Êtes-vous sûr de votre choix ?");
        Optional<ButtonType> is_confirmed = alert.showAndWait();

        if (is_confirmed.get() == ButtonType.OK)
            return true;

        return false;
    }

    private void build_subject() {
        String selected_class = classes_esprit.getSelectionModel().getSelectedItem().toUpperCase();

        subject.set_name(name_input.getText());
        subject.set_description(description_input.getText());
        subject.set_classes_esprit(selected_class);

    }

}
