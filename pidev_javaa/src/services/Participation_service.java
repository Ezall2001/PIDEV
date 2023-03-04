package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Participation;
import entities.Session;
import entities.User;
import utils.Jdbc_connection;
import utils.Log;

public class Participation_service {
  Connection cnx;

  public Participation_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public Participation add(Participation participation) {

    Participation matched_participation = find_by_id_session_and_id_user(participation);
    if (matched_participation != null)
      return null;

    String sql = "insert into participations(id_user,id_session,state) values (?,?,?)";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, participation.get_user().get_id());
      stmt.setInt(2, participation.get_session().get_id());
      stmt.setString(3, Participation.State.PENDING.toString());
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      participation.set_id(generated_id.getInt(1));
      participation.set_state(Participation.State.PENDING);
      return participation;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }

  }

  public void update(Participation participation) {
    String sql = "UPDATE participations SET state=? where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, participation.get_state().toString());
      stmt.setInt(2, participation.get_id());
      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public Participation find_by_id_session_and_id_user(Participation participation) {
    String sql = "select * from participations where id_user = ? and id_session = ?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, participation.get_user().get_id());
      stmt.setInt(2, participation.get_session().get_id());
      stmt.executeQuery();

      ResultSet result = stmt.getResultSet();
      if (!result.next())
        return null;

      participation.set_id(result.getInt("id"));
      participation.set_state(result.getString("state"));
      return participation;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }

  }

  public List<Participation> find_by_id_session(Session session) {
    List<Participation> participations = new ArrayList<>();
    try {
      String sql = "select participations.id,state,email,first_name,last_name,score,id_user from participations left join users on id_user = users.id where id_session=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, session.get_id());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {

        User user = new User();
        user.set_id(result.getInt("id_user"));
        user.set_email(result.getString("email"));
        user.set_first_name(result.getString("first_name"));
        user.set_last_name(result.getString("last_name"));
        user.set_score(result.getInt("score"));

        Participation participation = new Participation();
        participation.set_id(result.getInt("id"));
        participation.set_state(result.getString("state"));
        participation.set_user(user);
        participation.set_session(session);

        participations.add(participation);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return participations;
  }

  public List<Participation> find_by_id_user(User user) {
    List<Participation> participations = new ArrayList<>();

    ///TODO: complete this
    return participations;
  }

}
