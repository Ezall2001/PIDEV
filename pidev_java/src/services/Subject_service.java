package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.Jdbc_connection;
import entities.Subject;
import utils.Log;

public class Subject_service {

  Connection cnx;

  public Subject_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public Subject add(Subject subject) {
    String sql = "INSERT INTO subjects (name, description, classes_esprit) values ( ? , ? , ? ) ";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setString(1, subject.get_name());
      stmt.setString(2, subject.get_description());
      stmt.setString(3, subject.get_classes_esprit_string());
      stmt.executeUpdate();
      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      subject.set_id(generated_id.getInt(1));
      return subject;
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return null;
  }

  public void update(Subject subject) {
    String sql = "update subjects set name=? ,description=?, classes_esprit=? where id=? ";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, subject.get_name());
      stmt.setString(2, subject.get_description());
      stmt.setString(3, subject.get_classes_esprit_string());
      stmt.setInt(4, subject.get_id());
      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public List<Subject> get_all() {
    List<Subject> subjects = new ArrayList<>();

    try {
      String sql = "select * from subjects";
      Statement stmt = cnx.createStatement();
      ResultSet result = stmt.executeQuery(sql);

      while (result.next()) {
        Subject subject = new Subject(
            result.getInt("id"),
            result.getString("name"),
            result.getString("description"));

        subject.set_classes_esprit(result.getString("classes_esprit"));

        subjects.add(subject);
      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return subjects;
  }

  public void delete_subject(Subject subject) {
    String sql = "delete from subject where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, subject.get_id());
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

  }

}
