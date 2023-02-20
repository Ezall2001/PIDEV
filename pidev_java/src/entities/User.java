package entities;

import java.util.Objects;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import config.Log;

public class User {
    private enum Type_user {
        ADMIN, STUDENT
    };

    public enum Grade {
        NEWCOMMER, BEGINNER, COMPETENT, PROFICIENT, EXPERT
    };

    private int user_id;
    private String first_name, last_name;
    private int age;
    private Grade level;
    private int score;
    private String description;
    private String email, password, hashed_password;
    private byte[] picture_data;
    private File picture_path;
    protected Type_user type;

    public User() {
    }

    public User(int user_id, String email) {
        this.user_id = user_id;
        this.email = email;

    }

    public User(String email) {

        this.email = email;

    }

    public User(int user_id, String first_name, String last_name, int age, Grade level, int score,
            String description, String email, String password, String hashed_password,
            byte[] picture_data,
            Type_user type) {
        this(first_name, last_name, age, description, email, password, picture_data);
        this.user_id = user_id;
        this.level = level;
        this.score = score;
        this.type = type;
    }

    // for data base

    public User(String first_name, String last_name, int age, String description, String email, String password,
            byte[] picture_data) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.description = description;
        this.email = email;
        this.password = password;
        set_hashed_password(password);
        this.picture_data = picture_data;
    }
    // for add method

    public User(String first_name, String last_name, int age, String description, String email, String password,
            File picture_path) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.description = description;
        this.email = email;
        this.password = password;
        set_hashed_password(password);
        this.picture_path = picture_path;
    }

    public User(int user_id, String first_name, String last_name, int age, String description, String email,
            String password,
            File picture_path) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.description = description;
        this.email = email;
        this.password = password;
        set_hashed_password(password);
        this.picture_path = picture_path;
    }

    public String get_first_name() {
        return this.first_name;
    }

    public void set_first_name(String first_name) {
        this.first_name = first_name;
    }

    public String get_last_name() {
        return this.last_name;
    }

    public void set_last_name(String last_name) {
        this.last_name = last_name;
    }

    public int get_age() {
        return this.age;
    }

    public void set_age(int age) {
        this.age = age;
    }

    public Grade get_level_grade() {
        return this.level;
    }

    //TODO : problem in this setter 
    public String get_level_string() {

        if (this.level != null) {
            return this.level.toString();
        } else {
            return "Unknown level";
        }
    }

    public void set_level(String level) {
        try {
            this.level = Grade.valueOf(level);

        } catch (Exception e) {
            Log.console(e.getMessage());
        }

    }

    public int get_score() {
        return this.score;
    }

    public void set_score(int score) {
        this.score = score;
    }

    public String get_description() {
        return this.description;
    }

    public void set_description(String description) {
        this.description = description;
    }

    public int get_user_id() {
        return this.user_id;
    }

    public void set_user_id(int user_id) {
        this.user_id = user_id;
    }

    public String get_email() {
        return this.email;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public byte[] get_picture_data() {
        if (picture_path == null) {
            return picture_data;
        }
        byte[] picture_data = null;
        try (FileInputStream inputStream = new FileInputStream(picture_path)) {
            picture_data = new byte[inputStream.available()];
            inputStream.read(picture_data);
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
        return picture_data;
    }

    public File get_picture_path() {
        return picture_path;
    }

    public void set_picture_data(byte[] picture_data) {
        this.picture_data = picture_data;
    }

    public String get_hashed_password() {
        return hashed_password;
    }

    public void set_hashed_password(String password) {
        try {
            MessageDigest message_digest = MessageDigest.getInstance("SHA-256");
            message_digest.update(password.getBytes());
            byte[] bytes = message_digest.digest();
            StringBuilder string_builder = new StringBuilder();
            for (byte b : bytes) {
                string_builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            hashed_password = string_builder.toString();
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
    }

    public boolean checkPassword(String password) {
        try {
            MessageDigest message_digest = MessageDigest.getInstance("SHA-256");
            message_digest.update(password.getBytes());
            byte[] bytes = message_digest.digest();
            StringBuilder string_builder = new StringBuilder();
            for (byte b : bytes) {
                string_builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            String hashedInput = string_builder.toString();
            return hashedInput.equals(hashed_password);
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
        return false;
    }

    public Type_user get_type_Type_user() {
        return this.type;
    }

    public String get_type_string() {

        if (this.type != null) {
            return this.type.toString();
        } else {
            return "Unknown type";
        }
    }

    public void set_type(String type) {
        try {
            this.type = Type_user.valueOf(type);

        } catch (Exception e) {
            Log.console(e.getMessage());
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
        return Objects.equals(email, user.email);

    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name, age, level, score, description,
                picture_data, user_id, email,
                password, type);
    }

    @Override
    public String toString() {
        return "{" +
                " first_name='" + get_first_name() + "'" +
                ", last_name='" + get_last_name() + "'" +
                ", age='" + get_age() + "'" +
                ", level='" + get_level_string() + "'" +
                ", score='" + get_score() + "'" +
                ", description='" + get_description() + "'" +
                ", user_id='" + get_user_id() + "'" +
                ", email='" + get_email() + "'" +
                ", password='" + get_hashed_password() + "'" +
                ", type='" + get_type_string() + "'" +
                "}";
    }

}
