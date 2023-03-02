package pages.course;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import components.grid.Grid_controller;
import components.session_card.Session_card_controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.Session_service;
import utils.Log;
import utils.Router;
import entities.Course;
import entities.Session;
import java.util.List;

public class Course_controller {
  private static Session_service session_service = new Session_service();

  @FXML
  private TextField creator_name_input;

  @FXML
  private DatePicker date_filter_input;

  @FXML
  private MenuButton filter_by_button;

  @FXML
  private MenuButton sort_selector;
  @FXML
  private MenuItem sort_by_time_button;

  @FXML
  private HBox result_controls_wrapper;

  @FXML
  private HBox result_controls_wrapper1;

  @FXML
  private VBox sessions_section_wrapper;

  Course course;
  List<Session> sessions;
  GridPane sessions_grid;

  public void hydrate(Course course) {
    this.course = course;
    this.sessions = session_service.find_by_id_course(course);
    this.sessions_grid = new GridPane();

    set_session_grid();

  }

  private void set_session_grid() {
    List<Parent> session_cards = sessions.stream().map(session -> {
      return Router.load_component("Session_card", (Session_card_controller controller) -> controller.hydrate(session));
    }).collect(Collectors.toList());

    sessions_section_wrapper.getChildren().remove(sessions_grid);

    sessions_grid = (GridPane) Router.load_component("Grid",
        (Grid_controller controller) -> controller.hydrate(session_cards, 300, 300, 20, 30));

    sessions_section_wrapper.getChildren().add(sessions_grid);
  }

  @FXML
  void on_sort_by_time_button_pressed(ActionEvent event) {
    sort_selector.setText("Trier par : Temps");
    this.sessions.sort((a, b) -> {
      return a.get_start_time_localTime().compareTo(b.get_start_time_localTime());
    });

  }

  @FXML
  void on_sort_by_price_button_pressed(ActionEvent event) {
    sort_selector.setText("Trier par : Prix");
    this.sessions.sort((a, b) -> {
      return a.get_price().compareTo(b.get_price());
    });

  }

}
