package entities;

public class Answer {
    private int id;
    private String message;
    private int vote_nb;
    // la relation one to many
    private Question question;

    // constructors
    public Answer() {
    }

    public Answer(int id, String message) {
        this.id = id;
        this.message = message;

    }

    public Answer(String message) {
        this.message = message;

    }

    // getters and setters
    public int get_vote_nb() {
        return vote_nb;

    }

    public void set_vote_nb(int vote_nb) {
        this.vote_nb = vote_nb;

    }

    public int get_id() {
        return id;

    }

    public String get_message() {
        return message;

    }

    public void set_message(String message) {
        this.message = message;

    }

    public void set_id(int id) {
        this.id = id;

    }
    // methodes one to many

    public Question get_question() {
        return question;
    }

    public void set_question(Question question) {
        this.question = question;
    }

    // toString
    @Override
    public String toString() {
        return "Reponse{" + "message=" + message + '}';
    }

}