package services;

import utils.Jdbc_connection;
import utils.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.User;

public class User_service {
  Connection cnx;

  private static User_session_service user_session_service = new User_session_service();

  public User_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public User add(User user) {
    if ((find_by_email(user.get_email()) != null) || (!check_user_infos(user)))
      return null;

    String sql = "insert into users(first_name,last_name,bio,age,email,password,score) values (?,?,?,?,?,?,?)";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setString(1, user.get_first_name());
      stmt.setString(2, user.get_last_name());
      stmt.setString(3, user.get_bio());
      stmt.setInt(4, user.get_age());
      stmt.setString(5, user.get_email());
      stmt.setString(6, user.get_hashed_password());
      stmt.setInt(7, 0);
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      user.set_id(generated_id.getInt(1));
      return user;

    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    return null;

  }

  public void update(User user) {
    String sql = "UPDATE users SET first_name=?, last_name=?, age=?, bio=?, avatar_path=? where email=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, user.get_first_name());
      stmt.setString(2, user.get_last_name());
      stmt.setInt(3, user.get_age());
      stmt.setString(4, user.get_bio());
      stmt.setString(5, user.get_avatar_path());
      stmt.setString(6, user.get_email());
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public void delete(User user) {
    String sql = "delete from users where email=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, user.get_email());
      stmt.executeUpdate();
      Log.console("deleted");
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

        users.add(user);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return users;
  }

  public User find_by_id(User user) {
    try {
      String sql = "select * from users where id=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, user.get_id());

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

  public boolean check_user_infos(User user) {
    String email = user.get_email();
    String first_name = user.get_first_name();
    String last_name = user.get_last_name();
    String bio = user.get_bio();
    Integer age = user.get_age();
    if ((email.isEmpty()) || (!email.endsWith("@esprit.tn"))) {
      return false;
    }
    if ((first_name.isEmpty()) || (!first_name.matches("[a-zA-Z]+")) || ((first_name.length() < 2))) {
      return false;
    }
    if ((last_name.isEmpty()) || (!last_name.matches("[a-zA-Z]+")) || (last_name.length() < 2)) {
      return false;
    }
    if ((bio.isEmpty()) || (!bio.matches("^[a-zA-Z0-9,.!?\\s]+$")) || (bio.length() < 10)) //TODO: change bio to 50 
    {
      return false;
    }
    if ((age.toString().isEmpty()) || (!age.toString().matches("[0-9]+"))) {
      return false;
    }

    return true;
  }

  public User login(User user) {
    try {
      String sql = "SELECT * FROM users WHERE email = ?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, user.get_email());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        User user_to_match = new User(result.getString("email"),
            result.getString("password"));

        if (user.equals_to_user(user_to_match)) {
          User matched_user = user_to_match;

          matched_user.set_id(result.getInt("id"));
          matched_user.set_first_name(result.getString("first_name"));
          matched_user.set_last_name(result.getString("last_name"));
          matched_user.set_bio(result.getString("bio"));
          matched_user.set_avatar_path(result.getString("avatar_path"));
          matched_user.set_age(result.getInt("age"));
          matched_user.set_email(result.getString("email"));
          matched_user.set_type(result.getString("type"));
          matched_user.set_score(result.getInt("score"));

          user_session_service.create_session(matched_user);

          return matched_user;
        }

      }

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return null;
  }

  public void logout() {
    user_session_service.delete_session();
  }

}
