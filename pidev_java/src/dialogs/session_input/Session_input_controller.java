package dialogs.session_input;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import entities.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pages.session.Session_controller;
import utils.Log;
import utils.Router;

public class Session_input_controller {

  @FXML
  private Label date_error_label;

  @FXML
  private DatePicker date_input;

  @FXML
  private Label end_time_error_label;

  @FXML
  private TextField end_time_input;

  @FXML
  private Label price_error_label;

  @FXML
  private TextField price_input;

  @FXML
  private Label start_time_error_label;

  @FXML
  private TextField start_time_input;

  @FXML
  private Label topics_error_input;

  @FXML
  private TextArea topics_input;

  @FXML
  private HBox button_wrapper;

  Session session;

  public void hydrate() {
    button_wrapper.getChildren().remove(1);
  }

  public void hydrate(Session session) {
    this.session = session;
    button_wrapper.getChildren().remove(0);
    date_input.setValue(session.get_date_localDate());
    start_time_input.setText(session.get_start_time_string());
    end_time_input.setText(session.get_end_time_string());
    price_input.setText(session.get_price().toString());
    topics_input.setText(session.get_topics());
  }

  @FXML
  void on_add_button_pressed(ActionEvent event) {

  }

  @FXML
  void on_modify_button_pressed(ActionEvent event) {
    if (!validate_input())
      return;

    Session new_session = new Session();
    new_session.set_id(session.get_id());
    new_session.set_course(session.get_course());
    new_session.set_user(session.get_user());
    new_session.set_date(date_input.getValue());
    new_session.set_start_time(LocalTime.parse(start_time_input.getText()));
    new_session.set_end_time(LocalTime.parse(end_time_input.getText()));
    new_session.set_price(Double.parseDouble(price_input.getText()));
    new_session.set_topics(topics_input.getText());

    Router.render_user_template("Session",
        (Session_controller controller) -> controller.hydrate(new_session));
  }

  private Boolean validate_input() {
    ///TODO: validate in entity
    Boolean is_valid = true;

    start_time_error_label.setText("");
    end_time_error_label.setText("");
    price_error_label.setText("");

    try {
      if (start_time_input.getText().isEmpty())
        throw new NullPointerException();
      LocalTime.parse(start_time_input.getText());
    } catch (NullPointerException e) {
      start_time_error_label.setText("champ vide");
      is_valid = false;

    } catch (DateTimeParseException e) {
      start_time_error_label.setText("heure doit être de la forme hh:mm");
      is_valid = false;
    }

    try {
      if (end_time_input.getText().isEmpty())
        throw new NullPointerException();
      LocalTime.parse(end_time_input.getText());
    } catch (NullPointerException e) {
      end_time_error_label.setText("champ vide");
      is_valid = false;

    } catch (DateTimeParseException e) {
      end_time_error_label.setText("heure doit être de la forme hh:mm");
      is_valid = false;
    }

    try {
      if (price_input.getText().isEmpty())
        throw new NullPointerException();
      Double.parseDouble(price_input.getText());

    } catch (NullPointerException e) {
      price_error_label.setText("champ vide");
      is_valid = false;
    } catch (NumberFormatException e) {
      price_error_label.setText("champ invalide");
      is_valid = false;
    }

    return is_valid;
  }
}
