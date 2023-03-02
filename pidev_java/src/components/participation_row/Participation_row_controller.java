package components.participation_row;

import entities.Participation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import services.Participation_service;
import utils.String_helpers;

public class Participation_row_controller {

  @FXML
  private Label email_label;

  @FXML
  private Label full_name_label;

  @FXML
  private Label level_label;

  @FXML
  private Label status_label;

  static Participation_service participation_service = new Participation_service();
  private Participation participation;

  public void hydrate(Participation participation) {
    this.participation = participation;

    email_label.setText(participation.get_user().get_email());
    full_name_label.setText(participation.get_user().get_full_name());
    level_label.setText(String_helpers.capitalize(participation.get_user().get_level()));
    set_status();
  }

  void set_status() {
    status_label.setText(String_helpers.capitalize(participation.get_state_string()));

    if (participation.get_state() == Participation.State.PENDING)
      status_label.setStyle("-fx-text-fill: #051148;");
    if (participation.get_state() == Participation.State.ACCEPTED)
      status_label.setStyle("-fx-text-fill: #5ce079;");
    if (participation.get_state() == Participation.State.DENIED)
      status_label.setStyle("-fx-text-fill: #e94f4f;");

  }

  @FXML
  void on_accept_button_pressed(MouseEvent event) {
    participation.set_state(Participation.State.ACCEPTED);
    participation_service.update(participation);
    set_status();
  }

  @FXML
  void on_deny_button_pressed(MouseEvent event) {
    participation.set_state(Participation.State.DENIED);
    participation_service.update(participation);
    set_status();
  }

}
