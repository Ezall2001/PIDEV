package entities;

import types.Getter_setter_map;
import types.Getter_setter_pair;

public class Subject extends Base_entity {
  enum Difficulty {
    EASY, MEDIUM, HARD
  };

  private Integer id, id_class_esprit;
  private String name, description;
  private Difficulty difficulty;

  public Subject() {
    Getter_setter_map map = build_getter_setter_map(
        new Getter_setter_pair("difficulty", "get_difficulty_String", "set_difficulty"));
    set_getter_setter_map(map);
  }

  public Subject(Integer id, String name, String description, String difficulty, Integer id_class_esprit) {
    this(name, description, difficulty, id_class_esprit);
    set_id(id);
  }

  public Subject(Integer id, String name, String description, Difficulty difficulty, Integer id_class_esprit) {
    this(name, description, difficulty, id_class_esprit);
    set_id(id);
  }

  public Subject(String name, String description, String difficulty, Integer id_class_esprit) {
    this(name, description, id_class_esprit);
    set_difficulty(difficulty);
  }

  public Subject(String name, String description, Difficulty difficulty, Integer id_class_esprit) {
    this(name, description, id_class_esprit);
    set_difficulty(difficulty);
  }

  public Subject(String name, String description, Integer id_class_esprit) {
    this();
    set_name(name);
    set_description(description);
    set_id_class_esprit(id_class_esprit);
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

  public Integer get_id_class_esprit() {
    return id_class_esprit;
  }

  public void set_id_class_esprit(Integer id_class_esprit) {
    this.id_class_esprit = id_class_esprit;
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
    return "Subject [id=" + id + ", id_class_esprit=" + id_class_esprit + ", name=" + name + ", description="
        + description + ", difficulty=" + difficulty + "]";
  }

}