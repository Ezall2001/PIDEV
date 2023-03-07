package entities;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private Integer id;
    private String title;
    private String description;
    private User user;
    private Subject subject;
    private List<Answer> answers = new ArrayList<>();

    // constructors
    public Question() {
    }

    public Question(Integer id) {
        this.id = id;
    }

    public Question(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;

    }

    public Question(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Question(String title, String description) {
        this.title = title;
        this.description = description;

    }

    //  getters and setters
    public Integer get_id() {
        return id;

    }

    public String get_title() {
        return title;

    }

    public String get_description() {
        return description;

    }

    public void set_title(String title) {
        this.title = title;
    }

    public void set_description(String description) {
        this.description = description;

    }

    public void set_id(Integer id) {
        this.id = id;

    }

    //  methodes one to many
    public List<Answer> get_answers() {
        return answers;
    }

    public User get_user() {
        return user;
    }

    public void set_user(User user) {
        this.user = user;
    }

    // //one to many for subject
    public Subject get_subject() {
        return subject;
    }

    public void set_subject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", title=" + title + ", description=" + description + ", user=" + user
                + ", subject=" + subject + ", answers=" + answers + "]";
    }

}