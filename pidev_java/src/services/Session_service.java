package services;

import utils.Jdbc_connection;
import utils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Course;
import entities.Session;
import entities.User;

public class Session_service {
  Connection cnx;

  public Session_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public Session add(Session session) {
    String sql = "insert into sessions(price,date,start_time,end_time,id_user,topics,id_course) values (?,?,?,?,?,?,?)";
    try {

      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setDouble(1, session.get_price());
      stmt.setDate(2, session.get_date_sqlDate());
      stmt.setTime(3, session.get_start_time_sqlTime());
      stmt.setTime(4, session.get_end_time_sqlTime());
      stmt.setInt(5, session.get_user().get_id());
      stmt.setString(6, session.get_topics());
      stmt.setInt(7, session.get_course().get_id());
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      session.set_id(generated_id.getInt(1));
      return session;

    } catch (Exception e) {
      Log.file(e);
      return null;
    }

  }

  public void update(Session session) {
    String sql = "UPDATE sessions SET price=?, date=?, start_time=?, end_time=?, topics=? where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setDouble(1, session.get_price());
      stmt.setDate(2, session.get_date_sqlDate());
      stmt.setTime(3, session.get_start_time_sqlTime());
      stmt.setTime(4, session.get_end_time_sqlTime());
      stmt.setString(5, session.get_topics());
      stmt.setInt(6, session.get_id());
      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public void delete_by_id(Session session) {
    String sql = "delete from sessions where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, session.get_id());
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public List<Session> find_by_id_course(Course course) {
    List<Session> sessions = new ArrayList<>();
    try {
      String sql = "select sessions.id,price,date,start_time,end_time,topics,users.id as user_id,users.first_name as user_first_name,users.last_name as user_last_name from sessions LEFT JOIN users ON users.id = sessions.id_user where id_course=?;";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, course.get_id());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {

        User user = new User();
        user.set_first_name(result.getString("user_first_name"));
        user.set_last_name(result.getString("user_last_name"));
        user.set_id(result.getInt("user_id"));

        Session session = new Session(
            result.getInt("id"),
            result.getDouble("price"),
            result.getDate("date"),
            result.getTime("start_time"),
            result.getTime("end_time"),
            result.getString("topics"));

        session.set_user(user);
        session.set_course(course);

        sessions.add(session);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return sessions;
  }

  public Session find_by_id(Session session) {
    try {
      String sql = "select * from sessions where id=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, session.get_id());

      ResultSet result = stmt.executeQuery();

      if (result.next())
        return new Session(
            result.getInt("id"),
            result.getDouble("price"),
            result.getDate("date"),
            result.getTime("start_time"),
            result.getTime("end_time"),
            result.getString("topics"));

    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    return null;
  }

}