package types;

public class Service_response<R> {
  private Boolean is_empty;
  private Exception error;
  private R body;

  public Service_response(Boolean is_empty) {
    this.is_empty = is_empty;
  }

  public Service_response(Exception error) {
    this.error = error;
  }

  public Service_response(R body) {
    this.error = null;
    this.is_empty = false;
    this.body = body;
  }

  public Boolean get_vide() {
    return is_empty;
  }

  public void set_vide(Boolean is_empty) {
    this.is_empty = is_empty;
  }

  public Exception get_error() {
    return error;
  }

  public void set_error(Exception error) {
    this.error = error;
  }

  public R get_body() {
    return body;
  }

  public void set_body(R body) {
    this.body = body;
  }

}
