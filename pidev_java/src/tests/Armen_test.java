package tests;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import entities.Course;
import entities.Session;
import entities.User;
import entities.User_session;
import javafx.application.Application;
import javafx.stage.Stage;
import pages.course.Course_controller;
import pages.login.Login_controller;
import pages.profile.Profile_controller;
import pages.session.Session_controller;
import pages.signup.Signup_controller;
import dialogs.session_input.Session_input_controller;
import services.Course_service;
import services.Session_service;
import services.User_service;
import services.User_session_service;
import utils.Log;
import utils.Router;

public class Armen_test extends Application {
  static Session_service session_service = new Session_service();
  static Course_service course_service = new Course_service();
  static User_service user_service = new User_service();
  static User_session_service user_session_service = new User_session_service();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage main_stage) {

    Router.init(main_stage);
    // Router.render_user_template("Profile", (Profile_controller controller) -> {
    //   User user = new User();
    //   user.set_id(16);
    //   user = user_service.login(user);
    //   controller.hydrate(user);
    // });
    Router.render_user_template("Login", null);
    // Router.render_user_template("Signup", null);

  }

  private static void test() {
    Course course = new Course();
    course.set_id_c(79);

    Log.console(session_service.find_by_id_course(course));

  }

  private static void insert_test_date() {

    LocalDate date_1 = LocalDate.of(2023, 2, 23);
    LocalTime time_1 = LocalTime.of(12, 00, 00);

    User user = new User();
    user.set_id(1);

    Course course = new Course();
    course.set_id_c(1);

    Session session = new Session(12.5, date_1, time_1, time_1, "topcis");
    session.set_course(course);
    session.set_user(user);

    Log.console(session_service.add(session));

  }
}
