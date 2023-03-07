package dialogs.answer_input;

import java.util.function.Consumer;

import components.answer_row.Answer_row_controller;
import dialogs.Base_dialog_controller;
import entities.Answer;
import entities.Question;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import pages.forum_thread.Forum_thread_controller;
import services.Answer_service;
import services.Question_service;
import services.User_session_service;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import utils.Log;
import utils.Router;
import utils.String_helpers;

public class Answer_input_controller extends Base_dialog_controller {
  private Consumer<Answer> success_callback;

  @FXML
  private TextArea answer_input;

  @FXML
  private HBox actions_wrapper;
  @FXML
  private Button add_button;
  private Answer answer;
  @FXML
  private Button modify_button;
  private User user;
  private static Answer_service answer_service = new Answer_service();
  private static User_session_service user_session_service = new User_session_service();

  public void hydrate(Consumer<Answer> success_callback) {
    this.success_callback = success_callback;
    this.answer = new Answer();

    actions_wrapper.getChildren().remove(modify_button);
  }

  public void hydrate(Answer answer) {
    this.answer = answer;
    answer_input.setText(answer.get_message());
    actions_wrapper.getChildren().remove(add_button);
  }

  @FXML
  void on_add_answer_button_pressed(ActionEvent event) {

    if (!validate_input())
      return;

    Answer answer = new Answer();
    answer.set_message(answer_input.getText());
    success_callback.accept(answer);
    close();

  }

  @FXML
  void on_modify_answer_button_pressed(ActionEvent event) {

    if (!validate_input())
      return;

    answer.set_message(answer_input.getText());

    answer_service.update(answer);
    Router.render_user_template("Forum_thread", (Forum_thread_controller controller) -> controller.hydrate(answer));

    close();
  }

  private Boolean validate_input() {
    if (answer_input.getText().isEmpty()) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Champs vides");
      errorAlert.setContentText("vous devez remplir remplir les champs");
      errorAlert.showAndWait();
      return false;

    } else if (String_helpers.check_bad_word(answer_input.getText()) != null) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Attention");
      errorAlert.setContentText("vous n'avez pas le droit d utiliser ce genre des mots");
      errorAlert.showAndWait();
      return false;
    }

    return true;
  }

}
