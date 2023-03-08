package dialogs.test_question_input;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import dialogs.Base_dialog_controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.Test_qs_service;
import services.Test_service;
import utils.Router;
import entities.Test;
import entities.Test_qs;
import javafx.fxml.Initializable;

public class Test_question_input_controller extends Base_dialog_controller implements Initializable {

  @FXML
  private HBox actions_wrapper;

  @FXML
  private Button add_button;

  @FXML
  private ChoiceBox<String> correct_option_choice_box;

  @FXML
  private ChoiceBox<String> test_choice_box;

  @FXML
  private Button modify_button;

  @FXML
  private TextField option_a_input;

  @FXML
  private TextField option_b_input;

  @FXML
  private TextField option_c_input;

  @FXML
  private TextField option_d_input;

  @FXML
  private TextField question_input;

  @FXML
  private TextField question_number_input;

  @FXML
  private Text title_label;

  static private Test_service test_service = new Test_service();
  static private Test_qs_service test_qs_service = new Test_qs_service();
  static private List<String> correct_options = Arrays.asList("A", "B", "C", "D");

  private List<Test> tests;
  private Test_qs test_qs;
  private Boolean is_modify;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tests = test_service.get_all();
  }

  public void hydrate(Test_qs test_qs) {
    this.is_modify = true;
    this.test_qs = test_qs;

    question_input.setText(test_qs.get_question());
    option_a_input.setText(test_qs.get_optionA());
    option_b_input.setText(test_qs.get_optionB());
    option_c_input.setText(test_qs.get_optionC());
    option_d_input.setText(test_qs.get_optionD());
    question_number_input.setText(test_qs.get_question_number().toString());

    set_title();
    set_actions();
    set_correct_options();
    set_tests();
  }

  public void hydrate() {
    this.is_modify = false;
    this.test_qs = new Test_qs();

    set_title();
    set_actions();
    set_correct_options();
    set_tests();

  }

  @FXML
  void on_add_button_pressed(ActionEvent event) {
    if (!validate_input())
      return;

    build_question();
    test_qs_service.add(test_qs);

    Router.render_admin_template("Test_questions_table", null);
    close();
  }

  @FXML
  void on_modify_button_pressed(ActionEvent event) {
    if (!validate_input())
      return;

    build_question();
    test_qs_service.update(test_qs);

    Router.render_admin_template("Test_questions_table", null);
    close();
  }

  private void set_title() {
    if (!is_modify)
      return;

    title_label.setText("Modifier Question");
  }

  private void set_actions() {
    if (is_modify)
      actions_wrapper.getChildren().remove(add_button);
    else
      actions_wrapper.getChildren().remove(modify_button);
  }

  private void set_correct_options() {
    correct_option_choice_box.getItems().addAll(correct_options);
    if (is_modify) {
      correct_option_choice_box.getSelectionModel().select(test_qs.get_correct_option_index());
      return;
    }
    correct_option_choice_box.getSelectionModel().selectFirst();
  }

  private void set_tests() {
    List<String> test_choices = tests
        .stream()
        .map(test -> test.get_name())
        .collect(Collectors.toList());

    test_choice_box.getItems().addAll(test_choices);

    if (is_modify) {
      test_choice_box.getSelectionModel().select(test_qs.get_test().get_name());
      return;
    }

    test_choice_box.getSelectionModel().selectFirst();
  }

  private Boolean validate_input() {
    String[] input_values = { option_a_input.getText(), option_b_input.getText(), option_c_input.getText(),
        option_d_input.getText() };

    if (option_a_input.getText().isEmpty() ||
        option_b_input.getText().isEmpty() ||
        option_c_input.getText().isEmpty() ||
        option_d_input.getText().isEmpty() ||
        question_input.getText().isEmpty()) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Champs vides");
      errorAlert.setContentText("vous devez remplir remplir les champs");
      errorAlert.showAndWait();

      return false;
    }

    try {
      Integer.parseInt(question_number_input.getText());
    } catch (NumberFormatException e) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Numéro invalide");
      errorAlert.setContentText("le numéro de la question doit être numérique");
      errorAlert.showAndWait();
      return false;
    }

    for (Integer i = 0; i < input_values.length - 1; i++)
      for (Integer j = i + 1; j < input_values.length; j++)
        if (input_values[i].equals(input_values[j])) {
          Alert errorAlert = new Alert(AlertType.ERROR);
          errorAlert.setHeaderText("Options invalides");
          errorAlert.setContentText("Les options doivent être différents");
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

  private void build_question() {
    Test selected_test = tests.get(test_choice_box.getSelectionModel().getSelectedIndex());

    test_qs.set_question_number(Integer.parseInt(question_number_input.getText()));
    test_qs.set_question(question_input.getText());
    test_qs.set_optionA(option_a_input.getText());
    test_qs.set_optionB(option_b_input.getText());
    test_qs.set_optionC(option_c_input.getText());
    test_qs.set_optionD(option_d_input.getText());
    test_qs.set_test(selected_test);
    test_qs.set_correct_option(correct_option_choice_box.getSelectionModel().getSelectedIndex());

  }

}
