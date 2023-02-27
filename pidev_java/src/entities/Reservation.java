package entities;

public class Reservation {

  public enum State {
    PENDING, ACCEPTED, DENIED
  }

  private Session session;
  private User user;
  private State state;

  public Reservation() {
  }

  public Session get_session() {
    return session;
  }

  public void set_session(Session session) {
    this.session = session;
  }

  public User get_user() {
    return user;
  }

  public void set_user(User user) {
    this.user = user;
  }

  public State get_state() {
    return state;
  }

  public void set_state(State state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Reservation [session=" + session.toString() + ", user=" + user.toString() + ", state=" + state + "]";
  }

}