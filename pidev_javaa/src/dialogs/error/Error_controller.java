package dialogs.error;

import dialogs.Base_dialog_controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Error_controller extends Base_dialog_controller {

  @FXML
  private Label Error_label;

  public void hydrate(String error_message) {
    Error_label.setText(error_message);
  }
}
