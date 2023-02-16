package tests;

import config.Log;
import services.Answer_question_map;

public class Answer_question_map_test {
    public static void main(String[] args) {
        Answer_question_map sq = new Answer_question_map();
        Log.console(sq.get_with_answer(4));

    }

}
