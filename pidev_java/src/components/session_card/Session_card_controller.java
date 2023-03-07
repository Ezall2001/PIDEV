package components.session_card;

import entities.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pages.session.Session_controller;
import utils.Log;
import utils.Router;

public class Session_card_controller {

  @FXML
  private Label creator_name_label;

  @FXML
  private Label date_label;

  @FXML
  private Label price_label;

  @FXML
  private Label time_interval_label;

  @FXML
  private Label topics_label;

  Session session;

  @FXML
  void on_details_button_pressed(ActionEvent event) {
    Router.render_user_template("Session", (Session_controller controller) -> {
      controller.hydrate(session);
    });
  }

  public void hydrate(Session session) {
    this.session = session;
    creator_name_label.setText(session.get_user().get_full_name());
    date_label.setText(session.get_date_string());
    time_interval_label.setText(session.get_time_interval());
    price_label.setText(session.get_price_string());
    topics_label.setText(session.get_topics());
  }

}
