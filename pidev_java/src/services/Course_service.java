package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import entities.Course;
import entities.Subject;
import utils.Jdbc_connection;
import utils.Log;

public class Course_service {

  Connection cnx;

  public Course_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public List<Course> get_all() {
    List<Course> courses = new ArrayList<>();

    try {
      String sql = "SELECT courses.id, courses.name, courses.description, courses.difficulty, subjects.id AS id_subject, subjects.name as name_subject, subjects.classes_esprit FROM courses LEFT JOIN subjects ON subjects.id = courses.id_subject";
      PreparedStatement stmt = cnx.prepareStatement(sql);

      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        Course course = new Course(
            result.getInt("id"),
            result.getString("name"),
            result.getString("description"),
            result.getString("difficulty"));

        Subject subject = new Subject();
        subject.set_id(result.getInt("id_subject"));
        subject.set_name(result.getString("name_subject"));
        subject.set_classes_esprit(result.getString("classes_esprit"));

        course.set_subject(subject);
        courses.add(course);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return courses;
  }

  public Course add(Course course) {
    String sql = "insert into courses (name, description, difficulty, id_subject) values (?,?,?,?)";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setString(1, course.get_name());
      stmt.setString(2, course.get_description());
      stmt.setString(3, course.get_difficulty_string());
      stmt.setInt(4, course.get_subject().get_id());
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      course.set_id(generated_id.getInt(1));
      return course;
    } catch (Exception e) {
      Log.file(e);
      return null;
    }
  }

  public void delete_by_id(Course course) {
    String sql = "delete from courses where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, course.get_id());
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public void update(Course course) {
    String sql = "UPDATE courses SET name=?, description=?, difficulty=?, id_subject=? where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, course.get_name());
      stmt.setString(2, course.get_description());
      stmt.setString(3, course.get_difficulty_string());
      stmt.setInt(4, course.get_subject().get_id());
      stmt.setInt(5, course.get_id());
      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

}