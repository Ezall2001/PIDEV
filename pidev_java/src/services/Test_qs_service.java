package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Test_qs;
import utils.*;

public class Test_qs_service {
    Connection cnx;

    public Test_qs_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public Test_qs add(Test_qs question) {
        String sql = "insert into test_qs (question_number,optionA,optionB,optionC,optionD,correct_option,question,id_test) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, question.get_question_number());
            stmt.setString(2, question.get_optionA());
            stmt.setString(3, question.get_optionB());
            stmt.setString(4, question.get_optionC());
            stmt.setString(5, question.get_optionD());
            stmt.setString(6, question.get_correct_option());
            stmt.setString(7, question.get_question());
            stmt.setInt(8, question.get_test().get_id());
            stmt.executeUpdate();

            ResultSet generated_id = stmt.getGeneratedKeys();
            if (!generated_id.next())
                return null;

            question.set_id(generated_id.getInt(1));
            return question;

        } catch (Exception e) {
            Log.file(e);
        }

        return null;
    }

    public void delete(Test_qs test_qs) {
        String sql = "delete from test_qs where id=?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, test_qs.get_id());
            stmt.executeUpdate();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

    }

    public List<Test_qs> get_all() {
        List<Test_qs> test_questions = new ArrayList<>();
        try {
            String sql = "select * from test_qs";
            Statement stmt = cnx.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Test_qs question = new Test_qs(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString("question"),
                        result.getString("correct_option"),
                        result.getString("optionA"),
                        result.getString("optionB"),
                        result.getString("optionC"),
                        result.getString("optionD"));
                test_questions.add(question);
            }
        } catch (Exception e) {
            Log.file(e.getMessage());
        }
        return test_questions;
    }

    public void update(Test_qs question) {
        String sql = "update test_qs set question_number=?, question=?, correct_option=?, optionA=?, optionB=?, optionC=?, optionD=? where id=? ";
        try {
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, question.get_question_number());
            stmt.setString(2, question.get_question());
            stmt.setString(3, question.get_correct_option());
            stmt.setString(4, question.get_optionA());
            stmt.setString(5, question.get_optionB());
            stmt.setString(6, question.get_optionC());
            stmt.setString(7, question.get_optionD());
            stmt.setInt(8, question.get_id());
            stmt.executeUpdate();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

    }

    public Test_qs find_by_id(Test_qs test_qs) {
        Test_qs test_question = new Test_qs();
        try {
            String sql = "select * from test_qs where id=? ";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, test_qs.get_id());
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                test_question.set_id(result.getInt("id"));
                test_question.set_correct_option(result.getString("correct_option"));
                test_question.set_question(result.getString("question"));
                test_question.set_question_number(result.getInt("question_number"));
                test_question.set_optionA(result.getString("optionA"));
                test_question.set_optionB(result.getString("optionB"));
                test_question.set_optionC(result.getString("optionC"));
                test_question.set_optionD(result.getString("optionD"));
            }

        } catch (Exception e) {
            Log.file(e.getMessage());
        }
        return test_question;
    }

}
