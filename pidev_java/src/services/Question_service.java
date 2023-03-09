package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Answer;
import entities.Question;
import entities.Subject;
import entities.User;
import utils.Jdbc_connection;
import utils.Log;
import utils.String_helpers;

public class Question_service {
    Connection cnx;

    public Question_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public Question add(Question question) {
        String sql = "insert into questions(title,description,subject_id,user_id) values(?,?,?,?)";

        try {
            validate_input(question);

            PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, question.get_title());
            stmt.setString(2, question.get_description());
            stmt.setInt(3, question.get_subject().get_id());
            stmt.setInt(4, question.get_user().get_id());

            stmt.executeUpdate();

            ResultSet generated_id = stmt.getGeneratedKeys();
            generated_id.next();
            question.set_id(generated_id.getInt(1));

            stmt.close();

            return question;
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

        return null;
    }

    public List<Question> get_all() {
        List<Question> questions = new ArrayList<>();

        try {
            String sql = "SELECT questions.id, questions.title, questions.description, users.first_name, users.last_name, users.id as user_id , subjects.id as id_subject, subjects.name as name_subject FROM questions INNER JOIN subjects ON subjects.id = questions.subject_id LEFT JOIN users ON questions.user_id = users.id";
            Statement stmt = cnx.createStatement();
            ResultSet result = stmt.executeQuery(sql);

            while (result.next()) {
                Question question = new Question();
                question.set_id(result.getInt("id"));
                question.set_title(result.getString("title"));
                question.set_description(result.getString("description"));

                User user = new User();
                user.set_id(result.getInt("user_id"));
                user.set_first_name(result.getString("first_name"));
                user.set_last_name(result.getString("last_name"));

                Subject subject = new Subject();
                subject.set_id(result.getInt("id_subject"));
                subject.set_name(result.getString("name_subject"));

                question.set_user(user);
                question.set_subject(subject);

                questions.add(question);

                // fil get stmt nahi il indexes hothom il kol bil column names
                //stmt nbadelha chnw ? stmt aamil ctrl + f w mbaad ctrl + h bich tbadalhom il kol mara wahda

                // shhh
                // :)
            }

            result.close();
            stmt.close();

        } catch (Exception e) {
            Log.file(e.getMessage());
        }

        return questions;
    }

    public Question find_by_id(Question question) {

        try {
            String sql = "SELECT questions.title, questions.description, questions.id as question_id, answers.id as answer_id, answers.message, answers.vote_nb, users.first_name, users.last_name, users.id as user_id FROM questions LEFT JOIN users ON questions.user_id = users.id LEFT JOIN answers ON answers.question_id = questions.id where questions.id=?";

            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, question.get_id());

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                question.set_id(result.getInt("question_id"));
                question.set_title(result.getString("title"));
                question.set_description(result.getString("description"));
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

    public void delete(Question question) {
        try {

            String sql = "DELETE FROM questions WHERE id=?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, question.get_id());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

    }

    public void update(Question question) {

        try {
            validate_input(question);

            String sql = "UPDATE questions SET title=?, description=?, subject_id=? WHERE id=?";
            PreparedStatement stmt = cnx.prepareStatement(sql);

            stmt.setString(1, question.get_title());
            stmt.setString(2, question.get_description());
            stmt.setInt(3, question.get_subject().get_id());
            stmt.setInt(4, question.get_id());
            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

    public void validate_input(Question question) throws Exception {

        if (question.get_title().isEmpty() || question.get_description().isEmpty())
            throw new Exception("vous devez remplir remplir les champs");

        else if (question.get_title().length() > 200)
            throw new Exception("vous devez minimiser votre question");

        String bad_word_title = String_helpers.check_bad_word(question.get_title());
        String bad_word_description = String_helpers.check_bad_word(question.get_description());

        if (bad_word_title == null)
            bad_word_title = "";

        if (bad_word_description == null)
            bad_word_description = "";

        if (!bad_word_title.isEmpty() || !bad_word_description.isEmpty())
            throw new Exception("vous n'avez pas le droit d utiliser ce genre des mots" + bad_word_title
                    + bad_word_description);

    }

}