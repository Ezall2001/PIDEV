package entities;

public class Answer {
    private Integer id;
    private String message;
    private Integer vote_nb;
    // la relation one to many
    private Question question;
    private User user;
    private Vote vote = null;

    // constructors
    public Answer() {
        this.vote_nb = 0;
    }

    public Answer(Integer id, String message) {
        this.id = id;
        this.message = message;

    }

    public Answer(Integer id, String message, Integer vote_nb) {
        this.id = id;
        this.message = message;
        this.vote_nb = vote_nb;

    }

    public Answer(Integer id, String message, Integer vote_nb, User user) {
        this.id = id;
        this.message = message;
        this.vote_nb = vote_nb;
        this.user = user;

    }

    public Answer(String message) {
        this.message = message;
        this.vote_nb = 0;

    }

    // getters and setters
    public Integer get_vote_nb() {
        return vote_nb;

    }

    public void set_vote_nb(Integer vote_nb) {
        this.vote_nb = vote_nb;

    }

    public Integer get_id() {
        return id;

    }

    public void set_message(String message) {
        this.message = message;

    }

    public String get_message() {
        return message;

    }

    public void set_id(Integer id) {
        this.id = id;

    }
    // methodes one to many

    public Question get_question() {
        return question;
    }

    public void set_question(Question question) {
        this.question = question;
    }

    // methodes one to many
    public User get_user() {
        return user;
    }

    public void set_user(User user) {
        this.user = user;
    }

    public Vote get_vote() {
        return vote;
    }

    public void set_vote(Vote vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "Answer [id=" + id + ", message=" + message + ", vote_nb=" + vote_nb + ", question=" + question
                + ", user=" + user + ", vote=" + vote + "]";
    }

}
