package dialogs;

import javafx.stage.Stage;

public class Base_dialog_controller {
  public Stage stage;

  public void set_stage(Stage stage) {
    this.stage = stage;
  }

  public void close() {
    stage.close();
  }
}
