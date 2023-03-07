package entities;

import utils.Log;

public class Course {

  public enum Difficulty {
    EASY, MEDIUM, HARD
  };

  private Integer id;
  private String name;
  private String description;
  private Difficulty difficulty;
  private Subject subject;

  public Course() {
  }

  public Course(Integer id) {
    this.id = id;
  }

  public Course(String name, String description, String difficulty) {
    set_name(name);
    set_description(description);
    set_difficulty(difficulty);
  }

  public Course(Integer id, String name, String description, Difficulty difficulty) {
    set_id(id);
    set_name(name);
    set_description(description);
    set_difficulty(difficulty);
  }

  public Course(Integer id, String name, String description, String difficulty) {
    set_id(id);
    set_name(name);
    set_description(description);
    set_difficulty(difficulty);
  }

  public Integer get_id() {
    return id;
  }

  public String get_name() {
    return name;
  }

  public String get_description() {
    return description;
  }

  public Difficulty get_difficulty() {
    return this.difficulty;
  }

  public String get_difficulty_string() {
    if (this.difficulty == null)
      return "null";
    return this.difficulty.toString();
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public void set_name(String name) {
    this.name = name;
  }

  public void set_description(String description) {
    this.description = description;
  }

  public void set_difficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void set_difficulty(String difficulty) {

    try {
      this.difficulty = Difficulty.valueOf(difficulty);
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public Subject get_subject() {
    return subject;
  }

  public void set_subject(Subject subject) {
    this.subject = subject;
  }

  @Override
  public String toString() {
    return "Course [id=" + id + ", name=" + name + ", description=" + description + ", difficulty=" + difficulty
        + ", subject=" + subject + "]";
  }

}
