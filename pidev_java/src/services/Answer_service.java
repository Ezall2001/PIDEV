package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import entities.Answer;
import entities.User;
import entities.Question;
import utils.Jdbc_connection;
import utils.Log;
import utils.String_helpers;

public class Answer_service {
    Connection cnx;

    public Answer_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public void validate_input(Answer answer) throws Exception {

        if (answer.get_message().isEmpty())
            throw new Exception("vous devez remplir remplir les champs");

        String bad_word_message = String_helpers.check_bad_word(answer.get_message());

        if (bad_word_message != null)
            throw new Exception("vous n'avez pas le droit d utiliser ce genre des mots" + bad_word_message);
    }

    public Question find_by_id_question(Question question) {

        try {
            String sql = "SELECT answers.id, answers.message, answers.vote_nb, users.first_name, users.last_name, users.id as user_id FROM answers LEFT JOIN users ON answers.user_id = users.id LEFT JOIN questions ON answers.question_id = questions.id where question_id=?";

            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, question.get_id());

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Answer answer = new Answer();
                answer.set_id(result.getInt("id"));
                answer.set_message(result.getString("message"));
                answer.set_vote_nb(result.getInt("vote_nb"));

                User user = new User();
                user.set_id(result.getInt("user_id"));
                user.set_first_name(result.getString("first_name"));
                user.set_last_name(result.getString("last_name"));

                answer.set_user(user);

                question.get_answers().add(answer);

            }

            result.close();
            stmt.close();

        } catch (Exception e) {
            Log.file(e.getMessage());
        }

        return question;
    }

    public void update(Answer answer) {

        try {
            validate_input(answer);

            String sql = "UPDATE answers SET message=? WHERE id=?";
            PreparedStatement stmt = cnx.prepareStatement(sql);

            stmt.setString(1, answer.get_message());
            stmt.setInt(2, answer.get_id());
            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

    public Answer add(Answer answer) {
        String sql = "insert into answers(message,question_id,user_id) values(?,?,?)";

        try {
            validate_input(answer);

            PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, answer.get_message());
            stmt.setInt(2, answer.get_question().get_id());
            stmt.setInt(3, answer.get_user().get_id());

            stmt.executeUpdate();

            ResultSet generated_id = stmt.getGeneratedKeys();
            generated_id.next();
            answer.set_id(generated_id.getInt(1));

            stmt.close();

            return answer;
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

        return null;
    }

    public void delete(Answer answer) {
        try {
            String sql = "DELETE FROM answers WHERE id=?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, answer.get_id());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

    }

    public void update_vote(Answer answer) {
        try {
            String sql = "UPDATE answers SET vote_nb=? WHERE id=?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, answer.get_vote_nb());
            stmt.setInt(2, answer.get_id());

            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

}
