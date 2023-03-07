package dialogs.test_result;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import dialogs.Base_dialog_controller;
import entities.Test_result;
import utils.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

public class Test_result_controller extends Base_dialog_controller implements Initializable {

  @FXML
  private Label correct_questions_label;

  @FXML
  private ImageView image;

  @FXML
  private Label incorrect_questions_label;

  @FXML
  private Label total_questions_label;

  @FXML
  private Label test_state_label;

  private static String fail_image = "public/image/test_fail_hero.png";

  private Test_result test_result;
  private Integer test_questions_number, correct_options_number, incorrect_options_number;
  private Boolean is_success;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    test_questions_number = 0;
    correct_options_number = 0;
    incorrect_options_number = 0;
  }

  public void hydrate(Test_result test_result, Boolean is_success) {
    this.test_result = test_result;
    this.is_success = is_success;

    test_questions_number = test_result.get_test().get_questions().size();
    correct_options_number = test_result.get_mark();
    incorrect_options_number = test_questions_number - correct_options_number;

    total_questions_label.setText(test_questions_number.toString());
    correct_questions_label.setText(correct_options_number.toString());
    incorrect_questions_label.setText(incorrect_options_number.toString());
    set_image();
    set_state();
  }

  @FXML
  void on_print_button_pressed(ActionEvent event) {
    ///TODO: print NOUR
  }

  private void set_image() {

    if (is_success)
      return;

    try (InputStream stream = new FileInputStream(fail_image)) {
      Image fail_image = new Image(stream);
      image.setImage(fail_image);

    } catch (IOException e) {
      Log.file(e.getMessage());
    }

  }

  private void set_state() {
    if (is_success)
      return;

    test_state_label.setText("Test Echoué");
    test_state_label.setTextFill(Paint.valueOf("#E54242"));

  }

}
