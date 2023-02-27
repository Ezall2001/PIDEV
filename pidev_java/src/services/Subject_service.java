package services;

import utils.Jdbc_connection;
import utils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Class_esprit;
import entities.Subject;

public class Subject_service {
  Connection cnx;

  public Subject_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public Subject add(Subject subject) {
    String sql = "insert into subjects(name,description,difficulty,id_class_esprit) values (?,?,?,?)";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setString(1, subject.get_name());
      stmt.setString(2, subject.get_description());
      stmt.setString(3, subject.get_difficulty_String());
      stmt.setInt(4, subject.get_class_esprit().get_id());
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      subject.set_id(generated_id.getInt(1));
      return subject;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }

  }

  public void update(Subject subject) {
    String sql = "UPDATE subjects SET name=?, description=?, difficulty=?, id_class_esprit=? where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, subject.get_name());
      stmt.setString(2, subject.get_description());
      stmt.setString(3, subject.get_difficulty_String());
      stmt.setInt(4, subject.get_class_esprit().get_id());
      stmt.setInt(5, subject.get_id());

      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public void delete_by_id(Integer id) {
    String sql = "delete from subjects where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public List<Subject> get_all() {
    List<Subject> subjects = new ArrayList<>();
    try {
      String sql = "select subjects.* , class_esprit.name as name_class_esprit from subjects left join class_esprit on subjects.id_class_esprit = class_esprit.id";
      Statement stmt = cnx.createStatement();
      ResultSet result = stmt.executeQuery(sql);

      while (result.next()) {
        Subject subject = new Subject(
            result.getInt("id"),
            result.getString("name"),
            result.getString("description"),
            result.getString("diffculty"),
            new Class_esprit(result.getInt("id_class_esprit"), result.getString("name_class_esprit")));

        subjects.add(subject);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return subjects;
  }

}