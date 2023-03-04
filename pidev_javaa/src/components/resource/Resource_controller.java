package components.resource;

import java.awt.Desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Consumer;

import dialogs.error.Error_controller;
import dialogs.file_chooser.File_chooser_controller;
import entities.Participation;
import entities.Resource;
import entities.Session;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import services.Participation_service;
import services.Resource_service;
import services.User_session_service;
import utils.File_helpers;
import utils.Log;
import utils.Router;

public class Resource_controller {

  @FXML
  private ImageView delete_button;

  @FXML
  private ImageView resource_image;

  @FXML
  private HBox resource_info_wrapper;

  @FXML
  private Label resource_name_label;

  private static String pdf_image_path = "public/image/pdf_media_icon.png";
  private static String add_file_image_path = "public/image/add_file.png";
  private static String base_file_image_path = "public/image/base_file.png";
  private static String user_files_directory_path = "server/user_files/";
  private static Resource_service resource_service = new Resource_service();
  private static User_session_service user_session_service = new User_session_service();
  private static Participation_service participation_service = new Participation_service();

  private Resource resource;
  private Session session;
  private Consumer<Resource> delete_callback;
  private Consumer<Resource> add_callback;
  private Boolean is_add_file;
  private Boolean is_creator;

  public void hydrate(Session session, Consumer<Resource> add_callback) {
    is_add_file = true;
    this.add_callback = add_callback;
    this.session = session;
    resource_name_label.setText("Ajouter Resource");
    resource_info_wrapper.getChildren().remove(delete_button);
    set_image();
  }

  public void hydrate(Resource resource, Boolean is_creator, Consumer<Resource> delete_callback) {
    this.resource = resource;
    this.delete_callback = delete_callback;
    this.is_creator = is_creator;
    this.is_add_file = false;

    resource_name_label.setText(resource.get_name());

    if (!is_creator)
      resource_info_wrapper.getChildren().remove(delete_button);

    set_image();
  }

  private void set_image() {
    try {
      String image_path = base_file_image_path;

      if (is_add_file)
        image_path = add_file_image_path;

      else if (resource.get_file_path().endsWith(".pdf"))
        image_path = pdf_image_path;

      InputStream stream = new FileInputStream(image_path);
      Image image = new Image(stream);
      resource_image.setImage(image);
    } catch (

    Exception e) {
      Log.file(e.getMessage());
    }
  }

  @FXML
  void on_delete_button_pressed(MouseEvent event) {
    resource_service.delete_by_id(resource);
    if (delete_callback != null)
      delete_callback.accept(resource);
  }

  @FXML
  void on_resource_image_pressed(MouseEvent event) {
    if (is_add_file)
      add_file();
    else
      open_file();
  }

  void add_file() {
    File_chooser_controller controller = new File_chooser_controller();
    controller.hydrate(src_file -> {
      File dest_file = new File(user_files_directory_path + src_file.getName());
      File_helpers.copy_file(src_file, dest_file);

      Resource new_resource = new Resource(dest_file.getName(), dest_file.getPath());
      new_resource.set_session(session);
      new_resource = resource_service.add(new_resource);

      add_callback.accept(new_resource);
    });

  }

  void open_file() {
    User user = user_session_service.get_user();
    Session session = resource.get_session();

    Participation participation = new Participation();
    participation.set_session(session);
    participation.set_user(user);

    participation = participation_service.find_by_id_session_and_id_user(participation);

    if ((participation == null || participation.get_state() != Participation.State.ACCEPTED) && !is_creator) {
      Router.render_dialog("Error",
          (Error_controller controller) -> {
            controller.hydrate("Vous n'êtes pas autorisé à consulter ce fichier.");
          });
      return;
    }

    try {
      File file = new File(resource.get_file_path());
      Desktop.getDesktop().open(file);
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

  }

}