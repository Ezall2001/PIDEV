package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import config.Jdbc_connection;
import config.Log;
import entities.Answer;

public class AnswerService implements InterfaceService<Answer> {
    Connection cnx;
    Scanner sc = new Scanner(System.in);

    public AnswerService() {
        cnx = Jdbc_connection.getInstance();
    }

    public void add_by_id(int id) {
        Answer r = new Answer();
        Log.console("Entrer votre réponse");
        r.setMessage(sc.nextLine());

        r.setId_question(id);
        try {
            PreparedStatement pst = cnx.prepareStatement("insert into answers(message, question_id) " + "values(?,?)");
            pst.setString(1, r.getMessage());
            pst.setInt(2, r.getId_question());

            int x = pst.executeUpdate();
            pst.close();

            if (x == 1) {
                Log.console("la réponse est ajoutée avec succés");
            }
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    @Override
    public List<Answer> get_All() {
        List<Answer> answers = new ArrayList<>();
        try {
            String sql = "select * from answers";
            java.sql.Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Answer r = new Answer();

                r.setMessage(rs.getString("message"));

                answers.add(r);
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return answers;
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM answers WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, id);
            int x = statement.executeUpdate();
            statement.close();
            if (x == 1) {
                Log.console("réponse est supprimée");
                Log.console(get_All());
            } else {
                Log.console("réponse n'exite pas");
            }
        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

    @Override
    public List<Answer> get_by_id(int id) {
        List<Answer> Answers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM answers WHERE id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Answer r = new Answer();

                r.setMessage(rs.getString("message"));

                Answers.add(r);
            }
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

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
            Log.console(get_All());

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

    }

}
