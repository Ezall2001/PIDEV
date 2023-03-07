package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.Jdbc_connection;
import utils.Log;
import entities.Vote;

public class Vote_service {
  Connection cnx;

  private static Answer_service answer_service = new Answer_service();

  public Vote_service() {
    cnx = Jdbc_connection.getInstance();
  }

  private void update_answer_nb_vote(Integer modifier, Vote vote) {
    Integer answer_vote = vote.get_answer().get_vote_nb();
    vote.get_answer().set_vote_nb(answer_vote + vote.get_type_vote() * modifier);
    answer_service.update_vote(vote.get_answer());
  }

  public Vote add(Vote vote) {
    String sql = "insert into votes(answer_id, id_user,vote_type) values(?,?,?) ";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, vote.get_answer().get_id());
      stmt.setInt(2, vote.get_user().get_id());
      stmt.setInt(3, vote.get_type_vote());

      stmt.executeUpdate();
      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      vote.set_id(generated_id.getInt(1));
      stmt.close();

      update_answer_nb_vote(1, vote);

      return vote;
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    return null;
  }

  public void delete(Vote vote) {

    try {
      String sql = "DELETE FROM votes WHERE id=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, vote.get_id());
      stmt.executeUpdate();
      stmt.close();

      update_answer_nb_vote(-1, vote);

    } catch (Exception e) {
      Log.file(e.getMessage());
    }

  }

  public void update(Vote vote) {
    try {
      String sql = "UPDATE votes SET vote_type=? WHERE id=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, vote.get_type_vote());
      stmt.setInt(2, vote.get_id());

      update_answer_nb_vote(2, vote);

      stmt.executeUpdate();
      stmt.close();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public Vote find_by_id_user_and_id_answer(Vote vote) {

    try {
      String sql = "SELECT * FROM votes WHERE id_user=? and answer_id =?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, vote.get_user().get_id());
      stmt.setInt(2, vote.get_answer().get_id());

      ResultSet result = stmt.executeQuery();
      if (!result.next())
        return null;

      vote.set_id(result.getInt("id"));
      vote.set_type_vote(result.getInt("vote_type"));
      result.close();
      stmt.close();

      return vote;

    } catch (

    SQLException ex) {
      Log.console(ex.getMessage());
    }

    return null;

  }

}
