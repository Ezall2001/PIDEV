package pages.session;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import components.grid.Grid_controller;
import components.participation_row.Participation_row_controller;
import components.resource.Resource_controller;
import dialogs.session_input.Session_input_controller;
import entities.Participation;
import entities.Resource;
import entities.Session;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.Participation_service;
import services.Resource_service;
import services.Session_service;
import services.User_session_service;
import utils.Log;
import utils.Router;

public class Session_controller implements Initializable {

  @FXML
  private HBox actions_wrapper;

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
  private Button state_confirmed_label;

  @FXML
  private Button state_denied_label;

  @FXML
  private Button state_pending_label;

  @FXML
  private Label price_label;

  @FXML
  private Label time_label;

  @FXML
  private Label topics_label;

  @FXML
  private Label user_label;

  @FXML
  private VBox resources_wrapper;

  @FXML
  private VBox participation_list;

  @FXML
  private VBox Participation_wrapper;

  @FXML
  private HBox link_wrapper;

  @FXML
  private TextField link_input;

  private User user;
  private Session session;
  private Participation participation;
  private GridPane resources_grid;

  private static Session_service session_service = new Session_service();
  private static User_session_service user_session_service = new User_session_service();
  private static Resource_service resource_service = new Resource_service();
  private static Participation_service participation_service = new Participation_service();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    User user = user_session_service.get_user();
    if (user == null)
      return;

    this.user = user;
  }

  public void hydrate(Session session) {
    List<Resource> resources = resource_service.find_by_id_session(session);
    session.set_resources(resources);

    Participation participation = new Participation();
    participation.set_session(session);
    participation.set_user(user);
    this.participation = participation_service.find_by_id_session_and_id_user(participation);

    this.session = session;
    user_label.setText(session.get_user().get_full_name());
    course_label.setText(session.get_course().get_course_name());
    date_label.setText(session.get_date_string());
    price_label.setText(session.get_price_string());
    time_label.setText(session.get_time_interval());
    topics_label.setText(session.get_topics());

    if (user.get_id() == session.get_user().get_id())
      actions_wrapper.getChildren().remove(participate_button);
    else {
      actions_wrapper.getChildren().remove(modify_button);
      actions_wrapper.getChildren().remove(delete_button);
      Participation_wrapper.getChildren().remove(0);
    }

    set_resources_grid();
    set_participation();
    set_participation_list();
  }

  void set_participation() {
    actions_wrapper.getChildren().remove(participate_button);
    actions_wrapper.getChildren().remove(state_confirmed_label);
    actions_wrapper.getChildren().remove(state_denied_label);
    actions_wrapper.getChildren().remove(state_pending_label);

    if (user.get_id() == session.get_user().get_id())
      return;

    if (participation == null)
      actions_wrapper.getChildren().add(participate_button);

    else if (participation.get_state() == Participation.State.PENDING)
      actions_wrapper.getChildren().add(state_pending_label);

    else if (participation.get_state() == Participation.State.ACCEPTED)
      actions_wrapper.getChildren().add(state_confirmed_label);

    else if (participation.get_state() == Participation.State.DENIED)
      actions_wrapper.getChildren().add(state_denied_label);

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
          controller.hydrate(session, new_session -> {
            session_service.update(new_session);
            hydrate(new_session);
          });
        });
  }

  @FXML
  void on_participate_button_pressed(ActionEvent event) {
    Participation participation = new Participation();
    participation.set_session(session);
    participation.set_user(user);

    participation = participation_service.add(participation);
    if (participation == null)
      return;

    this.participation = participation;
    set_participation();
  }

  @FXML
  void on_send_link_button_pressed(ActionEvent event) {
    String link = link_input.getText();
    ///TODO: send mails

  }

  void set_resources_grid() {
    List<Parent> resource_components = new ArrayList<>();
    Boolean is_creator = user.get_id() == session.get_user().get_id();
    if (is_creator)
      resource_components.add(Router.load_component("Resource", (Resource_controller controller) -> {
        controller.hydrate(session, (Resource resource) -> {
          if (resource != null)
            session.get_resources().add(resource);
          set_resources_grid();
        });
      }));

    for (Resource resource : session.get_resources()) {
      resource.set_session(session);
      resource_components.add(Router.load_component("Resource", (Resource_controller controller) -> {
        controller.hydrate(resource, is_creator,
            (Resource deleted_resource) -> {
              session.get_resources().remove(deleted_resource);
              set_resources_grid();
            });
      }));

    }
    resources_wrapper.getChildren().remove(resources_grid);

    resources_grid = (GridPane) Router.load_component("Grid",
        (Grid_controller controller) -> controller.hydrate(resource_components, 200, 250, 50, 30));

    resources_wrapper.getChildren().add(resources_grid);
  }

  void set_participation_list() {
    if (user.get_id() != session.get_user().get_id())
      return;

    List<Participation> participations = participation_service.find_by_id_session(session);

    for (Participation participation : participations) {
      HBox participation_row = (HBox) Router.load_component("Participation_row",
          (Participation_row_controller controller) -> {
            controller.hydrate(participation);
          });
      participation_list.getChildren().add(participation_row);
    }
  }

}