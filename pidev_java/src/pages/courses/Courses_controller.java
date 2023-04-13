package pages.courses;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import components.course_card.Course_card_controller;
import components.grid.Grid_controller;
import entities.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import services.Course_service;
import utils.Router;

public class Courses_controller implements Initializable {

  @FXML
  private VBox courses_wrapper;

  @FXML
  private MenuItem sort_by_difficulty_button;

  @FXML
  private MenuItem sort_by_subjects_button;

  private static Course_service course_service = new Course_service();
  private List<Course> courses;
  private List<Course> sorted_courses;
  private GridPane courses_grid;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    courses = course_service.get_all();
    sorted_courses = new ArrayList<>(courses);
    courses_grid = new GridPane();
    set_courses_grid();
  }

  @FXML
  void on_sort_by_difficulty_button_pressed(ActionEvent event) {
    sorted_courses = courses
        .stream()
        .sorted((a, b) -> a.compare_difficulty(b.get_difficulty()))
        .collect(Collectors.toList());

    set_courses_grid();
  }

  @FXML
  void on_sort_by_subjects_button_pressed(ActionEvent event) {
    sorted_courses = courses
        .stream()
        .sorted((a, b) -> a.get_subject().get_name().compareTo(b.get_subject().get_name()))
        .collect(Collectors.toList());

    set_courses_grid();
  }

  private void set_courses_grid() {
    List<Parent> course_cards = sorted_courses.stream()
        .map(course -> Router.load_component("Course_card",
            (Course_card_controller controller) -> controller.hydrate(course)))
        .collect(Collectors.toList());

    courses_wrapper.getChildren().remove(courses_grid);

    courses_grid = (GridPane) Router.load_component("Grid",
        (Grid_controller controller) -> controller.hydrate(course_cards, 400, 350, 60, 40));

    courses_wrapper.getChildren().add(courses_grid);
  }
}
