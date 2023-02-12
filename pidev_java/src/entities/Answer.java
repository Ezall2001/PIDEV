package entities;

public class Answer {
    private int id;
    private String message;
    private int question_id;
    private int nb_vote;
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

    public Answer(int id, String message, int question_id) {
        this.id = id;
        this.message = message;
        this.question_id = question_id;

    }

    // getters and setters
    public int get_Nbvote() {
        return nb_vote;

    }

    public void setNbvote(int nb_vote) {
        this.nb_vote = nb_vote;

    }

    public int getId_question() {
        return question_id;

    }

    public void setId_question(int question_id) {
        this.question_id = question_id;

    }

    public int getId() {
        return id;

    }

    public String getMessage() {
        return message;

    }

    public void setMessage(String message) {
        this.message = message;

    }

    public void setId(int id) {
        this.id = id;

    }
    // methodes one to many

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    // toString
    @Override
    public String toString() {
        return "Reponse{" + "message=" + message + '}';
    }

}
