package entities;

import utils.Log;

public class Course {

  enum Difficulty {
    EASY, MEDIUM, HARD
  };

  private Integer id, id_subject;
  private String name, description;
  private Difficulty difficulty;

  public Course() {
  }

  public Course(Integer id, String name, String description, String difficulty, Integer id_subject) {
    set_id(id);
    set_name(name);
    set_description(description);
    set_difficulty(difficulty);
    set_id_subject(id_subject);
  }

  public Course(Integer id, String name, String description, Difficulty difficulty, Integer id_subject) {
    set_id(id);
    set_name(name);
    set_description(description);
    set_difficulty(difficulty);
    set_id_subject(id_subject);
  }

  public Course(String name, String description, String difficulty, Integer id_subject) {
    set_name(name);
    set_description(description);
    set_difficulty(difficulty);
    set_id_subject(id_subject);
  }

  public Course(String name, String description, Difficulty difficulty, Integer id_subject) {
    set_name(name);
    set_description(description);
    set_difficulty(difficulty);
    set_id_subject(id_subject);
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

  public Integer get_id_subject() {
    return id_subject;
  }

  public void set_id_subject(Integer id_subject) {
    this.id_subject = id_subject;
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
    return "Course [id=" + id + ", id_subject=" + id_subject + ", name=" + name + ", description=" + description
        + ", difficulty=" + difficulty + "]";
  }

}
