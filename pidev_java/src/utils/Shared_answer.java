package utils;

import entities.Answer;

public class Shared_answer {
    private static Shared_answer instance;

    private Answer answer;

    private Shared_answer() {
    }

    public static Shared_answer getInstance() {
        if (instance == null) {
            instance = new Shared_answer();
        }
        return instance;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

}
