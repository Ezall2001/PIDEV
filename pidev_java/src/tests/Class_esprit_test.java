package tests;

import java.util.List;

import config.Log;
import entities.Class_esprit;
import services.Class_esprit_service;
import types.Service_response;

public class Class_esprit_test {
  public static void main(String[] args) {
    Class_esprit_service service = new Class_esprit_service();
    Class_esprit class_esprit = new Class_esprit("1A");

    // add test
    Service_response<Integer> add_response = service.add_class_esprit(class_esprit);
    if (check_error(add_response))
      return;
    Integer new_id = add_response.get_body();
    class_esprit.set_id(new_id);

    // modify test
    Class_esprit modification_class_esprit = new Class_esprit(new_id, "3A");
    Service_response<Integer> modify_response = service.modify_class_esprit_by_id(class_esprit,
        modification_class_esprit);
    if (check_error(modify_response))
      return;

    // find test
    Service_response<List<Class_esprit>> find_response = service.find_class_esprit_by_id(class_esprit);
    if (check_error(find_response))
      return;
    Log.console(find_response.get_body());

    // delete test
    Service_response<Integer> delete_response = service.delete_class_esprit_by_id(class_esprit);
    if (check_error(delete_response))
      return;

  }

  public static <T> Boolean check_error(Service_response<T> response) {
    if (response.get_error() != null) {
      Log.console(response.get_error());
      return true;
    }

    return false;
  }
}