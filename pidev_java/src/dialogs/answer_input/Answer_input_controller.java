package dialogs.answer_input;

import java.util.function.Consumer;

import dialogs.Base_dialog_controller;
import entities.Answer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import utils.Log;
import utils.String_helpers;

public class Answer_input_controller extends Base_dialog_controller {
  private Consumer<Answer> success_callback;

  @FXML
  private TextArea answer_input;

  public void hydrate(Consumer<Answer> success_callback) {
    this.success_callback = success_callback;
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
