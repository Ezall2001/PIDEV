package dialogs.course_input;

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

public class Course_input_controller extends Base_dialog_controller implements Initializable {

  @FXML
  private HBox actions_wrapper;

  @FXML
  private Button add_button;

  @FXML
  private TextArea description_input;

  @FXML
  private ChoiceBox<String> difficulties_menu;

  @FXML
  private Button modify_button;

  @FXML
  private TextField name_input;

  @FXML
  private ChoiceBox<String> subjects_menu;

  @FXML
  private Text title_label;

  private static Course_service course_service = new Course_service();
  private static Subject_service subject_service = new Subject_service();

  private List<Subject> subjects;
  private Course course;
  private Boolean is_modify;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    subjects = subject_service.get_all();
  }

  public void hydrate(Course course) {
    this.is_modify = true;
    this.course = course;

    name_input.setText(course.get_name());
    description_input.setText(course.get_description());

    set_title();
    set_actions();
    set_difficulties();
    set_subjects();
  }

  public void hydrate() {
    this.is_modify = false;
    this.course = new Course();

    set_title();
    set_actions();
    set_difficulties();
    set_subjects();

  }

  @FXML
  void on_add_button_pressed(ActionEvent event) {
    if (!validate_input())
      return;

    build_course();
    course_service.add(course);

    Router.render_admin_template("Courses_table", null);
    close();
  }

  @FXML
  void on_modify_button_pressed(ActionEvent event) {
    if (!validate_input())
      return;

    build_course();
    course_service.update(course);

    Router.render_admin_template("Courses_table", null);
    close();
  }

  private void set_title() {
    if (!is_modify)
      return;

    title_label.setText("Modifier Cours");

  }

  private void set_actions() {
    if (is_modify)
      actions_wrapper.getChildren().remove(add_button);
    else
      actions_wrapper.getChildren().remove(modify_button);
  }

  private void set_difficulties() {
    List<String> difficulty_items = Arrays
        .asList(Course.Difficulty.values())
        .stream()
        .map(difficulty -> String_helpers.capitalize(difficulty.toString()))
        .collect(Collectors.toList());

    difficulties_menu.getItems().addAll(difficulty_items);

    if (is_modify)
      difficulties_menu.getSelectionModel()
          .select(String_helpers.capitalize(course.get_difficulty_string()));
    else
      difficulties_menu.getSelectionModel().selectFirst();

  }

  private void set_subjects() {
    List<String> subject_items = subjects
        .stream()
        .map(subject -> subject.get_name())
        .collect(Collectors.toList());

    subjects_menu.getItems().addAll(subject_items);

    if (is_modify)
      subjects_menu.getSelectionModel().select(course.get_subject().get_name());
    else
      subjects_menu.getSelectionModel().selectFirst();
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

  private void build_course() {
    Subject selected_subject = subjects.get(subjects_menu.getSelectionModel().getSelectedIndex());
    String selected_difficulty = difficulties_menu.getSelectionModel().getSelectedItem().toUpperCase();

    course.set_name(name_input.getText());
    course.set_description(description_input.getText());
    course.set_difficulty(selected_difficulty);
    course.set_subject(selected_subject);

  }

}
