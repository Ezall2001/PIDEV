package services;

import java.util.Arrays;
import java.util.List;

import entities.Session;
import types.Service_response;

public class Session_service extends Base_service {

  public Session_service() {
    super();
  }

  public Service_response<Integer> add_session(Session answer) {
    return super.add(answer, null);
  }

  public Service_response<Integer> modify_session_by_id(Session search_session, Session modification_session) {
    return super.modify(search_session, modification_session, Arrays.asList("id"), null);
  }

  public Service_response<Integer> delete_session_by_id(Session session) {
    return super.delete(session, Arrays.asList("id"));
  }

  public Service_response<List<Session>> find_session_by_id(Session session) {
    return super.find(session, Arrays.asList("id"));
  }

  public Service_response<List<Session>> find_session_by_id(Integer id) {
    Session session = new Session();
    session.set_id(id);
    return find_session_by_id(session);
  }

  public Service_response<Integer> delete_session_by_id(Integer id) {
    Session session = new Session();
    session.set_id(id);
    return delete_session_by_id(session);
  }

}
