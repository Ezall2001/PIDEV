package tests;

import java.util.List;

import config.Log;
import entities.Answer;
import services.Answer_service;
import types.Service_response;

public class Question_test {
    public static void main(String[] args) {
        //     //add
        Answer_service r = new Answer_service();
        Answer r1 = new Answer("message1", 4);
        Log.console(r1);
        Service_response<Integer> add_response = r.add_answer(r1);
        if (check_error(add_response))
            return;
        Integer new_id = add_response.get_body();
        r1.set_id(new_id);

        //     Question_service q = new Question_service();
        //     Question q1 = new Question("najiba111", "gragba");
        //     Service_response<Integer> add_response = q.add_question(q1);
        //     if (check_error(add_response))
        //         return;
        //     Integer new_id = add_response.get_body();
        //     q1.set_id(new_id);
        //     //update
        Answer modification_answer = new Answer(new_id, "wessim", 4);
        Service_response<Integer> modify_response = r.modify_answer_by_id(r1, modification_answer);
        if (check_error(modify_response))
            return;

        //     Question modification_question = new Question(new_id, "wessim", "wessim1");
        //     Service_response<Integer> modify_response = q.modify_question_by_id(q1, modification_question);
        //     if (check_error(modify_response))
        //         return;
        //     //delete
        Service_response<Integer> delete_response = r.delete_answer_by_id(1);
        if (check_error(delete_response))
            return;

        //     Service_response<Integer> delete_response = q.delete_question_by_id(1);
        //     if (check_error(delete_response))
        //         return;
        //     //find-by-id
        Service_response<List<Answer>> find_response = r.find_answer_by_id(2);
        if (check_error(find_response))
            return;
        Log.console(find_response.get_body());

        //     Service_response<List<Question>> find_response = q.find_question_by_id(2);
        //     if (check_error(find_response))
        //         return;
        //     Log.console(find_response.get_body());

    }

    public static <T> Boolean check_error(Service_response<T> response) {
        if (response.get_error() != null) {
            Log.console(response.get_error());
            return true;
        }

        return false;
    }
}
