package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import utils.Jdbc_connection;
import utils.Log;
import entities.Course;
import entities.Test;
import entities.Test_qs;
import entities.Course.Difficulty;

public class Course_Service {

    Connection cnx;

    public Course_Service() {

        cnx = Jdbc_connection.getInstance();

    }

    public void add_course(Course course) {
        try {
            String sql = "insert into courses (id_c,course_name,description_c,difficulty,id_subject)"
                    + "values (?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, course.get_id_c());
            ste.setString(2, course.get_course_name());
            ste.setString(3, course.get_description_c());
            ste.setString(4, course.get_difficulty_String());
            ste.setInt(5, course.getSubject().get_id());
            ste.executeUpdate();
            Log.console("Cours ajouté");
        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }
    }

    public void update(int id_c, String course_name) {

        try {
            String sql = "UPDATE courses SET course_name=?  WHERE id_c=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(2, id_c);

            statement.setString(1, course_name);
            int x = statement.executeUpdate();
            statement.close();
            if (x == 1)
                Log.console("la  matiere est modifier avec succés");

        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }
    }

    public List<Course> get_all() {
        List<Course> courses = new ArrayList<>();
        try {
            String sql = "select * from courses";
            Statement ste = cnx.createStatement();
            ResultSet result = ste.executeQuery(sql);
            Difficulty diff = Difficulty.valueOf(result.getString("difficulty"));
            while (result.next()) {

                Course new_course = new Course(result.getInt(1), result.getString(2),
                        result.getString(3), diff);
                courses.add(new_course);

            }
        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }
        return courses;
    }

    public void delete_course(Course course) {
        String sql = "delete from courses where course_name=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(2, course.get_course_name());

            ste.executeUpdate();

        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }
    }

    public List<Course> get_by_id(int id_c) {
        List<Course> courses = new ArrayList<>();
        try {
            String sql = "SELECT * FROM courses WHERE id_c=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id_c);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Course new_Course = new Course();

                new_Course.set_course_name(rs.getString("course_name"));
                new_Course.set_description_c(rs.getString(3));

                courses.add(new_Course);
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }

        return courses;

    };

    public List<Course> trier() {
        List<Course> courses = new ArrayList<Course>();
        courses = get_all();
        courses = courses.stream()
                .sorted(Comparator.comparing(Course::get_course_name))

                .collect(Collectors.toList());
        return courses;
    }

    // ? 

    public Test get_course_test(int id) {

        Test test = new Test();
        try {
            // Retrieve the subject and its test from the database
            String sql = "SELECT * FROM courses INNER JOIN tests ON courses.id_c = tests.id_course where courses.id_c=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();

            while (set.next()) {

                Course course = new Course(
                        set.getInt("id_c"),
                        set.getString("course_name"),
                        set.getString("description_c"),
                        Difficulty.valueOf(set.getString("difficulty")));

                test.set_id(set.getInt("id"));
                test.set_min_points(set.getInt("min_points"));
                test.set_duration(set.getInt("duration"));
                test.setType(set.getString("type"));
                test.setCourse(course);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return test;
    }

    public Course get_by_id_2(int id) {

        Course course = new Course();
        try {
            String sql = "select * from courses where id_c=? ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet set = ste.executeQuery();
            if (set.next()) {
                course.set_id_c(id);
                course.set_course_name(set.getString("course_name"));
                course.set_description_c(set.getString("description_c"));
                course.set_difficulty(set.getString("difficulty"));
            }

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        return course;

    }

    public List<Test_qs> get_course_test_questions(int id) {

        List<Test_qs> questions_list = new ArrayList<Test_qs>();
        Course course = get_by_id_2(id);

        try {
            // Retrieve the course and its test_question from the database

            String sql = "SELECT test_qs.id, test_qs.question_number, test_qs.optionA,test_qs.optionB, test_qs.optionC, test_qs.optionD, test_qs.correct_option, "
                    +
                    "test_qs.question, courses.course_name from test_qs INNER JOIN tests ON tests.id_course = test_qs.id_course "
                    +
                    "JOIN courses ON tests.id_course = courses.id_c where test_qs.id_course =? ORDER by test_qs.question_number  ";

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

    public int count_course_questions(int id) {

        int sum = 0;
        try {
            String sql = "SELECT count(test_qs.id_course) as sum_question from test_qs INNER JOIN courses ON test_qs.id_course = courses.id_c where test_qs.id_course=? ";
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
