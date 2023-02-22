package entities;

import java.util.List;

import config.Log;

public class Subject extends Base_entity {
  enum Difficulty {
    EASY, MEDIUM, HARD
  };

  private Integer id;
  private String name, description;
  private Difficulty difficulty;
  private List<Course> courses;
  private Class_esprit class_esprit;

  public Subject() {
  }

  public Subject(Integer id, String name, String description, String difficulty, Class_esprit class_esprit) {
    set_id(id);
    set_name(name);
    set_description(description);
    set_class_esprit(class_esprit);
  }

  public Subject(Integer id, String name, String description, Difficulty difficulty, Class_esprit class_esprit) {
    set_id(id);
    set_name(name);
    set_description(description);
    set_class_esprit(class_esprit);
  }

  public Subject(String name, String description, String difficulty, Class_esprit class_esprit) {
    set_name(name);
    set_description(description);
    set_class_esprit(class_esprit);
    set_difficulty(difficulty);
  }

  public Subject(String name, String description, Difficulty difficulty, Class_esprit class_esprit) {
    set_name(name);
    set_description(description);
    set_class_esprit(class_esprit);
    set_difficulty(difficulty);
  }

  public Integer get_id() {
    return id;
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public String get_name() {
    return name;
  }

  public void set_name(String name) {
    this.name = name;
  }

  public String get_description() {
    return description;
  }

  public void set_description(String description) {
    this.description = description;
  }

  public Class_esprit get_class_esprit() {
    return class_esprit;
  }

  public void set_class_esprit(Class_esprit class_esprit) {
    this.class_esprit = class_esprit;
  }

  public List<Course> get_courses() {
    return courses;
  }

  public void set_courses(List<Course> courses) {
    this.courses = courses;
  }

  public String get_difficulty_String() {
    return difficulty.toString();
  }

  public Difficulty get_difficulty_Difficulty() {
    return difficulty;
  }

  public void set_difficulty(String difficulty) {
    try {
      this.difficulty = Difficulty.valueOf(difficulty);
    } catch (Exception e) {
      Log.file(e);
    }
  }

  public void set_difficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  @Override
  public String toString() {
    return "Subject [id=" + id + ", name=" + name + ", description=" + description + ", difficulty=" + difficulty
        + ", courses=" + courses + ", class_esprit=" + class_esprit + "]";
  }

}