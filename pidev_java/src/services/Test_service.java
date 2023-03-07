package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.Test;
import entities.Test_qs;
import utils.Log;
import utils.Jdbc_connection;

public class Test_service {
    Connection cnx;

    public Test_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public Test add(Test test) {
        String sql = "insert into tests (type,min_points,duration,id_subject,id_course) values (?,?,?,?,?)";

        Integer id_course = null, id_subject = null;

        if (test.get_type() == Test.Type.SUBJECT)
            id_subject = test.get_subject().get_id();
        else if (test.get_type() == Test.Type.COURSE)
            id_course = test.get_course().get_id();

        try {
            PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, test.get_type().toString());
            stmt.setInt(2, test.get_min_points());
            stmt.setInt(3, test.get_duration());
            stmt.setObject(4, id_subject);
            stmt.setObject(5, id_course);
            stmt.executeUpdate();

            ResultSet generated_id = stmt.getGeneratedKeys();
            if (!generated_id.next())
                return null;

            test.set_id(generated_id.getInt(1));
            return test;

        } catch (Exception e) {
            Log.file(e);
            return null;
        }

    }

    public List<Test> get_all() {
        List<Test> tests = new ArrayList<>();
        try {
            String sql = "select * from tests";
            Statement stmt = cnx.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Test test = new Test(
                        result.getInt("id"),
                        result.getInt("min_points"),
                        result.getInt("duration"),
                        result.getString("type"));

                tests.add(test);
            }
        } catch (Exception e) {
            Log.file(e.getMessage());

        }
        return tests;
    }

    public void update(Test test) {
        String sql = "update tests set type=? ,min_points=?, duration=?  where id=? ";
        try {
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setString(1, test.get_type_string());
            stmt.setInt(2, test.get_min_points());
            stmt.setInt(3, test.get_duration());
            stmt.setInt(4, test.get_id());
            stmt.executeUpdate();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

    public void delete(Test test) {
        String sql = "delete from tests where id=?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, test.get_id());
            stmt.executeUpdate();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

    }

    public Test find_by_id(Test test) {

        try {
            String sql = "select * from tests where id=? ";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, test.get_id());
            ResultSet result = stmt.executeQuery();

            if (!result.next())
                return null;

            Test test_found = new Test();
            test_found.set_id(result.getInt("id"));
            test_found.set_type(result.getString("type"));
            test_found.set_min_points(result.getInt("min_points"));
            test_found.set_duration(result.getInt("duration"));
            return test_found;

        } catch (Exception e) {
            Log.file(e.getMessage());
            return null;
        }
    }

    public Test get_by_course_or_subject_id(Test test) {
        String id_query_string = "";
        Integer id_query = -1;

        if (test.get_type() == Test.Type.SUBJECT) {
            id_query_string = "tests.id_subject  = ?";
            id_query = test.get_subject().get_id();
        }

        else if (test.get_type() == Test.Type.COURSE) {
            id_query_string = "tests.id_course  =  ?";
            id_query = test.get_course().get_id();
        }

        try {
            String sql = "SELECT tests.id as id,test_qs.id as question_id, type, min_points, duration, question_number, optionA, optionB, optionC, optionD, correct_option, question, id_test FROM tests LEFT JOIN test_qs ON test_qs.id_test = tests.id WHERE "
                    + id_query_string;
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, id_query);

            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Test found_test = new Test(
                        result.getInt("id"),
                        result.getInt("min_points"),
                        result.getInt("duration"),
                        result.getString("type"));

                Test_qs test_question = new Test_qs(
                        result.getInt("question_id"),
                        result.getInt("question_number"),
                        result.getString("question"),
                        result.getString("correct_option"),
                        result.getString("optionA"),
                        result.getString("optionB"),
                        result.getString("optionC"),
                        result.getString("optionD"));

                test_question.set_test(found_test);

                test.get_questions().add(test_question);
            }

            Test found_test = test.get_questions().get(0).get_test();
            test.set_id(found_test.get_id());
            test.set_min_points(found_test.get_min_points());
            test.set_duration(found_test.get_duration());
            test.set_type(found_test.get_type());

        } catch (Exception e) {
            Log.file(e.getMessage());
        }

        return test;
    }
}
