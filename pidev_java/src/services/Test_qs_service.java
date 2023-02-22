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

    public void add(Test_qs question) {
        try {
            String sql = "insert into test_qs (question_number,optionA,optionB,optionC,optionD,correct_option,question,id_test)"
                    + "values (?,?,?,?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, question.get_question_number());
            ste.setString(2, question.get_optionA());
            ste.setString(3, question.get_optionB());
            ste.setString(4, question.get_optionC());
            ste.setString(5, question.get_optionD());
            ste.setString(6, question.get_correct_option());
            ste.setString(7, question.get_question());
            ste.setInt(8, question.get_test().get_id());
            ste.executeUpdate();
            Log.console("question ajoutée");
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public void delete(int id) {
        String sql = "delete from test_qs where id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ste.executeUpdate();
            Log.console("question supprimée");
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Test_qs> get_all() {
        List<Test_qs> test_questions = new ArrayList<>();
        try {
            String sql = "select * from test_qs";
            Statement ste = cnx.createStatement();
            ResultSet set = ste.executeQuery(sql);
            while (set.next()) {
                Test_qs question = new Test_qs(
                        set.getInt(1),
                        set.getInt(2),
                        set.getString("question"),
                        set.getString("correct_option"),
                        set.getString("optionA"),
                        set.getString("optionB"),
                        set.getString("optionC"),
                        set.getString("optionD"));
                test_questions.add(question);
            }
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        return test_questions;
    }

    public void modify(Test_qs question) {
        String sql = "update test_qs question_number=?, question=?, correct_option=?, optionA=?"
                +
                ", optionB=?, optionC=?, optionD=? where id=? ";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, question.get_question_number());
            ste.setString(2, question.get_question());
            ste.setString(3, question.get_correct_option());
            ste.setString(4, question.get_optionA());
            ste.setString(5, question.get_optionB());
            ste.setString(6, question.get_optionC());
            ste.setString(7, question.get_optionD());
            ste.setInt(8, question.get_id());
            ste.executeUpdate();
            Log.console("question modifiée");
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public Test_qs get_by_id(int id) {
        Test_qs test_question = new Test_qs();
        try {
            String sql = "select * from test_qs where id=? ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();
            if (set.next()) {
                test_question.set_id(set.getInt(1));
                test_question.set_correct_option(set.getString("correct_option"));
                test_question.set_question(set.getString("question"));
                test_question.set_question_number(set.getInt(2));
                test_question.set_optionA(set.getString("optionA"));
                test_question.set_optionB(set.getString("optionB"));
                test_question.set_optionC(set.getString("optionC"));
                test_question.set_optionD(set.getString("optionD"));
            }

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        return test_question;
    }

}