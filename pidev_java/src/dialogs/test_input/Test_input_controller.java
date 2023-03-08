package dialogs.test_input;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import dialogs.Base_dialog_controller;
import entities.Test;
import entities.Course;
import entities.Subject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.Course_service;
import services.Subject_service;
import services.Test_service;
import utils.Router;
import utils.Log;

public class Test_input_controller extends Base_dialog_controller implements Initializable {

    @FXML
    private HBox actions_wrapper;

    @FXML
    private Button add_button;

    @FXML
    private TextField duration_input;

    @FXML
    private TextField min_points_input;

    @FXML
    private Button modify_button;

    @FXML
    private ChoiceBox<String> subject_name_choice_box;

    @FXML
    private ChoiceBox<String> course_name_choice_box;

    @FXML
    private Text title_label;

    private static Course_service course_service = new Course_service();
    private static Subject_service subject_service = new Subject_service();
    private static Test_service test_service = new Test_service();
    private static String empty_choice = "------------";

    private List<Subject> subjects;
    private List<Course> courses;
    private Test test;
    private Boolean is_modify;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        subjects = subject_service.get_all();
        courses = course_service.get_all();
    }

    public void hydrate(Test test) {
        this.is_modify = true;
        this.test = test;

        duration_input.setText(test.get_duration().toString());
        min_points_input.setText(test.get_min_points().toString());

        set_title();
        set_actions();
        set_courses();
        set_subjects();
    }

    public void hydrate() {
        this.is_modify = false;
        this.test = new Test();

        set_title();
        set_actions();
        set_courses();
        set_subjects();
    }

    @FXML
    void on_add_button_pressed(ActionEvent event) {
        if (!validate_input())
            return;

        build_test();
        test_service.add(test);
        Router.render_admin_template("Tests_table", null);
        close();
    }

    @FXML
    void on_modify_button_pressed(ActionEvent event) {
        if (!validate_input())
            return;

        build_test();
        test_service.update(test);
        Router.render_admin_template("Tests_table", null);
        close();
    }

    private void set_title() {
        if (!is_modify)
            return;

        title_label.setText("Modifier Test");

    }

    private void set_actions() {
        if (is_modify)
            actions_wrapper.getChildren().remove(add_button);
        else
            actions_wrapper.getChildren().remove(modify_button);
    }

    private void set_subjects() {
        List<String> subject_name_choices = subjects
                .stream()
                .map(subject -> subject.get_name())
                .collect(Collectors.toList());

        subject_name_choice_box.getItems().add(empty_choice);
        subject_name_choice_box.getItems().addAll(subject_name_choices);

        if (!is_modify || (is_modify && test.get_type().equals(Test.Type.COURSE)))
            subject_name_choice_box.getSelectionModel().select(empty_choice);

        else
            subject_name_choice_box.getSelectionModel().select(test.get_subject().get_name());

        subject_name_choice_box.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String prv_value,
                    String curr_value) {
                if (curr_value.equals(empty_choice))
                    return;
                course_name_choice_box.getSelectionModel().select(empty_choice);
            }
        });

    }

    private void set_courses() {
        List<String> course_name_choices = courses
                .stream()
                .map(course -> course.get_name())
                .collect(Collectors.toList());

        course_name_choice_box.getItems().add(empty_choice);
        course_name_choice_box.getItems().addAll(course_name_choices);

        if (!is_modify || (is_modify && test.get_type().equals(Test.Type.SUBJECT)))
            course_name_choice_box.getSelectionModel().select(empty_choice);

        else
            course_name_choice_box.getSelectionModel().select(test.get_course().get_name());

        course_name_choice_box.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String prv_value,
                    String curr_value) {

                if (curr_value.equals(empty_choice))
                    return;

                subject_name_choice_box.getSelectionModel().select(empty_choice);
            }
        });
    }

    private Boolean validate_input() {

        if (duration_input.getText().isEmpty() ||
                min_points_input.getText().isEmpty()) {

            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Champs vides");
            errorAlert.setContentText("vous devez remplir remplir les champs");
            errorAlert.showAndWait();
            return false;
        }

        if (subject_name_choice_box.getValue().equals(empty_choice) &&
                course_name_choice_box.getValue().equals(empty_choice)) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Cours ou matière invalide");
            errorAlert.setContentText("Vous devez choisir leurs noms respectives");
            errorAlert.showAndWait();
            return false;
        }

        try {
            Integer.parseInt(duration_input.getText());
            Integer.parseInt(min_points_input.getText());
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Seuil ou durée invalide");
            errorAlert.setContentText("Le seuil ou la durée doit être numérique");
            errorAlert.showAndWait();
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

    private void build_test() {

        test.set_duration(Integer.parseInt(duration_input.getText()));
        test.set_min_points(Integer.parseInt(min_points_input.getText()));

        if (subject_name_choice_box.getSelectionModel().getSelectedItem().equals(empty_choice)) {
            test.set_subject(null);
            test.set_course(courses.get(course_name_choice_box.getSelectionModel().getSelectedIndex() - 1));
            test.set_type(Test.Type.COURSE);
        } else {
            test.set_course(null);
            test.set_subject(subjects.get(subject_name_choice_box.getSelectionModel().getSelectedIndex() - 1));
            test.set_type(Test.Type.SUBJECT);
        }

    }

}
