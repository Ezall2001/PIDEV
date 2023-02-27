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
import entities.User;

public class User_service {
  Connection cnx;

  public User_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public User add(User user) {
    String sql = "insert into users(first_name,last_name,bio,age,email,password,id_class_esprit) values (?,?,?,?,?,?,?)";
    try {

      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setString(1, user.get_first_name());
      stmt.setString(2, user.get_last_name());
      stmt.setString(3, user.get_bio());
      stmt.setInt(4, user.get_age());
      stmt.setString(5, user.get_email());
      stmt.setString(6, user.get_hashed_password());
      stmt.setInt(7, user.get_class_esprit().get_id());
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      user.set_id(generated_id.getInt(1));
      return user;

    } catch (Exception e) {

      Log.file(e.getMessage());
      return null;
    }

  }

  public void update(User user) {
    String sql = "UPDATE users SET first_name=?, last_name=?, age=?, bio=?, avatar_path=?, class_esprit_id=? where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, user.get_first_name());
      stmt.setString(2, user.get_last_name());
      stmt.setInt(3, user.get_age());
      stmt.setString(4, user.get_bio());
      stmt.setString(5, user.get_avatar_path());
      stmt.setInt(6, user.get_class_esprit().get_id());
      stmt.setInt(7, user.get_id());
      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public void delete_by_id(Integer id) {
    String sql = "delete from users where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public List<User> get_all() {
    List<User> users = new ArrayList<>();
    try {
      String sql = "select * from users";
      Statement stmt = cnx.createStatement();
      ResultSet result = stmt.executeQuery(sql);

      while (result.next()) {
        User user = new User(
            result.getInt("id"),
            result.getString("first_name"),
            result.getString("last_name"),
            result.getString("bio"),
            result.getInt("age"),
            result.getInt("score"),
            result.getString("email"),
            result.getString("avatar_path"),
            result.getString("type"));

        user.set_class_esprit(new Class_esprit(result.getString(0)));

        users.add(user);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return users;
  }

  public User find_by_id(Integer id) {
    try {
      String sql = "select * from users where id=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, id);

      ResultSet result = stmt.executeQuery();

      if (result.next())
        return new User(
            result.getInt("id"),
            result.getString("first_name"),
            result.getString("last_name"),
            result.getString("bio"),
            result.getInt("age"),
            result.getInt("score"),
            result.getString("email"),
            result.getString("avatar_path"),
            result.getString("type"));
      return null;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }
  }

  public User find_by_email(String email) {
    try {
      String sql = "select * from users where email=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, email);

      ResultSet result = stmt.executeQuery();

      if (result.next())
        return new User(
            result.getInt("id"),
            result.getString("first_name"),
            result.getString("last_name"),
            result.getString("bio"),
            result.getInt("age"),
            result.getInt("score"),
            result.getString("email"),
            result.getString("avatar_path"),
            result.getString("type"));
      return null;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }
  }

}