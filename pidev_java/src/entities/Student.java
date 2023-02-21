package entities;

import config.Log;
import types.Getter_setter_map;
import types.Getter_setter_pair;
import types.Service_response;

import java.util.EmptyStackException;
import java.util.Objects;

public class Student extends User {
    private enum Grade {
        NEWCOMMER, BEGINNER, COMPETENT, PROFICIENT, EXPERT
    };

    private String first_name;
    private String last_name;
    private Integer age;
    private Grade level;
    private Integer score;
    private String description;
    private String picture_path;

    public Student() {
        Getter_setter_map map = build_getter_setter_map(
                new Getter_setter_pair("level", "get_level_string", "set_level"));
        set_getter_setter_map(map);
    }

    public Student(String first_name, String last_name, Integer age, String description, String picture_path) {
        this();
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.description = description;
        this.picture_path = picture_path;
    }

    public Student(String email, String password, String first_name, String last_name, Integer age, String description,
            String picture_path) {
        this(first_name, last_name, age, description, picture_path);
        this.email = email;
        this.password = password;
    }

    public Student(Integer id, String email, String password, String first_name, String last_name, Integer age,
            Grade level,
            Integer score, String description,
            String picture_path) {
        this(email, password, first_name, last_name, age, description, picture_path);
        this.id = id;
        this.level = Grade.NEWCOMMER;
        this.score = 0;
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

    public Integer get_age() {
        return this.age;
    }

    public void set_age(Integer age) {
        this.age = age;
    }

    public Grade get_level_grade() {
        return this.level;
    }

    public String get_level_string() {
        return this.level.toString();
    }

    public void set_level(String level) {
        try {
            Grade.valueOf(level);
        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

    public Integer get_score() {
        return this.score;
    }

    public void set_score(Integer score) {
        this.score = score;
    }

    public String get_description() {
        return this.description;
    }

    public void set_description(String description) {
        this.description = description;
    }

    public String get_picture_path() {
        return this.picture_path;
    }

    public void set_picture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(first_name, student.first_name) && Objects.equals(last_name, student.last_name)
                && age == student.age
                && Objects.equals(level, student.level) && score == student.score
                && Objects.equals(description, student.description)
                && Objects.equals(picture_path, student.picture_path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name, age, level, score, description, picture_path);
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
                ", picture_path='" + get_picture_path() + "'" +
                "}";
    }

}
