package tests;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import config.Log;
import entities.Session;
import services.Session_service;
import types.Service_response;

public class Session_test {

  public static void main(String[] args) {
    Session_service service = new Session_service();

    LocalDate date = LocalDate.of(2002, 6, 7);
    LocalTime start_time = LocalTime.of(15, 30);
    LocalTime end_time = LocalTime.of(17, 30);

    Session session = new Session(15.500, date, start_time, end_time);

    // add test
    Service_response<Integer> add_response = service.add_session(session);
    if (check_error(add_response))
      return;
    Integer new_id = add_response.get_body();
    session.set_id(new_id);

    // modify test
    Session modification_session = new Session(new_id, 18.5, date, start_time, end_time);
    Service_response<Integer> modify_response = service.modify_session_by_id(session, modification_session);
    if (check_error(modify_response))
      return;

    // find test
    Service_response<List<Session>> find_response = service.find_session_by_id(session);
    if (check_error(find_response))
      return;
    Log.console(find_response.get_body());

    // delete test
    Service_response<Integer> delete_response = service.delete_session_by_id(session);
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
