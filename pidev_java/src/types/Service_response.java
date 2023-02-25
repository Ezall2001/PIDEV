package types;

public class Service_response<R> {
  private Exception error;
  private R body;

  public Service_response(Exception error) {
    this.error = error;
  }

  public Service_response(R body) {
    this.error = null;
    this.body = body;
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
