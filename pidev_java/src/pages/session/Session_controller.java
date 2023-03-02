package pages.session;

import java.net.URL;
import java.util.ResourceBundle;

import dialogs.session_input.Session_input_controller;
import entities.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import services.Session_service;
import services.User_service;
import utils.Log;
import utils.Router;

public class Session_controller {

  @FXML
  private Label course_label;

  @FXML
  private Label date_label;

  @FXML
  private Button delete_button;

  @FXML
  private Button modify_button;

  @FXML
  private Button participate_button;

  @FXML
  private Label price_label;

  @FXML
  private Label topics_label;

  @FXML
  private Label user_label;

  @FXML
  private Label time_label;

  private Session session;

  private static Session_service session_service = new Session_service();

  public void hydrate(Session session) {
    this.session = session;
    user_label.setText(session.get_user().get_full_name());
    course_label.setText(session.get_course().get_course_name());
    date_label.setText(session.get_date_string());
    price_label.setText(session.get_price_string());
    time_label.setText(session.get_time_interval());
    topics_label.setText(session.get_topics());
  }

  @FXML
  void on_delete_button_pressed(ActionEvent event) {
    session_service.delete_by_id(session);
    Router.render_user_template("Profile", null);
  }

  @FXML
  void on_modify_button_pressed(ActionEvent event) {
    Router.render_dialog("Session_input",
        (Session_input_controller controller) -> {
          controller.hydrate(session);
        });
  }

  @FXML
  void on_participate_button_pressed(ActionEvent event) {

  }

}