package entities;

public class Vote {

  Integer id;
  Integer type_vote;

  //  one to many relationship
  private Answer answer;
  private User user;

  // constructors
  public Vote() {
  }

  public Vote(Integer id) {
    this.id = id;

  }

  public Vote(Integer type_vote, User user, Answer answer) {
    this.type_vote = type_vote;
    this.user = user;
    this.answer = answer;
  }

  public Vote(User user, Answer answer) {
    this.user = user;
    this.answer = answer;
  }

  //  getters and setters

  public Integer get_id() {
    return id;
  }

  public void set_id(Integer id) {
    this.id = id;

  }

  public Integer get_type_vote() {
    return type_vote;
  }

  public void set_type_vote(Integer type_vote) {
    this.type_vote = type_vote;

  }

  //  methodes one to many
  public Answer get_answer() {
    return answer;
  }

  public void set_answer(Answer answer) {
    this.answer = answer;
  }

  public User get_user() {
    return user;
  }

  public void set_user(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Vote [id=" + id + ", type_vote=" + type_vote + ", answer=" + answer + ", user=" + user + "]";
  }

}
