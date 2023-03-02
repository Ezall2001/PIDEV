package entities;

public class Resource {
  private Integer id;
  private String name, file_path;
  private Session session;

  public Resource() {

  }

  public Resource(String name, String file_path) {
    set_name(name);
    set_file_path(file_path);
  }

  public Resource(Integer id, String name, String file_path) {
    set_id(id);
    set_name(name);
    set_file_path(file_path);
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

  public String get_file_path() {
    return file_path;
  }

  public void set_file_path(String file_path) {
    this.file_path = file_path;
  }

  public Session get_session() {
    return session;
  }

  public void set_session(Session session) {
    this.session = session;
  }

  @Override
  public String toString() {
    return "Resource [id=" + id + ", name=" + name + ", file_path=" + file_path + ", session=" + session.toString()
        + "]";
  }

}