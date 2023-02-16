package entities;

import types.Getter_setter_map;

public class Answer extends Base_entity {
    private Integer id;
    private String message;
    private Integer question_id;
    private Integer vote_nb;

    public Answer() {
        Getter_setter_map map = build_getter_setter_map();

        set_getter_setter_map(map);
    }

    public Answer(Integer id, String message) {
        this();
        this.id = id;
        this.message = message;

    }

    public Answer(String message, Integer question_id) {
        this();
        this.message = message;
        this.question_id = question_id;

    }

    public Answer(Integer id, String message, Integer question_id) {
        this();
        this.id = id;
        this.message = message;
        this.question_id = question_id;

    }

    public Answer(Integer id, String message, Integer question_id, Integer vote_nb) {
        this();
        this.id = id;
        this.message = message;
        this.question_id = question_id;
        this.vote_nb = vote_nb;

    }

    public Integer get_vote_nb() {
        return vote_nb;

    }

    public void set_vote_nb(Integer vote_nb) {
        this.vote_nb = vote_nb;

    }

    public Integer get_question_id() {
        return question_id;

    }

    public void set_question_id(Integer question_id) {
        this.question_id = question_id;

    }

    public Integer get_id() {
        return id;

    }

    public String get_message() {
        return message;

    }

    public void set_message(String message) {
        this.message = message;

    }

    public void set_id(Integer id) {
        this.id = id;

    }

    @Override
    public String toString() {
        return "Reponse [ message=" + message +
                "]";
    }

}
