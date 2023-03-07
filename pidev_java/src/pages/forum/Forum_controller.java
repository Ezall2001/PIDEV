package pages.forum;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import components.question_row.Question_row_controller;
import dialogs.question_input.Question_input_controller;
import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import services.Question_service;
import utils.Log;
import utils.Router;

public class Forum_controller implements Initializable {

  private static Question_service question_service = new Question_service();
  private List<Question> questions;
  private List<Question> sorted_questions;

  @FXML
  private MenuButton sort_by_button;

  @FXML
  private MenuItem on_sort_by_date_button_pressed;

  @FXML
  private MenuItem on_sort_by_subject_button_pressed;

  @FXML
  private VBox question_list_wrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.questions = question_service.get_all();
    sorted_questions = new ArrayList<>(questions);
    set_questions_list();
  }

  @FXML
  void on_add_button_pressed(ActionEvent event) {
    Router.render_dialog("Question_input", (Question_input_controller controller) -> controller.hydrate());
  }

  @FXML
  void on_sort_by_date_button_pressed(ActionEvent event) {
    sort_by_button.setText("Trier par : Date De Publication");
    sorted_questions = questions.stream().sorted((a, b) -> {
      return a.get_id().compareTo(b.get_id()) * -1;
    }).collect(Collectors.toList());

    set_questions_list();
  }

  @FXML
  void on_sort_by_subject_button_pressed(ActionEvent event) {
    sort_by_button.setText("Trier par : Matiére");
    sorted_questions = questions.stream().sorted((a, b) -> {
      return a.get_subject().get_name().compareTo(b.get_subject().get_name());
    }).collect(Collectors.toList());

    set_questions_list();
  }

  @FXML
  void on_no_sort_button_pressed(ActionEvent event) {
    sort_by_button.setText("Trier par : ");
    sorted_questions = new ArrayList<>(questions);

    set_questions_list();
  }

  private void set_questions_list() {

    List<Parent> question_rows = sorted_questions.stream()
        .map(question -> Router.load_component("question_row",
            (Question_row_controller controller) -> controller.hydrate(question)))
        .collect(Collectors.toList());

    question_list_wrapper.getChildren().clear();
    question_list_wrapper.getChildren().addAll(question_rows);

  }

}
