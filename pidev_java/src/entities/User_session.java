package entities;

import java.time.Instant;
import java.util.Objects;

public class User_session {

    private Integer session_id;
    private User user;
    private String token;
    private Instant created_at;
    private Instant expires_at;

    public User_session() {
    }

    public User_session(Integer session_id, User user, String token, Instant created_at, Instant expires_at) {
        this(user, token, created_at, expires_at);
        this.session_id = session_id;

    }

    public User_session(User user, String token, Instant created_at, Instant expires_at) {
        this.user = user;
        this.token = token;
        this.created_at = created_at;
        this.expires_at = expires_at;
    }

    public Integer get_session_id() {
        return session_id;
    }

    public void set_session_id(Integer session_id) {
        this.session_id = session_id;
    }

    public User get_user() {
        return user;
    }

    public void set_user(User user) {
        this.user = user;
    }

    public String get_token() {
        return token;
    }

    public void set_token(String token) {
        this.token = token;
    }

    public Instant get_created_at() {
        return created_at;
    }

    public void set_created_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant get_expires_at() {
        return expires_at;
    }

    public void set_expires_at(Instant expires_at) {
        this.expires_at = expires_at;
    }

    public boolean is_expired() {
        return Instant.now().isAfter(expires_at);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User_session)) {
            return false;
        }
        User_session user_session = (User_session) o;
        return Objects.equals(session_id, user_session.session_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session_id, user, created_at, expires_at);
    }

    @Override
    public String toString() {
        return "{" +
                " session_id='" + get_session_id() + "'" +
                ", user='" + get_user() + "'" +
                ", created_at='" + get_created_at() + "'" +
                ", expires_at='" + get_expires_at() + "'" +
                "}";
    }

}