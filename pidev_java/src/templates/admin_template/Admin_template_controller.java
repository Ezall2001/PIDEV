package templates.admin_template;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import services.User_service;
import services.User_session_service;
import utils.Log;
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
  Integer active_nav_item_index;

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
    Router.render_admin_template("Courses_table", null);
  }

  @FXML
  void on_dashboard_nav_button_pressed(MouseEvent event) {
    Router.render_admin_template("Dashboard", null);
  }

  @FXML
  void on_questions_nav_button_pressed(MouseEvent event) {
    Router.render_admin_template("Questions_table", null);
  }

  @FXML
  void on_students_nav_button_pressed(MouseEvent event) {
    Router.render_admin_template("Users_table", null);
  }

  @FXML
  void on_subjects_nav_button_pressed(MouseEvent event) {
    Router.render_admin_template("Subjects_table", null);
  }

  @FXML
  void on_tests_nav_button_pressed(MouseEvent event) {
    Router.render_admin_template("Tests_table", null);
  }

  public void set_active_nav_item(String page_name) {

    Optional<Node> found_nav_item = nav_wrapper.getChildren()
        .stream()
        .filter(nav_item -> ((Label) nav_item).getText().equals(page_name))
        .findFirst();

    if (!found_nav_item.isPresent()) {
      Log.file(String.format("%s don't activate any nav item", page_name));
      return;
    }

    Label active_nav_item = (Label) found_nav_item.get();
    active_nav_item.setUnderline(true);
    Font nav_item_font = active_nav_item.getFont();
    active_nav_item.setFont(Font.font(nav_item_font.getFamily(), FontWeight.SEMI_BOLD, nav_item_font.getSize()));
  }
}
