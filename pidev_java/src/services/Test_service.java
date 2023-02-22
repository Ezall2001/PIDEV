package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Test;
import entities.Test_qs;

import config.*;

public class Test_service {
    Connection cnx;

    public Test_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public void add(Test test) {
        try {
            String sql = "insert into tests (type,min_points,duration)"
                    + "values (?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, test.getType());
            ste.setInt(2, test.get_min_points());
            ste.setInt(3, test.get_duration());
            ste.executeUpdate();
            Log.console("test ajouté");
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Test> get_all() {
        List<Test> tests = new ArrayList<>();
        try {
            String sql = "select * from tests";
            Statement ste = cnx.createStatement();
            ResultSet set = ste.executeQuery(sql);
            while (set.next()) {
                Test t = new Test(
                        set.getInt(1),
                        set.getInt(3),
                        set.getInt(4),
                        set.getString("type"));
                tests.add(t);
            }
        } catch (SQLException ex) {
            Log.console(ex.getMessage());

        }
        return tests;
    }

    // ? edited
    public void modify(Test test) {
        String sql = "update tests set type=? ,min_points=?, duration=?  where id=? ";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, test.getType());
            ste.setInt(2, test.get_min_points());
            ste.setInt(3, test.get_duration());
            ste.setInt(4, test.get_id());
            ste.executeUpdate();
            Log.console("test modifié");
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public void delete(int id) {
        String sql = "delete from tests where id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ste.executeUpdate();
            Log.console("test supprimé");
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public Test get_by_id(int id) {

        Test test = new Test();
        try {
            String sql = "select * from tests where id=? ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();
            if (set.next()) {

                test.set_id(set.getInt(1));
                test.setType(set.getString("type"));
                test.set_min_points(set.getInt(3));
                test.set_duration(set.getInt(4));
            }

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        return test;
    }

    public HashMap<Test, List<Test_qs>> get_with_questions(int id) {
        HashMap<Test, List<Test_qs>> map = new HashMap<Test, List<Test_qs>>();
        Test test = new Test();
        test = (Test) get_by_id(id);
        List<Test_qs> questions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM test_qs LEFT JOIN tests ON test_qs.id_test = tests.id WHERE"
                    + " test_qs.id_test=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();
            while (set.next()) {
                Test_qs test_question = new Test_qs(
                        set.getInt(1),
                        set.getInt(2),
                        set.getString("question"),
                        set.getString("correct_option"),
                        set.getString("optionA"),
                        set.getString("optionB"),
                        set.getString("optionC"),
                        set.getString("optionD"));
                questions.add(test_question);
            }
            map.put(test, questions);
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return map;
    }
}
