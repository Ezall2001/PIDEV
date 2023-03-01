package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Answer;
import entities.Question;
import entities.User;
import utils.Jdbc_connection;
import utils.Log;
import utils.Service_Fx;

public class Answer_service {
    Connection cnx;
    Scanner sc = new Scanner(System.in);

    public Answer_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public void add(Answer r, Question q, User u) {
        Service_Fx sf = new Service_Fx();

        try {

            PreparedStatement pst = cnx
                    .prepareStatement("insert into answers(message, question_id, user_id) values(?,?,?) ");
            pst.setString(1, r.getMessage());
            pst.setInt(2, q.get_id());
            pst.setInt(3, u.get_id());
            if (r.getMessage() == "") {
                throw new Exception("vous devez remplir remplir les champs");
            } else if (sf.is_matching(r.getMessage())) {

                throw new Exception("vous n'avez pas le droit d utiliser ce genre des mots");

            } else {
                pst.executeUpdate();
                pst.close();
            }

        } catch (Exception ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Answer> get_all() {
        List<Answer> answers = new ArrayList<>();
        User_service u = new User_service();

        try {
            String sql = "select * from answers";
            java.sql.Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Answer r = new Answer();
                r.setId(rs.getInt(rs.getInt("id")));
                r.setMessage(rs.getString("message"));
                r.set_vote_nb(rs.getInt("vote_nb"));
                r.set_user(u.find_by_id(rs.getInt("user_id")));

                answers.add(r);

                Log.console(rs.getInt(rs.getInt("id")));
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return answers;
    }

    public void delete(Answer r) {
        try {
            Log.console(r.getId());
            String sql = "DELETE FROM answers WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, r.getId());
            statement.executeUpdate();
            statement.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Answer> get_by_id(int id_rep) {
        List<Answer> Answers = new ArrayList<>();
        User_service u = new User_service();
        try {
            String sql = "SELECT * FROM answers WHERE id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id_rep);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Answer r = new Answer();

                r.setMessage(rs.getString("message"));
                r.set_vote_nb(rs.getInt("vote_nb"));
                r.setId(rs.getInt("id"));
                r.set_user(u.find_by_id(rs.getInt("user_id")));

                Answers.add(r);
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        Log.console(Answers);
        return Answers;

    }

    public void update(int id, String message) {
        Service_Fx sf = new Service_Fx();
        try {

            String sql = "UPDATE answers SET message=? WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            if (message == "") {
                throw new Exception("vous devez remplir remplir les champs");
            } else if (sf.is_matching(message)) {

                throw new Exception("vous n'avez pas le droit d utiliser ce genre des mots");

            } else {
                statement.setString(1, message);
                statement.setInt(2, id);
                int x = statement.executeUpdate();
                statement.close();
                if (x == 1) {
                    Log.console("la reponse est modifier avec succés");
                }
            }
            //Log.console(get_all());

        } catch (Exception ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Answer> trie(Question q) {
        List<Answer> answers = new ArrayList<>();
        HashMap<Question, List<Answer>> map = new HashMap<Question, List<Answer>>();
        Question_service qs = new Question_service();
        map = qs.get_with_answer(q.get_id());
        for (HashMap.Entry<Question, List<Answer>> entry : map.entrySet()) {

            answers = entry.getValue();
            //Log.console(value.get(0));

            //Log.console(value);

        }

        answers = answers.stream().sorted((a, b) -> b.get_vote_nb() - a.get_vote_nb()).collect(Collectors.toList());
        return answers;

    }

    public List<Answer> filtrer_recent(Question q) {
        List<Answer> answers = new ArrayList<>();
        HashMap<Question, List<Answer>> map = new HashMap<Question, List<Answer>>();
        Question_service qs = new Question_service();
        map = qs.get_with_answer(q.get_id());
        for (HashMap.Entry<Question, List<Answer>> entry : map.entrySet()) {

            answers = entry.getValue();
            //Log.console(value.get(0));

            //Log.console(value);

        }

        answers = answers.stream().sorted((a, b) -> b.getId() - a.getId()).collect(Collectors.toList());

        return answers;

    }

    public int incri(Answer a) {
        int nb_vote = 0;
        try {
            String sql = "SELECT * FROM answers WHERE id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, a.getId());
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {

                a.set_vote_nb(rs.getInt("vote_nb"));
                nb_vote = a.get_vote_nb();

                nb_vote = nb_vote + 1;
                a.set_vote_nb(nb_vote);

                update(a.getId(), nb_vote);

            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        return nb_vote;

    }

    public int déc(Answer a) {
        int nb_vote = 0;
        try {
            String sql = "SELECT * FROM answers WHERE id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, a.getId());
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {

                a.set_vote_nb(rs.getInt("vote_nb"));
                nb_vote = a.get_vote_nb();

                nb_vote = nb_vote - 1;
                a.set_vote_nb(nb_vote);

                update2(a.getId(), nb_vote);

            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        return nb_vote;
    }

    public void update(int id, int nb_vote) {
        try {

            String sql = "UPDATE answers SET vote_nb=vote_nb+1 WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            //statement.setInt(1, nb_vote);
            statement.setInt(1, id);
            int x = statement.executeUpdate();
            statement.close();
            if (x == 1) {
                Log.console("la reponse est modifier avec succés");
            }
            //Log.console(get_all());

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public void update2(int id, int nb_vote) {
        try {

            String sql = "UPDATE answers SET vote_nb=vote_nb-1 WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            //statement.setInt(1, nb_vote);
            statement.setInt(1, id);
            int x = statement.executeUpdate();
            statement.close();
            if (x == 1) {
                Log.console("la reponse est modifier avec succés");
            }
            //Log.console(get_all());

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Answer> get_by_vote(int id_rep) {
        List<Answer> Answers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM answers WHERE id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id_rep);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Answer r = new Answer();

                r.setMessage(rs.getString("message"));
                r.set_vote_nb(rs.getInt("vote_nb"));
                r.setId(rs.getInt("id"));

                Answers.add(r);
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
        Log.console(Answers);
        return Answers;

    }

}
