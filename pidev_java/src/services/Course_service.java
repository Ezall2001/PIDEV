package services;

import utils.Jdbc_connection;
import utils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Course;

public class Course_service {
  Connection cnx;

  public Course_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public Course add(Course course) {
    String sql = "insert into courses(name,description,difficulty,id_subject) values (?,?,?,?)";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setString(1, course.get_name());
      stmt.setString(2, course.get_description());
      stmt.setString(3, course.get_difficulty_String());
      stmt.setInt(4, course.get_id_subject());
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      course.set_id(generated_id.getInt(1));
      return course;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }

  }

  public void update(Course course) {
    String sql = "UPDATE courses SET name=?, description=?, difficulty=?, id_subject=? where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, course.get_name());
      stmt.setString(2, course.get_description());
      stmt.setString(3, course.get_difficulty_String());
      stmt.setInt(4, course.get_id_subject());
      stmt.setInt(5, course.get_id());

      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public void delete_by_id(Integer id) {
    String sql = "delete from courses where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public List<Course> get_all() {
    List<Course> courses = new ArrayList<>();
    try {
      String sql = "select * from courses";
      Statement stmt = cnx.createStatement();
      ResultSet result = stmt.executeQuery(sql);

      while (result.next()) {
        Course course = new Course(
            result.getInt("id"),
            result.getString("name"),
            result.getString("description"),
            result.getString("difficulty"),
            result.getInt("id_subject"));

        courses.add(course);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return courses;
  }

}