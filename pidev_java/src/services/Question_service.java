package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Question;
import types.Service_response;

public class Question_service extends Base_service {

    public Question_service() {
        super("service_name");
    }

    public Service_response<Integer> add_question(Question question) {
        return super.add(question, null);
    }

    public Service_response<Integer> modify_question_by_id(Question search_question, Question modification_question) {
        return super.modify(search_question, modification_question, Arrays.asList("id"), null);
    }

    public Service_response<Integer> delete_question_by_id(Question question) {
        return super.delete(question, Arrays.asList("id"));
    }

    public Service_response<List<Question>> find_question_by_id(Question question) {
        return super.find(question, Arrays.asList("id"));
    }

    public Service_response<List<Question>> find_question_by_id(Integer id) {
        Question question = new Question();
        question.set_id(id);
        return find_question_by_id(question);
    }

    public Service_response<Integer> delete_question_by_id(Integer id) {
        Question question = new Question();
        question.set_id(id);
        return delete_question_by_id(question);
    }

}
