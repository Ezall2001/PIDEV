package components.question_row;

import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pages.forum_thread.Forum_thread_controller;
import utils.Router;

public class Question_row_controller {
  private Question question;

  @FXML
  private Label creator_label;

  @FXML
  private Label question_label;

  @FXML
  private Label subject_label;

  public void hydrate(Question question) {
    this.question = question;
    creator_label.setText(question.get_user().get_full_name());
    question_label.setText(question.get_title());
    subject_label.setText(question.get_subject().get_name());

  }

  @FXML
  void on_see_responses_button_pressed(ActionEvent event) {
    Router.render_user_template("Forum_thread", (Forum_thread_controller controller) -> controller.hydrate(question));
  }

}
