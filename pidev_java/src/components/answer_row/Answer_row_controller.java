package components.answer_row;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import entities.Answer;
import entities.User;
import entities.Vote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import services.User_session_service;
import services.Vote_service;

public class Answer_row_controller implements Initializable {
  @FXML
  private HBox action_wrapper;

  @FXML
  private Label creator_label;

  @FXML
  private Button down_vote_button;

  @FXML
  private Label message_label;

  @FXML
  private Button up_vote_button;

  @FXML
  private Label vote_label;

  private Answer answer;
  private Vote vote;
  private Boolean is_creator;
  private User user;
  private Consumer<Answer> delete_callback;

  private static User_session_service user_session_service = new User_session_service();
  private static Vote_service vote_service = new Vote_service();

  private static String default_up_vote_button_style = "-fx-shape:  'M150 0 L75 200 L225 200 Z'; -fx-border-color: #97A8F8;";
  private static String default_down_vote_button_style = "-fx-shape: 'M0 0 L50 50 L100 0 Z'; -fx-border-color: #97A8F8;";

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    User user = user_session_service.get_user();

    if (user == null)
      return;

    this.user = user;
    is_creator = false;

  }

  public void hydrate(Answer answer, Consumer<Answer> delete_callback) {
    this.answer = answer;
    this.delete_callback = delete_callback;

    if (user.get_id().equals(answer.get_user().get_id()))
      is_creator = true;

    vote = new Vote();
    vote.set_answer(answer);
    vote.set_user(user);
    vote.set_type_vote(0);

    Vote found_vote = vote_service.find_by_id_user_and_id_answer(vote);

    if (found_vote != null)
      this.vote = found_vote;

    creator_label.setText(answer.get_user().get_full_name());
    message_label.setText(answer.get_message());

    set_actions();
    set_vote();
  }

  @FXML
  void on_delete_button_pressed(ActionEvent event) {
    delete_callback.accept(answer);
  }

  @FXML
  void on_down_vote_button_pressed(ActionEvent event) {
    set_vote_state(-1);
  }

  @FXML
  void on_up_vote_button_pressed(ActionEvent event) {
    set_vote_state(1);
  }

  void set_vote_state(Integer modifier) {
    Integer curr_vote = vote.get_type_vote();

    if (curr_vote.equals(modifier)) {
      vote_service.delete(vote);
      vote.set_type_vote(0);

    } else if (curr_vote.equals(0)) {
      vote.set_type_vote(modifier);
      vote_service.add(vote);

    } else {
      vote.set_type_vote(modifier);
      vote_service.update(vote);
    }

    set_vote();
  }

  private void set_actions() {
    if (is_creator)
      return;

    action_wrapper.getChildren().clear();

  }

  private void set_vote() {
    Integer curr_vote = vote.get_type_vote();

    vote_label.setText(answer.get_vote_nb().toString());

    if (curr_vote.equals(0)) {
      up_vote_button.setStyle("-fx-background-color: #eee;" + default_up_vote_button_style);
      down_vote_button.setStyle("-fx-background-color: #eee;" + default_down_vote_button_style);
    }

    else if (curr_vote.equals(1)) {
      up_vote_button.setStyle("-fx-background-color: #97A8F8;" + default_up_vote_button_style);
      down_vote_button.setStyle("-fx-background-color: #eee;" + default_down_vote_button_style);
    }

    else if (curr_vote.equals(-1)) {
      up_vote_button.setStyle("-fx-background-color: #eee;" + default_up_vote_button_style);
      down_vote_button.setStyle("-fx-background-color: #97A8F8;" + default_down_vote_button_style);
    }

  }

}
