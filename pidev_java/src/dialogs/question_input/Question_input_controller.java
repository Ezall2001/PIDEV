package dialogs.question_input;

import java.util.List;
import java.util.stream.Collectors;
import dialogs.Base_dialog_controller;
import entities.Question;
import entities.Subject;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pages.forum_thread.Forum_thread_controller;
import services.Question_service;
import services.Subject_service;
import services.User_session_service;
import utils.Router;
import utils.String_helpers;

public class Question_input_controller extends Base_dialog_controller {

  @FXML
  private TextArea description_label;

  @FXML
  private TextField question_title_label;

  @FXML
  private ChoiceBox<String> subject_choice;

  @FXML
  private Label title_label;

  @FXML
  private HBox actions_wrapper;

  @FXML
  private Button modify_button;

  @FXML
  private Button add_button;

  private Question question;
  private User user;

  private static Subject_service subject_service = new Subject_service();
  private static Question_service question_service = new Question_service();
  private static User_session_service user_session_service = new User_session_service();

  public void hydrate(Question question) {
    this.question = question;

    title_label.setText("Modifier Question");
    question_title_label.setText(question.get_title());
    description_label.setText(question.get_description());
    actions_wrapper.getChildren().remove(add_button);

    set_subject_choices();
    subject_choice.getSelectionModel().select(question.get_subject().get_name());
  }

  public void hydrate() {
    this.question = new Question();
    this.user = user_session_service.get_user();

    actions_wrapper.getChildren().remove(modify_button);
    set_subject_choices();
  }

  private void set_subject_choices() {
    List<Subject> subjects = subject_service.get_all();

    List<String> subject_names = subjects.stream().map(subject -> subject.get_name()).collect(Collectors.toList());
    ObservableList<String> choices = FXCollections.observableArrayList(subject_names);
    subject_choice.setItems(choices);

    subject_choice.setOnAction(event -> {
      Integer subject_index = subject_choice.getSelectionModel().getSelectedIndex();
      question.set_subject(subjects.get(subject_index));
    });

  }

  @FXML
  void on_add_question_button_pressed(ActionEvent event) {
    if (!validate_input())
      return;

    question.set_user(user);
    question.set_description(description_label.getText());
    question.set_title(question_title_label.getText());

    Question new_question = question_service.add(question);

    if (new_question == null)
      return;

    Router.render_user_template("Forum", null);

    close();
  }

  @FXML
  void on_modify_question_button_pressed(ActionEvent event) {
    if (!validate_input())
      return;

    question.set_description(description_label.getText());
    question.set_title(question_title_label.getText());

    question_service.update(question);

    Router.render_user_template("Forum_thread", (Forum_thread_controller controller) -> controller.hydrate(question));

    close();
  }

  private Boolean validate_input() {

    if (question_title_label.getText().isEmpty() || description_label.getText().isEmpty()
        || question.get_subject() == null) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Champs vides");
      errorAlert.setContentText("vous devez remplir remplir les champs");
      errorAlert.showAndWait();
      return false;
    } else if (question_title_label.getText().length() > 200) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("votre question ne doit pas depasser 40 caractéres");
      errorAlert.setContentText("vous devez remplir de noveau");
      errorAlert.showAndWait();
      return false;
    } else if (String_helpers.check_bad_word(question_title_label.getText()) != null
        || String_helpers.check_bad_word(description_label.getText()) != null) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Attention");
      errorAlert.setContentText("vous n'avez pas le droit d utiliser ce genre des mots");
      errorAlert.showAndWait();
      return false;
    }

    return true;

  }

}
