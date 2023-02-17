package services;

import java.util.Arrays;
import java.util.List;

import entities.Course;
import types.Service_response;

public class Course_service extends Base_service {

  public Service_response<Integer> add_course(Course course) {
    return super.add(course, null);
  }

  public Service_response<Integer> modify_course_by_id(Course search_course, Course modification_course) {
    return super.modify(search_course, modification_course, Arrays.asList("id"), null);
  }

  public Service_response<Integer> delete_course_by_id(Course course) {
    return super.delete(course, Arrays.asList("id"));
  }

  public Service_response<List<Course>> find_course_by_id(Course course) {
    return super.find(course, Arrays.asList("id"));
  }

  public Service_response<List<Course>> find_course_by_id(Integer id) {
    Course course = new Course();
    course.set_id(id);
    return find_course_by_id(course);
  }

  public Service_response<Integer> delete_course_by_id(Integer id) {
    Course course = new Course();
    course.set_id(id);
    return delete_course_by_id(course);
  }

}
