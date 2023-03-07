package pages.test;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.util.Pair;
import dialogs.test_result.Test_result_controller;
import entities.Course;
import entities.User;
import entities.Subject;
import entities.Test;
import entities.Test_qs;
import entities.Test_result;
import services.Test_result_service;
import services.Test_service;
import services.User_session_service;
import utils.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Test_controller implements Initializable {

  @FXML
  private HBox actions_wrapper;

  @FXML
  private Label active_question_label;

  @FXML
  private ToggleGroup answer_group;

  @FXML
  private Button finish_button;

  @FXML
  private Button next_question_button;

  @FXML
  private Label minutes_label;

  @FXML
  private Label question_label;

  @FXML
  private Label seconds_label;

  @FXML
  private Label subject_name_label;

  static private Test_service test_service = new Test_service();
  static private User_session_service user_session_service = new User_session_service();
  static private Test_result_service test_result_service = new Test_result_service();

  private Test test;
  private Test_result test_result;
  private Integer active_question_index;
  private Boolean test_ended;
  private Timeline timeline;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    User user = user_session_service.get_user();

    if (user == null)
      return;

    this.active_question_index = 0;
    this.test_ended = false;
    this.test_result = new Test_result();
    this.test_result.set_user(user);
  }

  public void hydrate(Subject subject) {

    Test test = new Test();
    test.set_subject(subject);
    test.set_type(Test.Type.SUBJECT);

    test = test_service.get_by_course_or_subject_id(test);

    if (test == null)
      return;

    this.test = test;
    this.test_result.set_test(test);

    subject_name_label.setText(subject.get_name());
    set_question();
    set_actions();
    set_timer();
  }

  public void hydrate(Course course) {

    Test test = new Test();
    test.set_course(course);
    test.set_type(Test.Type.COURSE);

    test = test_service.get_by_course_or_subject_id(test);

    if (test == null)
      return;

    this.test = test;
    this.test_result.set_test(test);

    subject_name_label.setText(course.get_name());
    set_question();
    set_actions();
    set_timer();
  }

  @FXML
  void on_finish_button_pressed(ActionEvent event) {
    if (!validate_answer())
      return;

    timeline.stop();
    validate_test();
  }

  @FXML
  void on_next_question_button_pressed(ActionEvent event) {
    if (!validate_answer())
      return;

    active_question_index++;
    set_question();
    set_actions();
  }

  private Boolean validate_answer() {
    Test_qs active_question = test.get_questions().get(active_question_index);
    Integer selected_option_index = answer_group.getToggles().indexOf(answer_group.getSelectedToggle());

    if (selected_option_index.equals(-1))
      return false;

    List<String> options = Arrays.asList(active_question.get_optionA(),
        active_question.get_optionB(),
        active_question.get_optionC(),
        active_question.get_optionD());

    String selected_option = options.get(selected_option_index);

    if (!active_question.get_correct_option().equals(selected_option))
      return true;

    test_result.set_mark(test_result.get_mark() + 1);

    return true;
  }

  private void validate_test() {

    if (test_ended)
      return;

    test_ended = true;

    Boolean is_success = true;
    if (test_result.get_mark() < test.get_min_points())
      is_success = false;

    final Boolean is_success_passed = is_success;
    Router.render_dialog("Test_result",
        (Test_result_controller controller) -> controller.hydrate(test_result, is_success_passed));

    if (is_success)
      test_result_service.add(test_result);

  }

  private void set_question() {
    Test_qs active_question = test.get_questions().get(active_question_index);
    String active_question_label_string = String.format("%s / %s", active_question_index + 1,
        test.get_questions().size());

    List<String> options = Arrays.asList(active_question.get_optionA(),
        active_question.get_optionB(),
        active_question.get_optionC(),
        active_question.get_optionD());

    active_question_label.setText(active_question_label_string);
    question_label.setText(active_question.get_question());

    Integer option_index = 0;
    for (String option : options) {
      ((RadioButton) answer_group.getToggles().get(option_index)).setText(option);
      option_index++;
    }

    answer_group.getSelectedToggle().setSelected(false);
  }

  private void set_actions() {
    if (active_question_index >= test.get_questions().size() - 1) {
      actions_wrapper.getChildren().clear();
      actions_wrapper.getChildren().add(finish_button);
      return;
    }

    actions_wrapper.getChildren().clear();
    actions_wrapper.getChildren().add(next_question_button);
  }

  private void set_timer() {
    minutes_label.setText(Integer.toString(test.get_duration() - 1));
    seconds_label.setText("59");

    this.timeline = new Timeline();
    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
      Integer curr_seconds = Integer.parseInt(seconds_label.getText());
      Integer curr_minutes = Integer.parseInt(minutes_label.getText());

      if (curr_minutes.equals(0) && curr_seconds.equals(0)) {
        timeline.pause();
        validate_test();
        return;
      }

      if (curr_seconds.equals(0)) {
        curr_seconds = 59;
        curr_minutes--;
      } else
        curr_seconds--;

      Pair<Integer, Integer> critical_time = test.get_critical_time();

      if (curr_minutes < critical_time.getKey()
          || (curr_minutes.equals(critical_time.getKey()) && curr_seconds < critical_time.getValue())) {
        minutes_label.setTextFill(Paint.valueOf("E54242"));
        seconds_label.setTextFill(Paint.valueOf("E54242"));
      }

      seconds_label.setText(String.format("%02d", curr_seconds));
      minutes_label.setText(String.format("%02d", curr_minutes));
    }));

    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();

  }

}
