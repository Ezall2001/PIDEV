package tests;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import entities.Course;
import entities.Participation;
import entities.Session;
import entities.User;
import entities.User_session;
import javafx.application.Application;
import javafx.stage.Stage;
import pages.course.Course_controller;
import pages.profile.Profile_controller;
import pages.session.Session_controller;
import dialogs.session_input.Session_input_controller;
import services.Course_service;
import services.Participation_service;
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
  static Participation_service participation_service = new Participation_service();

  static String lorem = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

  public static void main(String[] args) {
    // insert_sessions_data();
    // insert_resources_data();
    launch(args);
  }

  @Override
  public void start(Stage main_stage) {

    Router.init(main_stage);
    Router.render_user_template("Profile", null);

  }

  private static void insert_resources_data() {
    ///TODO: complete this
  }

  private static void insert_sessions_data() {

    LocalDate date_1 = LocalDate.of(2023, 2, 23);
    LocalDate date_2 = LocalDate.of(2023, 2, 24);

    LocalTime time_1 = LocalTime.of(12, 00, 00);
    LocalTime time_2 = LocalTime.of(14, 00, 00);
    LocalTime time_3 = LocalTime.of(13, 00, 00);
    LocalTime time_4 = LocalTime.of(15, 00, 00);

    User user_1 = new User();
    user_1.set_id(17);

    User user_2 = new User();
    user_2.set_id(16);

    Course course = new Course();
    course.set_id_c(79);

    Session session_1 = new Session(12.5, date_1, time_1, time_2, lorem);
    session_1.set_course(course);
    session_1.set_user(user_1);

    Session session_2 = new Session(16.5, date_1, time_3, time_4, lorem);
    session_2.set_course(course);
    session_2.set_user(user_2);

    Session session_3 = new Session(12.5, date_2, time_1, time_2, lorem);
    session_3.set_course(course);
    session_3.set_user(user_1);

    Session session_4 = new Session(12.5, date_2, time_3, time_4, lorem);
    session_4.set_course(course);
    session_4.set_user(user_2);

    session_service.add(session_1);
    session_service.add(session_2);
    session_service.add(session_3);
    session_service.add(session_4);

  }
}
