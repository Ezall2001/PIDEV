package entities;

import java.util.Objects;

import config.Log;

public class User extends Base_entity {
    private enum Type {
        ADMIN, STUDENT
    };

    protected Integer id;
    protected String email;
    protected String password;
    protected Type type;

    public User() {
    }

    public User(Integer id, String email, String password, Type type) {
        this(id, email, password);
        this.type = type;
    }

    public User(Integer id, String email, String password) {
        this(email, password);
        this.id = id;
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public Integer get_id() {
        return this.id;
    }

    public void set_id(Integer id) {
        this.id = id;
    }

    public String get_email() {
        return this.email;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public String get_password() {
        return this.password;
    }

    public void set_password(String password) {
        this.password = password;
    }

    public Type get_type_type() {
        return this.type;
    }

    public String get_type_string() {
        return this.type.toString();
    }

    public void set_type(Type type) {
        try {

        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password) && Objects.equals(type, user.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, type);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + get_id() + "'" +
                ", email='" + get_email() + "'" +
                ", password='" + get_password() + "'" +
                ", type='" + get_type_string() + "'" +
                "}";
    }

}
