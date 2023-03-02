package pages.profile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import dialogs.profile_input.Profile_input_controller;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import services.User_session_service;
import utils.Router;
import utils.String_helpers;

public class Profile_controller implements Initializable {

  private static Integer max_width_bar = 200;
  private static User_session_service user_session_service = new User_session_service();

  User user;

  @FXML
  private Label age_label;

  @FXML
  private ImageView avatar_image;

  @FXML
  private Label bio_label;

  @FXML
  private Label full_name_label;

  @FXML
  private Label level_label;

  @FXML
  private Text modify_profile_button;

  @FXML
  private Rectangle score_bar;

  @FXML
  private Label score_label;

  @FXML
  void on_modify_profile_button_pressed(MouseEvent event) {
    Router.render_dialog("Profile_input", (Profile_input_controller controller) -> {
      controller.hydrate(user);
    });

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    User user = user_session_service.get_user();
    if (user == null)
      return;

    hydrate(user);
  }

  public void hydrate(User user) {
    this.user = user;
    age_label.setText(user.get_age().toString());
    full_name_label.setText(user.get_full_name());
    bio_label.setText(user.get_bio());
    level_label.setText(String_helpers.capitalize(user.get_level()));
    score_label.setText(String.format("%s / %s", user.compute_current_level_score(),
        User.compute_level_breakpoint_score(user.compute_level())));

    Log.console(user);
    set_image();
    set_score_bar();
  }

  void set_image() {

    if (user.get_avatar_path() == null)
      return;

    try {
      InputStream stream = new FileInputStream(user.get_avatar_path());
      Image image = new Image(stream);
      avatar_image.setImage(image);
    } catch (Exception e) {
      e.getMessage();
    }

  }

  void set_score_bar() {
    Integer current_score = user.compute_current_level_score();
    Integer current_breakpoint_score = User.compute_level_breakpoint_score(user.compute_level());
    Double bar_width = (current_score.doubleValue() / current_breakpoint_score) * max_width_bar;
    score_bar.setWidth(bar_width);
  }

}
