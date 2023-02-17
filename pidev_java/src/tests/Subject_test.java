package tests;

import java.util.List;

import config.Log;
import entities.Subject;
import services.Subject_service;
import types.Service_response;

public class Subject_test {
  public static void main(String[] args) {
    Subject_service service = new Subject_service();
    Subject subject = new Subject("integrale partiel", "integrale", "HARD", 2);

    // add test
    Service_response<Integer> add_response = service.add_subject(subject);
    if (check_error(add_response))
      return;
    Integer new_id = add_response.get_body();
    subject.set_id(new_id);

    // modify test
    Subject modification_subject = new Subject(new_id, "integrale partiel", "integrale", "EASY", 2);
    Service_response<Integer> modify_response = service.modify_subject_by_id(subject,
        modification_subject);
    if (check_error(modify_response))
      return;

    // find test
    Service_response<List<Subject>> find_response = service.find_subject_by_id(subject);
    if (check_error(find_response))
      return;
    Log.console(find_response.get_body());

    // delete test
    Service_response<Integer> delete_response = service.delete_subject_by_id(subject);
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