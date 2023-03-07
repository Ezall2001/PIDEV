package templates.admin_template;

import java.net.URL;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import services.User_service;
import services.User_session_service;
import utils.Router;

public class Admin_template_controller implements Initializable {
  @FXML
  private Button login_button;

  @FXML
  private HBox login_logout_wrapper;

  @FXML
  private Button logout_button;

  @FXML
  private HBox nav_wrapper;

  private static User_session_service user_session_service = new User_session_service();
  private static User_service user_service = new User_service();
  User user;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    User user = user_session_service.get_user();
    this.user = user;

    if (user == null)
      login_logout_wrapper.getChildren().remove(logout_button);
    else
      login_logout_wrapper.getChildren().remove(login_button);
  }

  @FXML
  void on_logout_button_pressed(ActionEvent event) {
    user_service.logout();
    Router.render_user_template("Login", null);
  }

  @FXML
  void on_login_button_pressed(ActionEvent event) {
    Router.render_user_template("Login", null);
  }

  @FXML
  void on_courses_nav_button_pressed(MouseEvent event) {

  }

  @FXML
  void on_dashboard_nav_button_pressed(MouseEvent event) {

  }

  @FXML
  void on_questions_nav_button_pressed(MouseEvent event) {

  }

  @FXML
  void on_students_nav_button_pressed(MouseEvent event) {

  }

  @FXML
  void on_subjects_nav_button_pressed(MouseEvent event) {

  }

  @FXML
  void on_tests_nav_button_pressed(MouseEvent event) {

  }
}
