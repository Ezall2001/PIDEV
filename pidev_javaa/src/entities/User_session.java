package entities;

import java.time.Instant;

public class User_session {

    private Integer id;
    private Instant created_at;
    private Instant expires_at;
    private User user;

    public User_session() {
    }

    public User_session(Integer id, Instant created_at, Instant expires_at) {
        this.id = id;
        this.created_at = created_at;
        this.expires_at = expires_at;
    }

    public Integer get_id() {
        return id;
    }

    public void set_id(Integer id) {
        this.id = id;
    }

    public User get_user() {
        return user;
    }

    public void set_user(User user) {
        this.user = user;
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

    public Boolean is_expired() {
        return Instant.now().isAfter(expires_at);
    }

    @Override
    public String toString() {
        return "User_session [id=" + id + ", created_at=" + created_at + ", expires_at=" + expires_at + ", user=" + user
                + "]";
    }

}
