package pages.course;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import components.grid.Grid_controller;
import components.session_card.Session_card_controller;
import dialogs.session_input.Session_input_controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.Session_service;
import services.User_session_service;
import utils.Log;
import utils.Router;
import entities.Course;
import entities.Session;
import entities.User;

import java.time.LocalDate;
import java.util.List;

public class Course_controller {
  private static Session_service session_service = new Session_service();
  private static User_session_service user_session_service = new User_session_service();

  @FXML
  private TextField creator_name_input;

  @FXML
  private DatePicker date_filter_input;

  @FXML
  private MenuButton filter_by_button;

  @FXML
  private HBox result_controls_wrapper;

  @FXML
  private VBox sessions_section_wrapper;

  @FXML
  private MenuItem sort_by_price_button;

  @FXML
  private MenuItem sort_by_time_button;

  @FXML
  private MenuButton sort_selector;

  @FXML
  private ScrollPane scroll_pane;

  Course course;
  List<Session> sessions;
  List<Session> filtered_sessions;
  GridPane sessions_grid;

  public void hydrate(Course course) {
    this.course = course;
    this.sessions = session_service.find_by_id_course(course);
    this.filtered_sessions = sessions;
    this.sessions_grid = new GridPane();

    set_session_grid();

    result_controls_wrapper.getChildren().remove(date_filter_input);
    result_controls_wrapper.getChildren().remove(creator_name_input);

  }

  private void set_session_grid() {
    List<Parent> session_cards = filtered_sessions.stream().map(session -> {
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
    filtered_sessions.sort((a, b) -> {
      Integer date_comparison = a.get_date_localDate().compareTo(b.get_date_localDate());
      if (date_comparison == 0)
        return a.get_start_time_localTime().compareTo(b.get_start_time_localTime());
      return date_comparison;
    });
    set_session_grid();
  }

  @FXML
  void on_sort_by_price_button_pressed(ActionEvent event) {
    sort_selector.setText("Trier par : Prix");
    filtered_sessions.sort((a, b) -> {
      return a.get_price().compareTo(b.get_price());
    });

    set_session_grid();
  }

  @FXML
  void on_no_filter_button_pressed(ActionEvent event) {
    filter_by_button.setText("Chercher par : ");
    filtered_sessions = sessions;

    result_controls_wrapper.getChildren().remove(date_filter_input);
    result_controls_wrapper.getChildren().remove(creator_name_input);
    set_session_grid();
  }

  @FXML
  void on_creator_name_filter_button_pressed(ActionEvent event) {
    filter_by_button.setText("Chercher par : Nom Tuteur");

    result_controls_wrapper.getChildren().add(creator_name_input);
    result_controls_wrapper.getChildren().remove(date_filter_input);
  }

  @FXML
  void on_creator_name_input_change(ActionEvent event) {
    String name = creator_name_input.getText().toLowerCase();
    filtered_sessions = sessions.stream().filter(session -> {
      if (session.get_user().get_first_name().startsWith(name))
        return true;

      if (session.get_user().get_last_name().startsWith(name))
        return true;

      return false;
    }).collect(Collectors.toList());

    set_session_grid();
  }

  @FXML
  void on_date_filter_button_pressed(ActionEvent event) {
    filter_by_button.setText("Chercher par : Date");

    result_controls_wrapper.getChildren().add(date_filter_input);
    result_controls_wrapper.getChildren().remove(creator_name_input);
  }

  @FXML
  void on_date_filter_input_button_pressed(ActionEvent event) {
    LocalDate filter_date = date_filter_input.getValue();
    Log.console(filter_date);
    filtered_sessions = sessions.stream().filter(session -> session.get_date_localDate().equals(filter_date))
        .collect(Collectors.toList());
    set_session_grid();
  }

  @FXML
  void on_create_session_button_pressed(ActionEvent event) {
    User user = user_session_service.get_user();
    if (user == null)
      return;

    Router.render_dialog("Session_input", (Session_input_controller controller) -> {
      controller.hydrate((Session session) -> {
        session.set_course(course);
        session.set_user(user);

        Session new_session = session_service.add(session);
        if (new_session == null)
          return;

        sessions.add(new_session);
        set_session_grid();
      });
    });
  }

}
