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

public class Class_esprit_service {
  Connection cnx;

  public Class_esprit_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public Class_esprit add(Class_esprit class_esprit) {
    String sql = "insert into class_esprit(name) values (?)";
    try {

      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setString(1, class_esprit.get_name());
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      class_esprit.set_id(generated_id.getInt(1));
      return class_esprit;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }

  }

  public void update(Class_esprit class_esprit) {
    String sql = "UPDATE class_esprit SET name=? where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, class_esprit.get_name());
      stmt.setInt(2, class_esprit.get_id());

      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public void delete_by_id(Integer id) {
    String sql = "delete from class_esprit where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public List<Class_esprit> get_all() {
    List<Class_esprit> class_esprit_list = new ArrayList<>();
    try {
      String sql = "select * from class_esprit";
      Statement stmt = cnx.createStatement();
      ResultSet result = stmt.executeQuery(sql);

      while (result.next()) {
        Class_esprit class_esprit = new Class_esprit(
            result.getInt("id"),
            result.getString("name"));

        class_esprit_list.add(class_esprit);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return class_esprit_list;
  }

  public Class_esprit find_by_name(String name) {
    try {
      String sql = "select * from class_esprit where name=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, name);

      ResultSet result = stmt.executeQuery();

      if (result.next())
        return new Class_esprit(
            result.getInt("id"),
            result.getString("name"));
      return null;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }
  }

}