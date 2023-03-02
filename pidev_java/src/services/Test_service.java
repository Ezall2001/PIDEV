package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import entities.Course;
import entities.Subject;
import entities.Test;
import entities.Test_qs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import types.check;
import utils.*;

public class Test_service {
    Connection cnx;

    public Test_service() {
        cnx = Jdbc_connection.getInstance();
    }

    // virgin test 
    public void add(Test test) {
        try {
            String sql = "insert into tests (type,min_points,duration)"
                    + "values (?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            //!
            int duration = test.get_duration();
            int min_points = test.get_min_points();
            String type = test.getType();
            //!
            if (check.IntValidator(min_points) && check.IntValidator(duration) && check.StringValidator(type)) {
                ste.setString(1, type);
                ste.setInt(2, min_points);
                ste.setInt(3, duration);
                ste.executeUpdate();
                Log.console("test ajouté");
            } else
                Log.console("invalides");
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    // subject test
    public void add_with_subject(Test test) {
        try {
            String sql = "insert into tests (type,min_points,duration,id_subject)"
                    + "values (?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            //!
            int duration = test.get_duration();
            int min_points = test.get_min_points();
            String type = test.getType();
            //!
            if (check.IntValidator(min_points) && check.IntValidator(duration) && check.StringValidator(type)) {
                ste.setString(1, type);
                ste.setInt(2, min_points);
                ste.setInt(3, duration);
                ste.setInt(4, test.getSubject().get_id());
                ste.executeUpdate();
                Log.console("test ajouté");
            } else
                Log.console("invalides");
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    // course test
    public void add_with_course(Test test) {
        try {
            String sql = "insert into tests (type,min_points,duration,id_course)"
                    + "values (?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            //!
            int duration = test.get_duration();
            int min_points = test.get_min_points();
            String type = test.getType();
            //!
            if (check.IntValidator(min_points) && check.IntValidator(duration) && check.StringValidator(type)) {
                ste.setString(1, type);
                ste.setInt(2, min_points);
                ste.setInt(3, duration);
                ste.setInt(4, test.getCourse().get_id_c());
                ste.executeUpdate();
                Log.console("test ajouté");
            } else
                Log.console("invalides");
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
            //!
            int duration = test.get_duration();
            int min_points = test.get_min_points();
            String type = test.getType();
            //!
            if (duration != 0 && min_points != 0 && type.length() > 0) {
                ste.setString(1, type);
                ste.setInt(2, min_points);
                ste.setInt(3, duration);
                ste.setInt(4, test.get_id());
                ste.executeUpdate();
                Log.console("test modifié");
            } else {
                Log.console("invalides");
            }

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

    public ObservableList<Test> get_all2() {
        ObservableList<Test> tests = FXCollections.observableArrayList();
        try {
            String sql = "select * from tests";
            Statement ste = cnx.createStatement();
            ResultSet set = ste.executeQuery(sql);
            while (set.next()) {
                Test t = new Test(
                        Integer.parseInt(set.getString("id")),
                        Integer.parseInt(set.getString("min_points")),
                        Integer.parseInt(set.getString("duration")),
                        set.getString("type"));
                tests.add(t);
            }
        } catch (SQLException ex) {
            Log.console(ex);

        }
        return tests;
    }

    // search by type : cours or matiere
    public ObservableList<Test> search_test(String type) {
        ObservableList<Test> tests = FXCollections.observableArrayList();
        try {
            String sql = "select * from tests where type like '%" + type + "%' ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ResultSet set = ste.executeQuery(sql);
            while (set.next()) {
                Test t = new Test(
                        Integer.parseInt(set.getString("id")),
                        Integer.parseInt(set.getString("min_points")),
                        Integer.parseInt(set.getString("duration")),
                        set.getString("type"));
                tests.add(t);
            }

        } catch (SQLException ex) {
            Log.console(ex);
        }
        return tests;
    }

    // one to many using observableList
    public ObservableList<Test_qs> get_with_questions_list(int id) {
        ObservableList<Test_qs> questions = FXCollections.observableArrayList();
        try {
            // Retrieve the test from the database
            String sql = "SELECT * FROM tests where id=? ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();
            //!
            ObservableList<Test> tests = FXCollections.observableArrayList();
            while (set.next()) {
                Test t = new Test(
                        set.getInt(1),
                        set.getInt(3),
                        set.getInt(4),
                        set.getString("type"));
                tests.add(t);
            }

            // Retrieve the questions for the test
            for (Test test : tests) {
                sql = "SELECT * FROM test_qs WHERE id_test =?";
                ste = cnx.prepareStatement(sql);
                ste.setInt(1, id);
                set = ste.executeQuery();

                while (set.next()) {
                    Test_qs question = new Test_qs(
                            Integer.parseInt(set.getString("id")),
                            Integer.parseInt(set.getString("question_number")),
                            set.getString("question"),
                            set.getString("correct_option"),
                            set.getString("optionA"),
                            set.getString("optionB"),
                            set.getString("optionC"),
                            set.getString("optionD"));
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public Subject get_test_subject(int id) {

        Subject subject = new Subject();

        try {
            // Retrieve the subject and its test from the database
            String sql = "SELECT * FROM subject INNER JOIN tests ON subject.id = tests.id_subject where tests.id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();

            while (set.next()) {

                Test test = new Test(
                        set.getInt("id"),
                        set.getInt("min_points"),
                        set.getInt("duration"),
                        set.getString("type"));

                test.setSubject(subject);

                subject.set_id(set.getInt("id"));
                subject.set_subject_name(set.getString("subject_name"));
                subject.set_description(set.getString("description"));
                subject.set_level(set.getString("niveau"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }

    public Course get_test_course(int id) {

        Course course = new Course();

        try {
            // Retrieve the subject and its test from the database
            String sql = "SELECT * FROM courses INNER JOIN tests ON courses.id_c = tests.id_course where tests.id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();

            while (set.next()) {

                Test test = new Test(
                        set.getInt("id"),
                        set.getInt("min_points"),
                        set.getInt("duration"),
                        set.getString("type"));

                test.setCourse(course);

                course.set_id_c(set.getInt("id_c"));
                course.set_course_name(set.getString("course_name"));
                course.set_description_c(set.getString("description_c"));
                course.set_difficulty(set.getString("difficulty"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    public List<Test> sort_tests_by_id() {
        List<Test> tests = new ArrayList<>();
        tests = get_all();
        tests = tests.stream().sorted((a, b) -> b.get_min_points() - a.get_min_points())
                .collect(Collectors.toList());
        return tests;

    }
}
