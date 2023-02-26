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
        String sql = "delete from ,courses where course_name=?";
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

}
