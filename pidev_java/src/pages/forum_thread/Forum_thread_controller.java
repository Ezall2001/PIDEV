package pages.forum_thread;

import entities.Question;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import components.answer_row.Answer_row_controller;
import dialogs.answer_input.Answer_input_controller;
import dialogs.question_input.Question_input_controller;
import entities.Answer;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pages.search.Search_controller;
import services.Answer_service;
import services.Question_service;
import services.User_session_service;
import utils.Log;
import utils.Router;
import javafx.scene.Parent;

public class Forum_thread_controller implements Initializable {

  private static Answer_service answer_service = new Answer_service();
  private static Question_service question_service = new Question_service();
  private static User_session_service user_session_service = new User_session_service();

  private Question question;
  private List<Answer> sorted_answers;
  private Boolean is_creator;
  private User user;

  @FXML
  private HBox actions_wrapper;

  @FXML
  private VBox answers_list_wrapper;

  @FXML
  private Label description_label;

  @FXML
  private Label question_creator_label;

  @FXML
  private Label question_label;

  @FXML
  private MenuButton sort_by_button;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = user_session_service.get_user();
    if (user == null)
      return;

    this.user = user;
    this.is_creator = false;
    this.sorted_answers = new ArrayList<>();

  }

  public void hydrate(Question question) {
    this.question = answer_service.find_by_id_question(question);

    if (question == null)
      return;

    if (question.get_user().get_id().equals(user.get_id()))
      is_creator = true;

    question_label.setText(question.get_title());
    description_label.setText(question.get_description());
    question_creator_label.setText(question.get_user().get_full_name());

    this.sorted_answers = new ArrayList<>(question.get_answers());

    set_controls();
    set_answers_list();
  }

  public void hydrate(Answer answer) {
    Question q = answer.get_question();
    Log.console(q);
    hydrate(q);
  }

  @FXML
  public void on_search_web_view_button_pressed(ActionEvent event) {
    Router.render_user_template("Search",
        (Search_controller controller) -> controller.hydrate(question));
  }

  @FXML
  void on_add_answer_pressed(ActionEvent event) {
    Router.render_dialog("Answer_input", (Answer_input_controller controller) -> {
      controller.hydrate((new_answer) -> {

        new_answer.set_user(user);
        new_answer.set_question(question);

        new_answer = answer_service.add(new_answer);
        question.get_answers().add(new_answer);

        new_answer.set_question(question);

        sorted_answers.add(new_answer);
        set_answers_list();
      });
    });

  }

  @FXML
  void on_delete_button_pressed(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmer");
    alert.setContentText("Êtes-vous sûr de votre choix ?");
    Optional<ButtonType> is_confirmed = alert.showAndWait();

    if (is_confirmed.get() != ButtonType.OK)
      return;

    question_service.delete(question);
    Router.render_user_template("Forum", null);
  }

  @FXML
  void on_modify_button_pressed(ActionEvent event) {
    Router.render_dialog("Question_input", (Question_input_controller controller) -> controller.hydrate(question));
  }

  @FXML
  void on_sort_by_date_button_pressed(ActionEvent event) {
    sort_by_button.setText("Trier par : Date De Publication");

    sorted_answers = question.get_answers().stream().sorted((a, b) -> {
      return a.get_id().compareTo(b.get_id()) * -1;
    }).collect(Collectors.toList());

    set_answers_list();
  }

  @FXML
  void on_sort_by_vote_button_pressed(ActionEvent event) {
    sort_by_button.setText("Trier par : Intéressant");

    sorted_answers = question.get_answers().stream().sorted((a, b) -> {
      return a.get_vote_nb().compareTo(b.get_vote_nb()) * -1;
    }).collect(Collectors.toList());

    set_answers_list();
  }

  private void set_controls() {
    if (is_creator)
      return;

    actions_wrapper.getChildren().clear();
  }

  public void set_answers_list() {

    List<Parent> answer_rows = sorted_answers.stream()
        .map(answer -> Router.load_component("Answer_row", (Answer_row_controller controller) -> {
          controller.hydrate(answer, (deleted_answer) -> {
            answer_service.delete(deleted_answer);
            question.get_answers().remove(deleted_answer);
            sorted_answers.remove(deleted_answer);
            set_answers_list();
          });
        })).collect(Collectors.toList());

    answers_list_wrapper.getChildren().clear();
    answers_list_wrapper.getChildren().addAll(answer_rows);

  }

}