package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import config.Jdbc_connection;
import config.Log;
import entities.Answer;
import entities.Question;

public class QuestionService implements InterfaceService<Question> {
    Connection cnx;

    public QuestionService() {
        cnx = Jdbc_connection.getInstance();
    }

    public void add() {
        Question q = new Question();
        Scanner sc = new Scanner(System.in);
        Log.console("Entrer le titre de votre question");
        q.setTitle(sc.nextLine());
        Log.console("Entrer la description de votre question");
        q.setDescription(sc.nextLine());
        try {
            PreparedStatement pst = cnx.prepareStatement("insert into questions(title,description) " + "values(?,?)");
            pst.setString(1, q.getTitle());
            pst.setString(2, q.getDescription());

            int x = pst.executeUpdate();
            pst.close();

            if (x == 1) {
                Log.console("la question est ajoutée avec succés");
            }
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    @Override
    public List<Question> get_All() {
        List<Question> questions = new ArrayList<>();
        try {
            String sql = "select * from questions";
            java.sql.Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Question q = new Question();

                q.setTitle(rs.getString("title"));
                q.setDescription(rs.getString(3));

                questions.add(q);
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return questions;
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM questions WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, id);
            int x = statement.executeUpdate();
            statement.close();
            if (x == 1) {
                Log.console("Question est supprimée");
                Log.console(get_All());
            } else {
                Log.console("question n'exite pas");
            }
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    @Override
    public List<Question> get_by_id(int id) {
        List<Question> questions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM questions WHERE id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Question q = new Question();

                q.setTitle(rs.getString("title"));
                q.setDescription(rs.getString(3));

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
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, id);
            int x = statement.executeUpdate();
            statement.close();
            if (x == 1) {
                Log.console("la question est modifier avec succés");
            }
            Log.console(get_All());

        } catch (SQLException ex) {
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
                int question_id = rs.getInt("question_id");
                int id_rep = rs.getInt(4);
                Answer r = new Answer(id_rep, message, question_id);
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

}
