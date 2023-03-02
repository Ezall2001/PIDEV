package entities;

import utils.Log;

public class Participation {

  public enum State {
    PENDING, ACCEPTED, DENIED
  }

  private Integer id;
  private Session session;
  private User user;
  private State state;

  public Participation() {
  }

  public Integer get_id() {
    return id;
  }

  public void set_id(Integer id) {
    this.id = id;
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

  public String get_state_string() {
    return state.toString().toLowerCase();
  }

  public void set_state(State state) {
    this.state = state;
  }

  public void set_state(String state) {
    try {
      this.state = State.valueOf(state);
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  @Override
  public String toString() {
    return "Reservation [session=" + session.toString() + ", user=" + user.toString() + ", state=" + state + "]";
  }

}