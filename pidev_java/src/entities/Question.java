package entities;

import java.util.List;

import types.Getter_setter_map;

public class Question extends Base_entity {
    private Integer id, vote_nb;
    private String title, description;

    public Question() {
        Getter_setter_map map = build_getter_setter_map();

        set_getter_setter_map(map);
    }

    public Question(Integer id, String title, String description) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;

    }

    public Question(String title, String description) {
        this();
        this.title = title;
        this.description = description;

    }

    public Question(String title, String description, Integer vote_nb) {
        this();
        this.title = title;
        this.description = description;

    }

    public Integer get_id() {
        return id;
    }

    public void set_id(Integer id) {
        this.id = id;
    }

    public String get_title() {
        return title;

    }

    public void set_title(String title) {
        this.title = title;
    }

    public String get_description() {
        return description;

    }

    public void set_description(String description) {
        this.description = description;
    }

    public Integer get_vote_nb() {
        return vote_nb;
    }

    public void set_vote_nb(Integer vote_nb) {
        this.vote_nb = vote_nb;
    }

    /*  //  methodes one to many
    public List<Answer> getAnswers() {
        return answers;
    }
    
    public void addAnswer(Answer answer) {
        answers.add(answer);
    } */

    @Override
    public String toString() {
        return "Question [id=" + id + ", title=" + title + ", description=" + description +
                "]";
    }

}
