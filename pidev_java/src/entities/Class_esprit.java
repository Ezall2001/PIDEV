package entities;

import types.Getter_setter_map;

public class Class_esprit extends Base_entity {
  Integer id;
  String name;

  public Class_esprit() {
    Getter_setter_map map = build_getter_setter_map();
    set_getter_setter_map(map);
  }

  public Class_esprit(Integer id, String name) {
    this(name);
    this.id = id;
  }

  public Class_esprit(String name) {
    this();
    this.name = name;
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
