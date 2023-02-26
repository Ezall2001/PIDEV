package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.mysql.cj.jdbc.BlobFromLocator;
import com.mysql.cj.protocol.Message;

import entities.Answer;
import entities.Question;
import entities.Subject;
import utils.Jdbc_connection;
import utils.Log;

public class Question_service {
    Connection cnx;

    public Question_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public void add(Question q, Subject c) {

        try {
            PreparedStatement pst = cnx
                    .prepareStatement("insert into questions(title,description,subject_id) values(?,?,?)");
            pst.setString(1, q.get_title());
            pst.setString(2, q.get_description());
            pst.setInt(3, c.get_id());

            if (q.get_title() == "" || q.get_description() == "") {
                throw new Exception("vous devez remplir remplir les champs");
            } else if (q.get_title().length() > 40) {

                throw new Exception("vous devez minimiser votre question");
            } else if (is_matching(q.getTitle()) || is_matching(q.get_description())) {

                throw new Exception("vous n'avez pas le droit d utiliser ce genre des mots");

            } else {
                pst.executeUpdate();
                pst.close();
                Log.console("la question est ajoutée avec succés");
            }
        } catch (Exception ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Question> get_all() {
        List<Question> questions = new ArrayList<>();
        Statement ste = null;
        ResultSet rs = null;

        try {
            String sql = "select * from questions";
            ste = cnx.createStatement();
            rs = ste.executeQuery(sql);
            while (rs.next()) {
                Question q = new Question();
                q.set_id(rs.getInt(1));
                q.set_title(rs.getString("title"));
                q.set_description(rs.getString(3));

                questions.add(q);
            }

        } catch (SQLException e) {
            Log.console(e.getMessage());
        }
        try {
            rs.close();
            ste.close();
        } catch (SQLException e) {
            Log.console(e.getMessage());
        }

        return questions;
    }

    public void delete(Question qs) {
        try {

            String sql = "DELETE FROM questions WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, qs.get_id());

            int x = statement.executeUpdate();

            statement.close();
            if (x == 1) {
                Log.console("Question est supprimée");
                Log.console(get_all());
            } else {
                Log.console("question n'exite pas");
            }
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Question> get_by_id(int id) {
        List<Question> questions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM questions WHERE id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Question q = new Question();

                q.set_title(rs.getString("title"));
                q.set_description(rs.getString(3));

                questions.add(q);
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return questions;

    };

    public void update(int id, String title, String description) {

        try {
            String sql = "UPDATE questions SET title=?, description=? WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            if (title == "" || description == "") {
                throw new Exception("vous devez remplir remplir les champs");
            } else if (title.length() > 40) {
                throw new Exception("vous devez minimiser votre question");
            } else if (is_matching(title) || is_matching(description)) {

                throw new Exception("vous n'avez pas le droit d utiliser ce genre des mots");

            } else {
                statement.setString(1, title);
                statement.setString(2, description);
                statement.setInt(3, id);
                int x = statement.executeUpdate();
                statement.close();
                if (x == 1)
                    Log.console("la question est modifier avec succés");
            }

        } catch (Exception ex) {
            Log.console(ex.getMessage());
        }
    }

    public HashMap<Question, List<Answer>> get_with_answer(int id) {
        HashMap<Question, List<Answer>> map = new HashMap<Question, List<Answer>>();
        List<Question> q = get_by_id(id);
        List<Answer> answers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM questions LEFT JOIN answers ON questions.id = answers.question_id WHERE questions.id=? ";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                String message = rs.getString("message");
                int id_rep = rs.getInt(4);
                Answer r = new Answer(id_rep, message);
                answers.add(r);
            }
            map.put(q.get(0), answers);
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return map;
    }

    public List<Question> filter_qs(int choice) {
        List<Question> questions = new ArrayList<>();
        questions = get_all();
        switch (choice) {
            // filter by subject

            case 1:

                //
                break;
            // recent
            case 2:
                questions = questions.stream()
                        .sorted((a, b) -> b.get_id() - a.get_id())
                        .collect(Collectors.toList());

                break;

            default:
                break;
        }
        return questions;

    }

    public boolean is_matching(String str) {
        boolean checked = true;
        try {
            //Path fileName = Path.of("D:\\pidev-2023\\PIDEV\\pidev_java\\src\\services\\words-forbidden.txt");
            //Path fileName = Path.of("./words-forbidden.txt");
            Path fileName = Paths.get("src/services/words-forbidden.txt");

            String str1 = Files.readString(fileName);
            //Log.console(str1);

            Pattern pattern = Pattern.compile(str1, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);

            if (matcher.find()) {
                checked = true;

            } else {
                checked = false;
            }
        } catch (IOException e) {
            Log.console(e.getMessage());
        }

        return checked;
    }

    // public HashMap<Question, List<Subject>> get_with_subject(int questionId) {
    //     HashMap<Question, List<Subject>> map = new HashMap<>();
    //     List<Subject> subjects = new ArrayList<>();

    //     try {
    //         String sql = "SELECT questions.id, questions.title, subject.subject_name, subject.id " +
    //                 "FROM questions " +
    //                 "LEFT JOIN subject ON questions.subject_id = subject.id " +
    //                 "WHERE questions.id = ?";
    //         PreparedStatement stmt = cnx.prepareStatement(sql);
    //         stmt.setInt(1, questionId);
    //         ResultSet rs = stmt.executeQuery();

    //         while (rs.next()) {
    //             String questionTitle = rs.getString("title");
    //             String subjectName = rs.getString("subject_name");
    //             int subjectId = rs.getInt("id");
    //             Subject subject = new Subject(subjectId, subjectName);
    //             subjects.add(subject);

    //             Question question = new Question(questionId, questionTitle);
    //             map.put(question, subjects);
    //         }

    //         rs.close();
    //         stmt.close();

    //     } catch (SQLException ex) {
    //         Log.console(ex.getMessage());
    //     }

    //     return map;
    // }

    public HashMap<String, String> get_with_subject(int questionId) {
        HashMap<String, String> map = new HashMap<>();

        try {
            String sql = "SELECT questions.title, subject.subject_name " +
                    "FROM questions " +
                    "LEFT JOIN subject ON questions.subject_id = subject.id " +
                    "WHERE questions.id = ?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String questionTitle = rs.getString("title");
                String subjectName = rs.getString("subject_name");
                map.put(questionTitle, subjectName);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return map;
    }

    /*SELECT questions.title, users.first_name
    FROM questions
    LEFT JOIN users ON questions.user_id = users.id
    WHERE questions.id = 39 */
    public HashMap<String, String> get_with_uesr(int questionId) {
        HashMap<String, String> map = new HashMap<>();

        try {
            String sql = "SELECT questions.title, users.first_name FROM questions LEFT JOIN users ON questions.user_id = users.id WHERE questions.id = ?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String questionTitle = rs.getString("title");
                String usertName = rs.getString("first_name");
                map.put(questionTitle, usertName);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return map;
    }

    //SELECT * FROM subject LEFT JOIN questions ON subject.id = questions.subject_id WHERE subject.id=41 ;
    public List<Question> get_subject(Subject s) {
        Subject_service service = new Subject_service();
        List<Question> questions = new ArrayList<>();
        //List<Subject> q = service.get_by_id(s.get_id());

        try {
            String sql = "SELECT * FROM subject LEFT JOIN questions ON subject.id = questions.subject_id WHERE subject.id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, s.get_id());
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                int id_rep = rs.getInt(5);
                Question r = new Question(id_rep, title, description);
                questions.add(r);
            }

            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return questions;
    }

}