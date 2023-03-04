package tests;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import entities.Class_esprit;
import entities.Course;
import entities.Session;
import entities.Subject;
import entities.User;
import services.Class_esprit_service;
import services.Course_service;
import services.Session_service;
import services.Subject_service;
import services.User_service;
import utils.Jdbc_connection;
import utils.Log;

public class Tester {
  static private Connection cnx = Jdbc_connection.getInstance();

  static private User_service user_service = new User_service();
  static private Class_esprit_service class_esprit_service = new Class_esprit_service();
  static private Subject_service subject_service = new Subject_service();
  static private Course_service course_service = new Course_service();
  static private Session_service session_service = new Session_service();

  static private String lorem = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.";

  public static void main(String[] args) {
    clear_db();
    // List<Class_esprit> class_esprit_list = insert_classes_esprit();
    // List<Subject> subjects = insert_subjects(class_esprit_list);
    // List<Course> courses = insert_courses(subjects);
    // List<User> users = insert_users(class_esprit_list);
    // List<Session> sessions = insert_sessions(courses, users);

  }

  public static void clear_db() {
    List<String> tables = Arrays.asList(
        "sessions", "courses", "subjects", "users", "class_esprit");

    tables.forEach(table -> {
      try {
        cnx.prepareStatement(String.format("delete from %s", table)).executeUpdate();
      } catch (Exception e) {
        Log.file(e.getMessage());
      }
    });
  }

  public static List<Class_esprit> insert_classes_esprit() {
    List<Class_esprit> class_esprit_list = new ArrayList<>();

    class_esprit_list.add(new Class_esprit("A1"));
    class_esprit_list.add(new Class_esprit("A2"));
    class_esprit_list.add(new Class_esprit("A3"));
    class_esprit_list.add(new Class_esprit("B1"));
    class_esprit_list.add(new Class_esprit("B2"));
    class_esprit_list.add(new Class_esprit("B3"));

    return class_esprit_list.stream().map(
        class_esprit -> class_esprit_service.add(class_esprit)).collect(Collectors.toList());

  }

  public static List<Subject> insert_subjects(List<Class_esprit> class_esprit_list) {
    List<Subject> subjects = new ArrayList<>();

    subjects.add(new Subject("C", lorem, Subject.Level., class_esprit_list.get(0)));
    subjects.add(new Subject("C++", lorem, Subject.Level.EASY, class_esprit_list.get(1)));
    subjects.add(new Subject("java", lorem, Subject.Level.HARD, class_esprit_list.get(2)));

    return subjects.stream().map(
        subject -> subject_service.add(subject)).collect(Collectors.toList());
  }

  public static List<Course> insert_courses(List<Subject> subjects) {
    List<Course> courses = new ArrayList<>();

    courses.add(new Course("type variable", lorem, Course.Level.EASY, subjects.get(0).get_id()));
    courses.add(new Course("structure", lorem, Course.Level., subjects.get(0).get_id()));
    courses.add(new Course("pointeur", lorem, Course.Level.HARD, subjects.get(0).get_id()));
    courses.add(new Course("Classes", lorem, Course.Level.HARD, subjects.get(1).get_id()));

    return courses.stream().map(
        course -> course_service.add(course)).collect(Collectors.toList());
  }

  public static List<User> insert_users(List<Class_esprit> class_esprit_list) {
    User user_1 = new User("armen", "bakir", 22, lorem, "armen.bakir@esprit.tn", "test1");
    User user_2 = new User("eya", "harbi", 22, lorem, "eya.harbi@esprit.tn", "test2");

    user_1.set_hashed_password();
    user_2.set_hashed_password();

    user_1.set_class_esprit(class_esprit_list.get(0));
    user_2.set_class_esprit(class_esprit_list.get(1));

    List<User> users = Arrays.asList(user_1, user_2);

    return users.stream().map(
        user -> user_service.add(user)).collect(Collectors.toList());
  }

  public static List<Session> insert_sessions(List<Course> courses, List<User> users) {
    List<Session> sessions = new ArrayList<>();

    LocalDate date_1 = LocalDate.of(2023, 2, 23);
    LocalDate date_2 = LocalDate.of(2023, 2, 24);
    LocalTime time_1 = LocalTime.of(12, 00, 00);
    LocalTime time_2 = LocalTime.of(13, 00, 00);
    LocalTime time_3 = LocalTime.of(17, 30, 00);
    LocalTime time_4 = LocalTime.of(18, 30, 00);

    sessions.add(new Session(users.get(0).get_id(), courses.get(0).get_id_c(), 12.5, date_1, time_1, time_2, lorem));
    sessions.add(new Session(users.get(1).get_id(), courses.get(0).get_id_c(), 16.5, date_1, time_1, time_2, lorem));
    sessions.add(new Session(users.get(1).get_id(), courses.get(1).get_id_c(), 16.5, date_2, time_3, time_4, lorem));

    return sessions.stream().map(
        session -> session_service.add(session)).collect(Collectors.toList());
  }
}
