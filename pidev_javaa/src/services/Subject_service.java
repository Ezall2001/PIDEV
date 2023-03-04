package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import utils.Jdbc_connection;
import entities.Course;
import entities.Course.Difficulty;
import entities.Subject;
import entities.Subject.Level;
import utils.Log;

public class Subject_service {

  Connection cnx;

  public Subject_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public void add_subject(Subject subject) {
    try {
      String sqlCheck = "select count(*) from Subject where subject_name=?";
      PreparedStatement stmtCheck = cnx.prepareStatement(sqlCheck);
      stmtCheck.setString(1, subject.get_subject_name());
      ResultSet rs = stmtCheck.executeQuery();
      rs.next();
      int count = rs.getInt(1);
      if (count > 0) {
        Log.console("Le nom du matiere " + subject.get_subject_name() + " est deja  exister.");
        return;
      }

      String sql = "insert into Subject(id,subject_name,description,niveau)"
          + "values (?,?,?,?)";
      PreparedStatement ste = cnx.prepareStatement(sql);

      ste.setInt(1, subject.get_id());
      ste.setString(2, subject.get_subject_name());
      ste.setString(3, subject.get_description());
      ste.setString(4, subject.get_level_String());
      ste.executeUpdate();
      Log.console("Matiere ajoutée");
    } catch (SQLException ex) {
      Log.file(ex.getMessage());
    }
  }

  public List<Subject> get_all() {
    List<Subject> subjects = new ArrayList<>();
    try {
      String sql = "select * from subject";
      Statement ste = cnx.createStatement();
      ResultSet result = ste.executeQuery(sql);
      while (result.next()) {
        Subject new_subject = new Subject(
            result.getInt(1),
            result.getString(2),
            result.getString(3),
            Level.valueOf(result.getString("niveau")));

        subjects.add(new_subject);

      }
    } catch (SQLException ex) {
      Log.file(ex.getMessage());
    }
    return subjects;
  }

  public List<Subject> get_by_id(int id) {
    List<Subject> subjects = new ArrayList<>();
    try {
      String sql = "SELECT * FROM subject  WHERE id=?";
      PreparedStatement ste = cnx.prepareStatement(sql);
      ste.setInt(1, id);
      ResultSet rs = ste.executeQuery();

      while (rs.next()) {
        Subject new_subject = new Subject();
        new_subject.set_subject_name(rs.getString("subject_name"));
        new_subject.set_description(rs.getString("description"));
        new_subject.set_level(rs.getString("niveau"));

        subjects.add(new_subject);
      }
      rs.close();
      ste.close();

    } catch (SQLException ex) {
      Log.file(ex.getMessage());
    }

    return subjects;

  };

  public void delete_subject(Subject subject) {
    String sql = "delete from subject where subject_name=?";
    try {
      PreparedStatement ste = cnx.prepareStatement(sql);
      ste.setString(1, subject.get_subject_name());
      ste.executeUpdate();
    } catch (SQLException ex) {
      Log.file(ex.getMessage());
    }

  }

  public HashMap<Subject, List<Course>> get_with_course(int id) {
    HashMap<Subject, List<Course>> map = new HashMap<Subject, List<Course>>();
    List<Subject> q = get_by_id(id);
    List<Course> courses = new ArrayList<>();

    try {
      String sql = "SELECT * FROM subject LEFT JOIN course ON id_subject = course.id_subject WHERE id=? ";
      PreparedStatement ste = cnx.prepareStatement(sql);
      ste.setInt(1, id);
      ResultSet rs = ste.executeQuery();
      while (rs.next()) {
        String course_name = rs.getString("course_name");
        String description_c = rs.getString("description_c");
        Difficulty difficulty = Difficulty.valueOf(rs.getString("difficulty"));
        int id_c = rs.getInt(5);
        Course r = new Course(id_c, course_name, description_c, difficulty);
        courses.add(r);
      }
      map.put(q.get(0), courses);
      rs.close();
      ste.close();

    } catch (SQLException ex) {
      Log.file(ex.getMessage());
    }
    return map;
  }

  public List<Subject> trier() {
    List<Subject> subjects = new ArrayList<Subject>();
    subjects = get_all();
    subjects = subjects.stream()
        .sorted(Comparator.comparing(Subject::get_subject_name))

        .collect(Collectors.toList());
    return subjects;
  }

  public Subject search_subject(String search_subject) {
    List<Subject> subjects = get_all();

    // Search for the subject in the list
    Optional<Subject> optionalSubject = subjects.stream()
        .filter(s -> s.get_subject_name().equals(search_subject))
        .findFirst();
    if (optionalSubject.isPresent()) {
      Log.console("La matiere " + search_subject + " est existe");
      return optionalSubject.get();
    } else {
      Log.console("La matiere " + search_subject + "  n'existe pas");
      return null;
    }
  }
}
