package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import utils.*;
import entities.Subject.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entities.Course;
import entities.Subject;
import entities.Test;
import entities.Test_qs;
import entities.Course.Difficulty;

public class Subject_Service {

    Connection cnx;

    public Subject_Service() {

        cnx = Jdbc_connection.getInstance();

    }

    public void add_subject(Subject subject) {
        try {
            String sqlCheck = "select count(*) from Subject where subject_name=?";
            PreparedStatement stmtCheck = cnx.prepareStatement(sqlCheck);
            stmtCheck.setString(1, subject.get_subject_name());
            ResultSet rs = stmtCheck.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                Log.console("Le nom du matiere " + subject.get_subject_name() + " est deja  exister.");
                return;
            }

            String sql = "insert into Subject(id,subject_name,description,niveau)"
                    + "values (?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);

            ste.setInt(1, subject.get_id());
            ste.setString(2, subject.get_subject_name());
            ste.setString(3, subject.get_description());
            ste.setString(4, subject.get_level_String());
            ste.executeUpdate();
            Log.console("Matiere ajoutée");
        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }
    }

    public List<Subject> get_all() {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "select * from subject";
            Statement ste = cnx.createStatement();
            ResultSet result = ste.executeQuery(sql);
            while (result.next()) {
                Subject new_subject = new Subject(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        Level.valueOf(result.getString("niveau")));

                subjects.add(new_subject);

            }
        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }
        return subjects;
    }

    public List<Subject> get_by_id(int id) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM subject  WHERE id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();

            while (rs.next()) {
                Subject new_subject = new Subject();
                new_subject.set_subject_name(rs.getString("subject_name"));
                new_subject.set_description(rs.getString("description"));
                new_subject.set_level(rs.getString("niveau"));

                subjects.add(new_subject);
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }

        return subjects;

    };

    public void delete_subject(Subject subject) {
        String sql = "delete from subject where subject_name=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, subject.get_subject_name());
            ste.executeUpdate();
        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }

    }

    public HashMap<Subject, List<Course>> get_with_course(int id) {
        HashMap<Subject, List<Course>> map = new HashMap<Subject, List<Course>>();
        List<Subject> q = get_by_id(id);
        List<Course> courses = new ArrayList<>();

        try {
            String sql = "SELECT * FROM subject LEFT JOIN course ON id_subject = course.id_subject WHERE id=? ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                String course_name = rs.getString("course_name");
                String description_c = rs.getString("description_c");
                Difficulty difficulty = Difficulty.valueOf(rs.getString("difficulty"));

                int id_c = rs.getInt(5);
                Course r = new Course(id_c, course_name, description_c, difficulty);
                courses.add(r);
            }
            map.put(q.get(0), courses);
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }
        return map;
    }

    public List<Subject> trier() {
        List<Subject> subjects = new ArrayList<Subject>();
        subjects = get_all();
        subjects = subjects.stream()
                .sorted(Comparator.comparing(Subject::get_subject_name))

                .collect(Collectors.toList());
        return subjects;
    }

    public Subject search_subject(String search_subject) {
        List<Subject> subjects = get_all();

        // Search for the subject in the list
        Optional<Subject> optionalSubject = subjects.stream()
                .filter(s -> s.get_subject_name().equals(search_subject))
                .findFirst();
        if (optionalSubject.isPresent()) {
            Log.console("La matiere " + search_subject + " est existe");
            return optionalSubject.get();
        } else {
            Log.console("La matiere " + search_subject + "  n'existe pas");
            return null;
        }
    }

    // ? ---------------------------  Nour's additions :

    // one to one : get subject's test
    public Test get_subject_test(int id) {

        Test test = new Test();
        try {
            // Retrieve the subject and its test from the database
            String sql = "SELECT * FROM subject INNER JOIN tests ON subject.id = tests.id_subject where subject.id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();

            while (set.next()) {

                Subject subject = new Subject(set.getInt("id"),
                        set.getString("subject_name"),
                        set.getString("description"),
                        Level.valueOf(set.getString("niveau")));

                test.set_id(set.getInt("id"));
                test.set_min_points(set.getInt("min_points"));
                test.set_duration(set.getInt("duration"));
                test.setType(set.getString("type"));
                test.setSubject(subject);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return test;
    }

    // this one returns a subject object (not a list)
    public Subject get_by_id_2(int id) {

        Subject subject = new Subject();
        try {
            String sql = "select * from subject where id=? ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();
            if (set.next()) {
                subject.set_id(id);
                subject.set_subject_name(set.getString("subject_name"));
                subject.set_description(set.getString("description"));
                subject.set_level(set.getString("niveau"));
            }

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        return subject;
    }

    // get subject test's questions

    public List<Test_qs> get_subject_test_questions(int id) {

        List<Test_qs> questions_list = new ArrayList<Test_qs>();
        Subject subject = get_by_id_2(id);

        try {
            // Retrieve the subject and its test_question from the database

            String sql = "SELECT test_qs.id, test_qs.question_number, test_qs.optionA,test_qs.optionB, test_qs.optionC, test_qs.optionD, test_qs.correct_option, "
                    +
                    "test_qs.question, subject.subject_name from test_qs INNER JOIN tests ON tests.id_subject = test_qs.id_subject "
                    +
                    "JOIN subject ON tests.id_subject = subject.id where test_qs.id_subject =? ";

            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();

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
                questions_list.add(question);
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return questions_list;
    }

    public int count_subject_questions(int id) {

        int sum = 0;
        try {
            String sql = "SELECT count(test_qs.id_subject) as sum_question from test_qs INNER JOIN subject ON test_qs.id_subject = subject.id where test_qs.id_subject=? ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();
            set.next();
            sum = set.getInt(1);

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return sum;
    }
}
// public ObservableList<Test> get_with_tests_list(int id) {
//     ObservableList<Test> tests = FXCollections.observableArrayList();
//     try {
//         // Retrieve the subject from the database
//         String sql = "SELECT * FROM subject where id=? ";
//         PreparedStatement ste = cnx.prepareStatement(sql);
//         ste.setInt(1, id);
//         ResultSet set = ste.executeQuery();
//         //!
//         ObservableList<Subject> subject = FXCollections.observableArrayList();
//         while (set.next()) {
//             Subject subj = new Subject(
//                     set.getInt(1),
//                     set.getString(2),
//                     set.getString(3),
//                     Level.valueOf(set.getString("niveau")));
//             subject.add(subj);
//         }

//         // Retrieve the tests for the subject
//         for (Subject sub : subject) {
//             sql = "SELECT * FROM tests WHERE id_subject =?";
//             ste = cnx.prepareStatement(sql);
//             ste.setInt(1, id);
//             set = ste.executeQuery();

//             while (set.next()) {
//                 Test x = new Test(
//                         Integer.parseInt(set.getString("id")),
//                         Integer.parseInt(set.getString("min_points")),
//                         Integer.parseInt(set.getString("duration")),
//                         set.getString("type"));
//                 tests.add(x);
//             }
//         }
//     } catch (SQLException e) {
//         e.printStackTrace();
//     }
//     return tests;
// }
