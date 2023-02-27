package utils;

import entities.Answer;
import entities.Question;

public class Shared_model {
    private static Shared_model instance;

    private Question user;

    private Shared_model() {
    }

    public static Shared_model getInstance() {
        if (instance == null) {
            instance = new Shared_model();
        }
        return instance;
    }

    public Question getUser() {
        return user;
    }

    public void setUser(Question user) {
        this.user = user;
    }

}
