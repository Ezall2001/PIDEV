package services;

import utils.Jdbc_connection;
import utils.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Test;
import entities.Test_result;
import entities.User;

public class Test_result_service {
  Connection cnx;

  static private User_service user_service = new User_service();

  public Test_result_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public Test_result add(Test_result test_result) {
    Test_result found_test_result = find_by_id_user_and_id_test(test_result);

    if (found_test_result != null) {
      if (test_result.get_mark() < found_test_result.get_mark())
        return found_test_result;

      found_test_result.set_mark(test_result.get_mark());
      update_test_result_mark(found_test_result);
      user_service.update_score(found_test_result);

      return found_test_result;
    }

    if (test_result.get_mark() < test_result.get_test().get_min_points())
      return null;

    String sql = "INSERT INTO test_results (mark, id_user, id_test) values ( ? , ? , ?) ";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, test_result.get_mark());
      stmt.setInt(2, test_result.get_user().get_id());
      stmt.setInt(3, test_result.get_test().get_id());
      stmt.executeUpdate();

      user_service.update_score(test_result);

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      test_result.set_id(generated_id.getInt(1));
      return test_result;

    } catch (Exception e) {
      Log.file(e);
    }

    return null;
  }

  public Test_result find_by_id_user_and_id_test(Test_result test_result) {
    String sql = "select * from test_results where id_user = ? and id_test = ?";

    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, test_result.get_user().get_id());
      stmt.setInt(2, test_result.get_test().get_id());
      ResultSet result = stmt.executeQuery();

      if (!result.next())
        return null;

      Test test = new Test();
      test.set_id(result.getInt("id_test"));

      User user = new User();
      user.set_id(result.getInt("id_user"));

      Test_result found_test_result = new Test_result();
      found_test_result.set_user(user);
      found_test_result.set_test(test);
      found_test_result.set_mark(result.getInt("mark"));
      return found_test_result;

    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    return null;
  }

  public void update_test_result_mark(Test_result test_result) {
    String sql = "UPDATE test_results SET mark = ? WHERE id_user = ? AND id_test = ?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, test_result.get_mark());
      stmt.setInt(2, test_result.get_user().get_id());
      stmt.setInt(3, test_result.get_test().get_id());
      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e);
    }
  }

  public List<Test_result> get_all() {
    List<Test_result> test_results = new ArrayList<>();

    try {
      String sql = "SELECT * FROM test_results";
      Statement stmt = cnx.createStatement();
      ResultSet result = stmt.executeQuery(sql);
      while (result.next()) {
        Test_result test_result = new Test_result();
        Test test = new Test();
        test.set_id(result.getInt("id_test"));

        User user = new User();
        user.set_id(result.getInt("id_user"));
        test_result.set_mark(result.getInt("mark"));
        test_result.set_user(user);
        test_result.set_test(test);
        test_results.add(test_result);
      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    return test_results;
  }
}