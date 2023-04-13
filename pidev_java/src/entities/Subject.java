package entities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import utils.Log;

public class Subject {

  public enum Class_esprit {
    A1, A2, A3, P2, B3
  }

  private Integer id;
  private String name;
  private String description;
  private List<Class_esprit> classes_esprit;
  private List<Course> courses;
  private List<Question> questions;

  public Subject() {
  }

  public Subject(int id) {
    this.id = id;
  }

  public Subject(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Subject(Integer id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
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

  public List<Class_esprit> get_classes_esprit_list() {
    return classes_esprit;
  }

  public String get_classes_esprit_string() {
    List<String> all_esprit_classes = classes_esprit.stream().map(classes_esprit -> classes_esprit.toString())
        .collect(Collectors.toList());

    return String.join(" / ", all_esprit_classes);
  }

  public List<Course> get_Courses() {
    return courses;
  }

  public List<Question> get_question() {
    return questions;
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

  public void set_classes_esprit(List<Class_esprit> classes_esprit) {
    this.classes_esprit = classes_esprit;
  }

  public void set_classes_esprit(String classes_esprit) {

    this.classes_esprit = Arrays.asList(classes_esprit.split(" / "))
        .stream()
        .map(class_esprit -> {
          try {
            return Class_esprit.valueOf(class_esprit);
          } catch (Exception e) {
            Log.file(e);
            return null;
          }
        })
        .filter(class_esprit -> class_esprit != null)
        .collect(Collectors.toList());
  }

  public void set_courses(List<Course> courses) {
    this.courses = courses;
  }

  public void set_questions(List<Question> questions) {
    this.questions = questions;
  }

  @Override
  public String toString() {
    return "Subject [id=" + id + ", name=" + name + ", description=" + description + ", classes_esprit="
        + classes_esprit
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Subject other = (Subject) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    return true;
  }

}
