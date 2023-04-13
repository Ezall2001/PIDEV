package pages.dashboard;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import entities.Course;
import entities.Subject;
import entities.Test;
import entities.Test_result;
import entities.User;
import services.Course_service;
import services.Session_service;
import services.Subject_service;
import services.Test_result_service;
import services.Test_service;
import services.User_service;
import utils.String_helpers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import densityCurve.DensityCurve;
import StatsFx.StatsPie;
import StatsFx.StatsBar;
import StatsFx.StatsArea;
import StatsFx.utils.StatsArrays;

public class Dashboard_controller implements Initializable {
  private static User_service user_service = new User_service();
  private static Session_service session_service = new Session_service();
  private static Course_service course_service = new Course_service();
  private static Subject_service subject_service = new Subject_service();
  private static Test_service test_service = new Test_service();
  private static Test_result_service test_result_service = new Test_result_service();

  @FXML
  private AreaChart<String, Double> area_chart;

  @FXML
  private BarChart<String, Integer> bar_chart;

  @FXML
  private PieChart pie_chart;

  @FXML
  private Label total_courses_label;

  @FXML
  private Label total_subjects_label;

  @FXML
  private Label total_tests_label;

  @FXML
  private Label total_users_label;

  private List<User> users;
  private List<Subject> subjects;
  private List<Course> courses;
  private List<Test> tests;
  private List<Test_result> test_results;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    users = user_service.get_all();
    subjects = subject_service.get_all();
    courses = course_service.get_all();
    tests = test_service.get_all();
    test_results = test_result_service.get_all();

    total_users_label.setText(Integer.toString(users.size()));
    total_subjects_label.setText(Integer.toString(subjects.size()));
    total_courses_label.setText(Integer.toString(courses.size()));
    total_tests_label.setText(Integer.toString(tests.size()));

    pie_chart();
    bar_chart();
    area_chart();
  }

  public void pie_chart() {

    HashMap<String, Integer> sessions_per_subject = new HashMap<>();
    courses
        .stream()
        .forEach(course -> {
          Integer number_of_sessions = session_service.find_by_id_course(course).size();
          String subject = course.get_subject().get_name();
          if (sessions_per_subject.containsKey(subject))
            sessions_per_subject.put(subject, sessions_per_subject.get(subject) + number_of_sessions);
          else
            sessions_per_subject.put(subject, number_of_sessions);
        });

    StatsPie.build(pie_chart, sessions_per_subject);
  }

  public void bar_chart() {

    HashMap<String, Integer> users_per_level = new HashMap<>();
    Arrays.asList(User.Level.values())
        .stream()
        .map(level -> String_helpers.capitalize(level.toString()))
        .forEach(level -> users_per_level.put(level, 0));

    users
        .stream()
        .forEach(user -> {
          if (users_per_level.containsKey(user.get_level()))
            users_per_level.put(user.get_level(), users_per_level.get(user.get_level()) + 1);
        });

    StatsBar.build(bar_chart, users_per_level);
  }

  public void area_chart() {
    List<Integer> marks_range = StatsArrays.range(0, 20, 1);

    HashMap<Integer, Integer> mark_count = new HashMap<>();
    marks_range.forEach(mark -> mark_count.put(mark, 0));
    test_results
        .stream()
        .forEach(test_result -> {
          Integer mark = test_result.get_mark();
          if (mark_count.containsKey(mark))
            mark_count.put(mark, mark_count.get(mark) + 1);
        });

    List<Double> xs = marks_range
        .stream()
        .map(mark -> new Double(mark))
        .collect(Collectors.toList());

    List<Double> ys = mark_count
        .values()
        .stream()
        .map(mark -> new Double(mark))
        .collect(Collectors.toList());

    List<String> x_vals = mark_count
        .keySet()
        .stream()
        .map(key -> key.toString())
        .collect(Collectors.toList());

    List<Double> y_vals = DensityCurve.densityCurve(xs, ys, marks_range.size());

    StatsArea.build(area_chart, x_vals, y_vals);

  }

}