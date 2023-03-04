package entities;

public class Class_esprit {
  Integer id;
  String name;

  public Class_esprit() {
  }

  public Class_esprit(Integer id, String name) {
    set_id(id);
    set_name(name);
  }

  public Class_esprit(String name) {
    set_name(name);
  }

  public Integer get_id() {
    return id;
  }

  public String get_name() {
    return name;
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public void set_name(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Class_esprit [id=" + id + ", name=" + name + "]";
  }

}
