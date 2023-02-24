package templates.user_template;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utils.Log;

public class User_template_controller implements Initializable {

  @FXML
  private HBox nav_wrapper;

  @FXML
  private Button logout_button;

  @FXML
  private HBox login_logout_wrapper;

  private static List<String> home_nav_item_activators = Arrays.asList("Profile");
  private static List<String> subject_nav_item_activators = Arrays.asList("Subject", "Subjects", "Course", "Session");
  private static List<String> forum_nav_item_activators = Arrays.asList("Forum", "Forum_thread");

  public User_template_controller() {
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ///TODO: implement a check for user loggged in
    login_logout_wrapper.getChildren().remove(logout_button);
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
