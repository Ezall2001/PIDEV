package utils;

import entities.*;

public class Shared_model_nour {

    private static Shared_model_nour instance;
    private Question question = null;
    private Test test = null;
    private Subject subject = null;
    private Course course = null;

    private Shared_model_nour() {
    }

    public static Shared_model_nour getInstance() {
        if (instance == null)
            instance = new Shared_model_nour();

        return instance;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Question get_question() {
        return question;
    }

    public void set_question(Question question) {
        this.question = question;
    }

    public Test get_test() {
        return test;
    }

    public void set_test(Test test) {
        this.test = test;
    }

}
