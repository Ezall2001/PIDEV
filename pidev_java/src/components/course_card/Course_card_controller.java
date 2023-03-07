package components.course_card;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import pages.course.Course_controller;
import utils.Router;

public class Course_card_controller implements Initializable {

  @FXML
  private Rectangle base_layer;

  @FXML
  private Label classes_label;

  @FXML
  private Label course_name_label;

  @FXML
  private Label description_label;

  @FXML
  private Label subject_name_label;

  @FXML
  private HBox difficullty_meter;

  @FXML
  private StackPane difficulty_wrapper;

  @FXML
  private Rectangle easy_difficulty;

  @FXML
  private Rectangle hard_difficulty;

  @FXML
  private Rectangle medium_difficulty;

  private Course course;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    Rectangle clip = new Rectangle();
    clip.setArcWidth(15);
    clip.setArcHeight(15);
    clip.setWidth(92);
    clip.setHeight(17);
    clip.setLayoutX(1);
    clip.setLayoutY(0);

    difficulty_wrapper.setClip(clip);

  }

  public void hydrate(Course course) {
    this.course = course;
    course_name_label.setText(course.get_name());
    classes_label.setText(course.get_subject().get_classes_esprit_string());
    description_label.setText(course.get_description());
    subject_name_label.setText(course.get_subject().get_name());

    set_difficulty_meter();

  }

  private void set_difficulty_meter() {

    difficullty_meter.getChildren().remove(easy_difficulty);
    difficullty_meter.getChildren().remove(medium_difficulty);
    difficullty_meter.getChildren().remove(hard_difficulty);

    if (course.get_difficulty() == Course.Difficulty.EASY) {
      difficullty_meter.getChildren().add(easy_difficulty);

    } else if (course.get_difficulty() == Course.Difficulty.MEDIUM) {
      difficullty_meter.getChildren().add(easy_difficulty);
      difficullty_meter.getChildren().add(medium_difficulty);

    } else if (course.get_difficulty() == Course.Difficulty.HARD) {
      difficullty_meter.getChildren().add(easy_difficulty);
      difficullty_meter.getChildren().add(medium_difficulty);
      difficullty_meter.getChildren().add(hard_difficulty);
    }

  }

  @FXML
  void on_explore_button_pressed(ActionEvent event) {
    Router.render_user_template("Course", (Course_controller controller) -> {
      controller.hydrate(course);
    });
  }

}
