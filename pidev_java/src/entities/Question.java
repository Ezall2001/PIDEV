package entities;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class Question {
    int id;
    private String title;
    private String description;
    private User user;
    private Subject subject;

    //  one to many relationship
    private List<Answer> answers = new ArrayList<>();

    // constructors
    public Question() {
    }

    public Question(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;

    }

    public Question(String title, String description) {

        this.title = title;
        this.description = description;

    }

    public Question(String title, String description, List<Answer> answers) {

        this.title = title;
        this.description = description;
        this.answers = answers;

    }

    //  getters and setters

    public int get_id() {
        return id;

    }

    public String get_title() {
        return title;

    }

    public String getTitle() {
        return title;

    }

    public String get_description() {
        return description;

    }

    public void set_title(String title) {
        this.title = title;

    }

    public void setTitle(String title) {
        this.title = title;

    }

    public void set_description(String description) {
        this.description = description;

    }

    public void set_id(int id) {
        this.id = id;

    }

    //  methodes one to many
    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public User get_user() {
        return user;
    }

    public void set_user(User user) {
        this.user = user;
    }

    //one to many for subject
    public Subject get_subject() {
        return subject;
    }

    public void set_subject(Subject subject) {
        this.subject = subject;
    }

    // toString
    @Override
    public String toString() {
        return "Question{" + ", titre=" + title + ", description=" + description
                + '}';
    }

}