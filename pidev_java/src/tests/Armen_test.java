package tests;

import java.util.List;

import entities.Course;
import entities.Session;
import javafx.application.Application;
import javafx.stage.Stage;
import pages.session.Session_controller;
import dialogs.session_input.Session_input_controller;
import services.Course_service;
import services.Session_service;
import utils.Log;
import utils.Router;

public class Armen_test extends Application {
  Session_service session_service = new Session_service();
  Course_service course_service = new Course_service();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage main_stage) {
    List<Course> courses = course_service.get_all();
    Course course = courses.get(0);
    List<Session> sessions = session_service.find_by_id_course(course.get_id());
    Session session = sessions.get(0);

    Router.init(main_stage);
    Router.render_user_template("Session",
        (Session_controller controller) -> controller.hydrate(
            session,
            course.get_name()));

  }

}
