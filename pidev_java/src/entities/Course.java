package entities;

import types.Getter_setter_map;
import types.Getter_setter_pair;

public class Course extends Base_entity {

  enum Difficulty {
    EASY, MEDIUM, HARD
  };

  private Integer id, id_subject;
  private String name, description;
  private Difficulty difficulty;

  public Course() {
    Getter_setter_map map = build_getter_setter_map(
        new Getter_setter_pair("difficulty", "get_difficulty_String", "set_difficulty"));
    set_getter_setter_map(map);
  }

  public Course(Integer id, String name, String description, String difficulty, Integer id_subject) {
    this(name, description, difficulty, id_subject);
    set_id(id);
  }

  public Course(Integer id, String name, String description, Difficulty difficulty, Integer id_subject) {
    this(name, description, difficulty, id_subject);
    set_id(id);
  }

  public Course(String name, String description, String difficulty, Integer id_subject) {
    this(name, description, id_subject);
    set_difficulty(difficulty);
  }

  public Course(String name, String description, Difficulty difficulty, Integer id_subject) {
    this(name, description, id_subject);
    set_difficulty(difficulty);
  }

  public Course(String name, String description, Integer id_subject) {
    this();
    set_name(name);
    set_description(description);
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
    this.difficulty = Difficulty.valueOf(difficulty);
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
