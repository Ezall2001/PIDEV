package entities;

import utils.*;

public class Course {

  public enum Difficulty {
    easy, normal, hard
  };

  private int id_c;
  private String course_name;

  private String description_c;
  private Difficulty difficulty;
  Subject subject;

  public Course() {
  }

  public Course(int id_c, String course_name) {

    this.id_c = id_c;
    this.course_name = course_name;
  }

  public Course(int id_c, String course_name, String description_c, Difficulty difficulty) {
    this.id_c = id_c;
    this.course_name = course_name;
    this.description_c = description_c;
    this.difficulty = difficulty;

  }

  public int get_id_c() {
    return id_c;
  }

  public String get_course_name() {
    return course_name;
  }

  public String get_description_c() {
    return description_c;
  }

  public Difficulty get_difficulty() {
    return this.difficulty;
  }

  public String get_difficulty_String() {
    return this.difficulty.toString();
  }

  public void set_id_c(int id_c) {
    this.id_c = id_c;
  }

  public void set_course_name(String course_name) {
    this.course_name = course_name;
  }

  public void set_description_c(String description_c) {
    this.description_c = description_c;
  }

  public void set_difficulty(String difficulty) {

    try {
      this.difficulty = Difficulty.valueOf(difficulty);
    } catch (Exception e) {
      Log.console(e.getMessage());
    }
  }

  @Override
  public String toString() {
    return "cour{" + "id=" + id_c + ",nom_cour =" + course_name +
        ", description=" + description_c + ", difficulter=" + get_difficulty_String() + '}';
  }

}