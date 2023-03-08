package entities;

import utils.Log;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class Test {

    public enum Type {
        SUBJECT, COURSE;
    }

    private Integer id, min_points, duration;
    private Type type;
    private Course course;
    private Subject subject;
    private List<Test_qs> questions = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public Test() {
    }

    public Test(Integer id, Integer min_points, Integer duration, String type) {
        this.id = id;
        this.min_points = min_points;
        this.duration = duration;
        this.set_type(type);
    }

    public Test(Integer min_points, Integer duration, String type) {
        this.min_points = min_points;
        this.duration = duration;
        this.set_type(type);
    }

    public void set_questions(List<Test_qs> questions) {
        this.questions = questions;
    }

    public List<Test_qs> get_questions() {
        return questions;
    }

    public void set_users(List<User> users) {
        this.users = users;
    }

    public List<User> get_users() {
        return users;
    }

    public void set_subject(Subject subject) {
        this.subject = subject;
    }

    public Subject get_subject() {
        return subject;
    }

    public void set_course(Course course) {
        this.course = course;
    }

    public Course get_course() {
        return course;
    }

    public Type get_type() {
        return type;
    }

    public String get_type_string() {
        return type.toString().toLowerCase();
    }

    public void set_type(String type) {
        try {
            this.type = Type.valueOf(type);
        } catch (Exception e) {
            Log.file(e);
        }
    }

    public void set_type(Type type) {
        this.type = type;
    }

    public Integer get_id() {
        return id;
    }

    public void set_id(Integer id) {
        this.id = id;
    }

    public Integer get_min_points() {
        return min_points;
    }

    public void set_min_points(Integer min_points) {
        this.min_points = min_points;
    }

    public Integer get_duration() {
        return duration;
    }

    public void set_duration(Integer duration) {
        this.duration = duration;
    }

    public Pair<Integer, Integer> get_critical_time() {

        Integer critical_total_seconds = new Double(get_duration() * (new Double(60) / 4)).intValue();
        Integer ciritical_minutes = critical_total_seconds / 60;
        Integer ciritical_seconds = critical_total_seconds % 60;

        return new Pair<Integer, Integer>(ciritical_minutes, ciritical_seconds);
    }

    public String get_name() {
        if (type.equals(Type.COURSE))
            return course.get_name();
        return subject.get_name();
    }

    @Override
    public String toString() {
        return "Test [id=" + id + ", min_points=" + min_points + ", duration=" + duration + ", type=" + type
                + ", course=" + course + ", subject=" + subject + ", questions=" + questions + ", users=" + users + "]";
    }

}
