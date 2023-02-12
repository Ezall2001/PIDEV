package entities;

import java.util.ArrayList;
import java.util.List;

public class Question {
    int id;
    String title;
    String description;
    String message;
    Answer r = new Answer();

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

    public int getId() {
        return id;

    }

    public String getTitle() {
        return title;

    }

    public String getDescription() {
        return description;

    }

    public void setTitle(String title) {
        this.title = title;

    }

    public void setDescription(String description) {
        this.description = description;

    }

    public void setId(int id) {
        this.id = id;

    }

    //  methodes one to many
    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    // toString
    @Override
    public String toString() {
        return "Question{" + ", titre=" + title + ", description=" + description
                + '}';
    }

}
