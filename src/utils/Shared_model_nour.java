package utils;

import entities.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Shared_model_nour {

    private static Shared_model_nour instance;
    private Question question = null;
    private Test test = null;
    private Subject subject = null;
    private Course course = null;
    private Scene scene = null;
    private Stage stage = null;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

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

    public void set_test_subject(Test test, Subject subject) {
        test.setSubject(subject);
    }

    public void set_test_course(Test test, Course course) {
        test.setCourse(course);
    }
}
