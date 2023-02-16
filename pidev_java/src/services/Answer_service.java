package services;

import java.util.Arrays;
import java.util.List;

import entities.Answer;
import types.Service_response;

public class Answer_service extends Base_service {

    public Answer_service() {
        super("service_name");
    }

    public Service_response<Integer> add_answer(Answer answer) {

        return super.add(answer, null);
    }

    public Service_response<Integer> modify_answer_by_id(Answer search_answer, Answer modification_answer) {
        return super.modify(search_answer, modification_answer, Arrays.asList("id"), null);
    }

    public Service_response<Integer> delete_answer_by_id(Answer answer) {
        return super.delete(answer, Arrays.asList("id"));
    }

    public Service_response<List<Answer>> find_question_by_id(Answer answer) {
        return super.find(answer, Arrays.asList("id"));
    }

    public Service_response<List<Answer>> find_answer_by_id(Integer id) {
        Answer answer = new Answer();
        answer.set_id(id);
        return find_question_by_id(answer);
    }

    public Service_response<Integer> delete_answer_by_id(Integer id) {
        Answer question = new Answer();
        question.set_id(id);
        return delete_answer_by_id(question);
    }

}
