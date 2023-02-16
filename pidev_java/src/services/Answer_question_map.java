package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import types.Service_response;
import config.Jdbc_connection;
import config.Log;
import entities.Answer;
import entities.Question;

public class Answer_question_map {
    private List<Answer> answers = new ArrayList<>();
    Connection cnx;

    public Answer_question_map() {
        cnx = Jdbc_connection.getInstance();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public HashMap<Question, List<Answer>> get_with_answer(Integer id) {
        HashMap<Question, List<Answer>> map = new HashMap<Question, List<Answer>>();
        Question_service qs = new Question_service();
        Service_response<List<Question>> q = qs.find_question_by_id(id);
        List<Question> questionList = q.get_body();
        List<Answer> answers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM question LEFT JOIN answer ON question.id = answer.question_id WHERE question.id=? ";
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
            map.put(questionList.get(0), answers);
            rs.close();
            ste.close();

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }

        return map;
    }

}
