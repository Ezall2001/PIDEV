package pages.courses_table;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dialogs.course_input.Course_input_controller;
import entities.Course;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import services.Course_service;
import utils.Router;
import utils.Table_view_helpers;

public class Courses_table_controller implements Initializable {

  static private Course_service course_service = new Course_service();

  @FXML
  private VBox table_wrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    TableView<Course> table = new TableView<>();

    TableColumn<Course, String> name_column = new TableColumn<>("Name");
    name_column.setCellValueFactory(new PropertyValueFactory<Course, String>("_name"));

    TableColumn<Course, String> description_column = new TableColumn<>("Description");
    description_column.setCellValueFactory(new PropertyValueFactory<Course, String>("_description"));
    description_column = Table_view_helpers.set_text_wrap_cell(description_column);
    description_column.setMinWidth(500);

    TableColumn<Course, String> difficulty_column = new TableColumn<>("Difficulty");
    difficulty_column.setCellValueFactory(new PropertyValueFactory<Course, String>("_difficulty"));

    table.getColumns().add(name_column);
    table.getColumns().add(description_column);
    table.getColumns().add(difficulty_column);

    table = Table_view_helpers.add_action_column(table,
        (Course course) -> {
          Router.render_dialog("Course_input", (Course_input_controller controller) -> controller.hydrate(course));
        },
        (Course course) -> {
          if (!Table_view_helpers.delete_confirmation())
            return;

          course_service.delete(course);
          Router.render_dialog("Courses_table", null);
        });

    List<Course> courses = course_service.get_all();
    table.getItems().addAll(courses);

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table = Table_view_helpers.set_style(table);

    table_wrapper.getChildren().add(table);

  }

}
