package tests;

import java.util.List;

import config.Log;
import entities.Course;
import services.Course_service;
import types.Service_response;

public class Course_test {
  public static void main(String[] args) {
    Course_service service = new Course_service();
    Course course = new Course("integrale partiel", "integrale", "HARD", 1);

    // add test
    Service_response<Integer> add_response = service.add_course(course);
    if (check_error(add_response))
      return;
    Integer new_id = add_response.get_body();
    course.set_id(new_id);

    // modify test
    Course modification_course = new Course(new_id, "integrale partiel", "integrale", "EASY", 1);
    Service_response<Integer> modify_response = service.modify_course_by_id(course,
        modification_course);
    if (check_error(modify_response))
      return;

    // find test
    Service_response<List<Course>> find_response = service.find_course_by_id(course);
    if (check_error(find_response))
      return;
    Log.console(find_response.get_body());

    // delete test
    Service_response<Integer> delete_response = service.delete_course_by_id(course);
    if (check_error(delete_response))
      return;

  }

  public static <T> Boolean check_error(Service_response<T> response) {
    if (response.get_error() != null) {
      Log.console(response.get_error());
      return true;
    }

    return false;
  }
}