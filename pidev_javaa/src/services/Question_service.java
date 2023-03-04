package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import entities.Answer;
import entities.Question;
import utils.Jdbc_connection;
import utils.Log;

public class Question_service {
    Connection cnx;

    public Question_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public void add(Question q) {

        try {
            PreparedStatement pst = cnx.prepareStatement("insert into questions(title,description) values(?,?)");
            pst.setString(1, q.get_title());
            pst.setString(2, q.get_description());

            if (q.get_title() == "" || q.get_description() == "") {
                throw new Exception("vous devez remplir remplir les champs");
            } else if (q.get_title().length() > 40) {

                throw new Exception("vous devez minimiser votre question");
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

}
