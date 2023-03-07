package pages.courses;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import components.course_card.Course_card_controller;
import components.grid.Grid_controller;
import entities.Course;
import entities.Subject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import services.Course_service;
import services.Subject_service;
import utils.Router;

public class Courses_controller implements Initializable {

  @FXML
  private VBox courses_wrapper;

  private static Course_service course_service = new Course_service();
  private List<Course> courses;
  private GridPane courses_grid;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    courses = course_service.get_all();
    courses_grid = new GridPane();
    set_courses_grid();
  }

  private void set_courses_grid() {
    List<Parent> course_cards = courses.stream()
        .map(course -> Router.load_component("Course_card",
            (Course_card_controller controller) -> controller.hydrate(course)))
        .collect(Collectors.toList());

    courses_wrapper.getChildren().remove(courses_grid);

    courses_grid = (GridPane) Router.load_component("Grid",
        (Grid_controller controller) -> controller.hydrate(course_cards, 400, 350, 60, 40));

    courses_wrapper.getChildren().add(courses_grid);
  }
}
