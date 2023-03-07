package templates.user_template;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class User_template_controller implements Initializable {

  private static User_session_service user_session_service = new User_session_service();
  private static User_service user_service = new User_service();
  User user;

  @FXML
  private HBox nav_wrapper;

  @FXML
  private Button logout_button;

  @FXML
  private Button login_button;

  @FXML
  private HBox login_logout_wrapper;

  private static List<String> home_nav_item_activators = Arrays.asList("Profile", "Login", "Signup");
  private static List<String> subject_nav_item_activators = Arrays.asList("Courses", "Course", "Session", "Test");
  private static List<String> forum_nav_item_activators = Arrays.asList("Forum", "Forum_thread");

  public User_template_controller() {
  }

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
  void on_forum_nav_button_pressed(MouseEvent event) {
    Router.render_user_template("Forum", null);
  }

  @FXML
  void on_profile_nav_button_pressed(MouseEvent event) {
    Router.render_user_template("Profile", null);
  }

  @FXML
  void on_subjects_nav_button_pressed(MouseEvent event) {
    Router.render_user_template("Courses", null);
  }

  public void set_active_nav_item(String page_name) {
    Integer active_nav_item_index = 0;

    List<List<String>> nav_items_activators = Arrays.asList(home_nav_item_activators, subject_nav_item_activators,
        forum_nav_item_activators);

    for (List<String> nav_item_activators : nav_items_activators) {
      if (nav_item_activators.stream().anyMatch(activator -> activator.equals(page_name)))
        break;
      active_nav_item_index++;
    }

    try {
      if (active_nav_item_index >= nav_items_activators.size())
        throw new Exception(String.format("%s page does not active any nav item", page_name));

      Label nav_item = (Label) nav_wrapper.getChildren().get(active_nav_item_index);
      nav_item.setUnderline(true);
      Font nav_item_font = nav_item.getFont();
      nav_item.setFont(Font.font(nav_item_font.getFamily(), FontWeight.SEMI_BOLD, nav_item_font.getSize()));

    } catch (Exception e) {
      Log.file(e.getMessage());
    }

  }

}
