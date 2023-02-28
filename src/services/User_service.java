package services;

import utils.Jdbc_connection;
import utils.Log;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Current_user_data;
import entities.User;

public class User_service {
  Connection cnx;
  //TODO: ****things i changed
  User_session_service user_session_service = new User_session_service();
  private static User_service user_service_instance;

  public User_service() {
    user_session_service = User_session_service.get_user_session_service_instance();

    cnx = Jdbc_connection.getInstance();
  }

  public User add(User user) {
    if ((find_by_email(user.get_email()) == null) && (check_user_infos(user))) {

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

        Log.console(e.getMessage());
        return null;
      }
    } else
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

  public User login(String email, String password) {
    try {
      String sql = "SELECT * FROM users WHERE email = ?";
      PreparedStatement pst = cnx.prepareStatement(sql);
      pst.setString(1, email);
      ResultSet rSet = pst.executeQuery();
      while (rSet.next()) {
        String hashed_password = rSet.getString("password");
        if (check_password(password, hashed_password)) {
          User user = new User();
          user.set_id(rSet.getInt("id"));
          user.set_first_name(rSet.getString("first_name"));
          user.set_last_name(rSet.getString("last_name"));
          user.set_bio(rSet.getString("bio"));
          user.set_avatar_path(rSet.getString("avatar_path"));
          user.set_age(rSet.getInt("age"));
          user.set_email(rSet.getString("email"));
          user.set_password(rSet.getString("password"));
          user.set_type(rSet.getString("type"));
          user_session_service.create_session(user);
          Log.console("session created");
          Current_user_data.set_current_user(user);
          Current_user_data.set_current_session(user_session_service.get_user_session(user));
          return user;
        } else

          return null;

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return null;
  }

  public boolean check_password(String password, String hashed_password) {
    try {
      MessageDigest message_digest = MessageDigest.getInstance("SHA-256");
      message_digest.update(password.getBytes());
      byte[] bytes = message_digest.digest();
      StringBuilder string_builder = new StringBuilder();
      for (byte b : bytes) {
        string_builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
      }
      String hashedInput = string_builder.toString();
      return hashedInput.equals(hashed_password);
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return false;
  }

  public void logout(User user) {
    user_session_service.delete_session_by_email(user.get_email());

  }

  public boolean logged_in(User user) {
    user_session_service.delete_expired_sessions();
    if (user_session_service.find_session_by_email(user.get_email()))
      return true;
    return false;
  }

  //TODO: ****things i changed
  public static User_service get_user_service_instance() {
    if (user_service_instance == null) {
      user_service_instance = new User_service();
    }
    return user_service_instance;
  }
}