package tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Test;
import entities.Test_qs;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.Course_service;
import services.Participation_service;
import services.Subject_service;
import services.Test_qs_service;
import services.Test_service;
import services.User_service;
import utils.Log;
import utils.Router;

public class Armen_test extends Application {

  static private Test_service test_service = new Test_service();
  static private Course_service course_service = new Course_service();
  static private Subject_service subject_service = new Subject_service();
  static private User_service user_service = new User_service();
  static private Participation_service participation_service = new Participation_service();
  static private Test_qs_service test_qs_service = new Test_qs_service();

  public static void main(String[] args) {

    List<Test> tests = test_service.get_all();
    // tests.forEach(Log::file);

    launch(args);
  }

  @Override
  public void start(Stage main_stage) {
    Router.init(main_stage);
    Router.render_admin_template("Users_table", null);
    // Router.render_user_template("Profile", null);
    // Router.render_user_template("Courses_table", null);
  }

}
