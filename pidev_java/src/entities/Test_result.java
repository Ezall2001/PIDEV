package entities;

public class Test_result {

  private Integer id, mark;
  private User user;
  private Test test;

  public Test_result() {
    this.mark = 0;
  }

  public Test_result(Integer id, Integer mark) {
    set_id(id);
    set_mark(mark);
  }

  public Test_result(Integer mark, User user, Test test) {
    set_mark(mark);
    set_user(user);
    set_test(test);
  }

  public Integer get_id() {
    return id;
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public Integer get_mark() {
    return mark;
  }

  public void set_mark(Integer mark) {
    this.mark = mark;
  }

  public User get_user() {
    return user;
  }

  public void set_user(User user) {
    this.user = user;
  }

  public Test get_test() {
    return test;
  }

  public void set_test(Test test) {
    this.test = test;
  }

  @Override
  public String toString() {
    return "Test_result [id=" + id + ", mark=" + mark + ", user=" + user + ", test=" + test + "]";
  }

}
