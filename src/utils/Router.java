package utils;

import java.util.function.Consumer;

import com.gluonhq.charm.glisten.mvc.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import templates.user_template.User_template_controller;

public class Router {

  private static Integer pref_width = 1440;
  private static Integer pref_height = 810;
  private static Stage main_stage;

  public static void init(Stage stage) {
    main_stage = stage;
  }

  public static <T> void render_user_template(String page_name, Consumer<T> controller_manipulator) {
    try {
      String template_path = "/templates/user_template/User_template.fxml";
      FXMLLoader template_loader = new FXMLLoader(Router.class.getResource(template_path));
      View template_vue = template_loader.load();

      template_vue.setCenter(load_page(page_name, controller_manipulator).getCenter());

      User_template_controller template_controller = template_loader.getController();
      template_controller.set_active_nav_item(page_name);
      update_scene(template_vue, page_name);

    } catch (Exception e) {
      Log.file(e);
    }
  }

  public static void render_admin_template(String page_name) {
    try {

    } catch (Exception e) {
      Log.file(e);
    }
  }

  private static <T> View load_page(String page_name, Consumer<T> controller_manipulator) {
    try {
      String page_path = String.format("/pages/%s/%s.fxml", page_name.toLowerCase(), page_name);
      FXMLLoader page_loader = new FXMLLoader(Router.class.getResource(page_path));
      View page_vue = page_loader.load();

      if (controller_manipulator != null)
        controller_manipulator.accept(page_loader.getController());

      return page_vue;

    } catch (Exception e) {
      Log.file(e);
      return null;
    }

  }

  private static void update_scene(View view, String page_name) {
    main_stage.setTitle(String.format("Myalò : %s", page_name));
    main_stage.setScene(new Scene(view, pref_width, pref_height));
    main_stage.show();
  }
}
