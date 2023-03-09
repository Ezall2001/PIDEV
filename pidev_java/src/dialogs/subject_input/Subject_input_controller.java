package dialogs.subject_input;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import dialogs.Base_dialog_controller;
import entities.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Node;
import services.Subject_service;
import utils.Router;

public class Subject_input_controller extends Base_dialog_controller {
    @FXML
    private HBox actions_wrapper;

    @FXML
    private Button add_button;

    @FXML
    private HBox classe_esprit_wrapper;

    @FXML
    private TextArea description_input;

    @FXML
    private Button modify_button;

    @FXML
    private TextField name_input;

    @FXML
    private Text title_label;

    private static Subject_service subject_service = new Subject_service();
    private static String empty_choice = "---";
    private static String combo_box_style = "-fx-background-color:  #fee39a; -fx-background-radius: 30;";
    private static DropShadow drop_shadow = init_combo_box_drop_shadow();

    private Boolean is_modify;
    private Subject subject;

    private static DropShadow init_combo_box_drop_shadow() {
        DropShadow drop_shadow = new DropShadow();
        drop_shadow.setColor(new Color(0, 0, 0, 0.25));
        drop_shadow.setRadius(10);
        drop_shadow.setOffsetX(0);
        drop_shadow.setOffsetY(3);
        return drop_shadow;
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

    @FXML
    void on_add_class_esprit_button_pressed(ActionEvent event) {
        if (classe_esprit_wrapper.getChildren().size() > 4)
            return;
        set_class_esprit_combo_box();
    }

    private void set_classes_esprit() {
        if (!is_modify)
            return;

        List<String> classes_esprit = subject
                .get_classes_esprit_list()
                .stream()
                .map(class_esprit -> class_esprit.toString())
                .collect(Collectors.toList());

        Integer combo_box_index = 0;
        for (String class_esprit : classes_esprit) {
            set_class_esprit_combo_box();

            @SuppressWarnings("unchecked")
            ComboBox<String> class_esprit_combo_box = (ComboBox<String>) classe_esprit_wrapper.getChildren().get(
                    combo_box_index);

            class_esprit_combo_box.getSelectionModel().select(class_esprit);
            combo_box_index++;
        }

    }

    private void set_class_esprit_combo_box() {
        ComboBox<String> class_esprit_combo_box = new ComboBox<>();
        class_esprit_combo_box.setStyle(combo_box_style);
        class_esprit_combo_box.setPrefSize(70, 30);
        class_esprit_combo_box.setEffect(drop_shadow);
        class_esprit_combo_box.setCursor(Cursor.HAND);

        List<String> classes_esprit_items = Arrays
                .asList(Subject.Class_esprit.values())
                .stream()
                .map(classes_esprit -> classes_esprit.toString().toUpperCase())
                .collect(Collectors.toList());

        class_esprit_combo_box.getItems().add(empty_choice);
        class_esprit_combo_box.getItems().addAll(classes_esprit_items);
        class_esprit_combo_box.getSelectionModel().selectFirst();

        classe_esprit_wrapper.getChildren().add(class_esprit_combo_box);
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

        List<String> combo_box_values = get_choice_box_values();

        for (Integer i = 0; i < combo_box_values.size() - 1; i++)
            for (Integer j = i + 1; j < combo_box_values.size(); j++)
                if (combo_box_values.get(i).equals(combo_box_values.get(j))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Classes similaires ! prière de choisir des classes diffèrents",
                            ButtonType.OK);
                    alert.showAndWait();
                    return false;
                }

        if (name_input.getText().isEmpty() || description_input.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Vous devez remplir tout les champs vides.",
                    ButtonType.OK);
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

        String classes_esprit = String.join(" / ", get_choice_box_values());

        subject.set_name(name_input.getText());
        subject.set_description(description_input.getText());
        subject.set_classes_esprit(classes_esprit);
    }

    private List<String> get_choice_box_values() {
        List<String> combo_box_values = classe_esprit_wrapper
                .getChildren()
                .stream()
                .filter((Node node) -> {
                    @SuppressWarnings("unchecked")
                    ComboBox<String> combo_box = (ComboBox<String>) node;
                    return !combo_box.getSelectionModel().getSelectedItem().equals(empty_choice);
                }).map((Node node) -> {
                    @SuppressWarnings("unchecked")
                    ComboBox<String> combo_box = (ComboBox<String>) node;
                    return combo_box.getSelectionModel().getSelectedItem();
                }).collect(Collectors.toList());

        return combo_box_values;
    }

}
