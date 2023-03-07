package dialogs.test_question_input;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Test;
import entities.Test_qs;
import javafx.fxml.Initializable;

public class Test_question_input_controller implements Initializable {

  private List<Test> tests;
  private Test_qs test_qs;
  private Boolean is_modify;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tests = subject_service.get_all();
  }

  public void hydrate(Test_qs test_qs) {
    this.is_modify = true;
    this.test_qs = test_qs;

    name_input.setText(test_qs.get_name());
    description_input.setText(test_qs.get_description());

    set_title();
    set_actions();
    set_difficulties();
    set_subjects();
  }

  public void hydrate() {
    this.is_modify = false;
    this.test_qs = new Test_qs();

    set_title();
    set_actions();
    set_difficulties();
    set_subjects();

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

}
