package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Answer;
import entities.Question;
import utils.Jdbc_connection;
import utils.Log;

public class Answer_service {
    Connection cnx;
    Scanner sc = new Scanner(System.in);

    public Answer_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public void add(Answer r, Question q) {

        try {

            PreparedStatement pst = cnx.prepareStatement("insert into answers(message, question_id) values(?,?) ");
            pst.setString(1, r.getMessage());
            pst.setInt(2, q.get_id());

            int x = pst.executeUpdate();
            pst.close();

            if (x == 1) {
                Log.console("la réponse est ajoutée avec succés");
            }
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    public List<Answer> get_all() {
        List<Answer> answers = new ArrayList<>();
        try {
            String sql = "select * from answers";
            java.sql.Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Answer r = new Answer();
                r.setId(rs.getInt(rs.getInt("id")));
                r.setMessage(rs.getString("message"));
                r.set_vote_nb(rs.getInt("vote_nb"));

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

    public void update(int id, String message) {
        try {

            String sql = "UPDATE answers SET message=? WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setString(1, message);
            statement.setInt(2, id);
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

    public List<Answer> trie() {
        List<Answer> answers = new ArrayList<>();

        answers = get_all();
        answers = answers.stream().sorted((a, b) -> b.get_vote_nb() - a.get_vote_nb()).collect(Collectors.toList());
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
                Log.console("hobbi" + nb_vote);

                nb_vote = nb_vote + 1;
                a.set_vote_nb(nb_vote);
                Log.console("hobbi" + nb_vote);
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
                Log.console("hobbi" + nb_vote);

                nb_vote = nb_vote - 1;
                a.set_vote_nb(nb_vote);
                Log.console("hobbi" + nb_vote);
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
