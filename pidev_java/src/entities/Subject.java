package entities;

import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.a.NativeAuthenticationProvider;

import utils.Log;

public class Subject {

  public enum Level {
    CLASS_A, CLASS_B
  };

  private int id;
  private String subject_name;
  private List<Question> questions = new ArrayList<>();
  private String description;
  private Level level;

  private List<Course> courses;

  public Subject() {
  }

  public Subject(String subject_name) {

    this.subject_name = subject_name;
  }

  public Subject(String subject_name, String description, Level level) {

    this.subject_name = subject_name;
    this.description = description;
    this.level = level;

  }

  public Subject(int id, String subject_name, String description, Level level) {
    this.id = id;
    this.subject_name = subject_name;
    this.description = description;
    this.level = level;

  }

  public int get_id() {
    return id;
  }

  public String get_subject_name() {
    return subject_name;
  }

  public String get_description() {
    return description;
  }

  public Level get_niveau() {
    return this.level;
  }

  public String get_level_String() {
    return this.level.toString();
  }

  public void set_id(int id) {
    this.id = id;
  }

  public void set_subject_name(String subject_name) {
    this.subject_name = subject_name;
  }

  public void set_description(String description) {
    this.description = description;
  }

  public void set_level(String level) {

    try {
      this.level = Level.valueOf(level);
    } catch (Exception e) {
      Log.console(e.getMessage());
    }
  }

  public void add_courses(Course courses) {
    this.courses.add(courses);

  }

  public List<Course> get_Courses() {
    return courses;
  }

  @Override
  public String toString() {
    return "Matiere{" + "id=" + id + ",nom_matiere =" + subject_name +
        ", description=" + description + ",Level=" + get_level_String() + '}';
  }

  public List<Question> get_question() {
    return questions;
  }

  public void add_question(Question question) {
    questions.add(question);
  }

}
