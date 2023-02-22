package entities;

public class Resource {
  private Integer id, id_session;
  private String name, file_path;

  public Resource() {

  }

  public Resource(Integer id_session, String name, String file_path) {
    set_id_session(id_session);
    set_name(name);
    set_file_path(file_path);
  }

  public Resource(Integer id, Integer id_session, String name, String file_path) {
    set_id(id);
    set_id_session(id_session);
    set_name(name);
    set_file_path(file_path);
  }

  public Integer get_id() {
    return id;
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public Integer get_id_session() {
    return id_session;
  }

  public void set_id_session(Integer id_session) {
    this.id_session = id_session;
  }

  public String get_name() {
    return name;
  }

  public void set_name(String name) {
    this.name = name;
  }

  public String get_file_path() {
    return file_path;
  }

  public void set_file_path(String file_path) {
    this.file_path = file_path;
  }

  @Override
  public String toString() {
    return "Resource [id=" + id + ", id_session=" + id_session + ", name=" + name + ", file_path=" + file_path + "]";
  }

}