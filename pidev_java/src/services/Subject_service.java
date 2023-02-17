package services;

import java.util.Arrays;
import java.util.List;

import entities.Subject;
import types.Service_response;

public class Subject_service extends Base_service {

  public Service_response<Integer> add_subject(Subject subject) {
    return super.add(subject, null);
  }

  public Service_response<Integer> modify_subject_by_id(Subject search_subject, Subject modification_subject) {
    return super.modify(search_subject, modification_subject, Arrays.asList("id"), null);
  }

  public Service_response<Integer> delete_subject_by_id(Subject subject) {
    return super.delete(subject, Arrays.asList("id"));
  }

  public Service_response<List<Subject>> find_subject_by_id(Subject subject) {
    return super.find(subject, Arrays.asList("id"));
  }

  public Service_response<List<Subject>> find_subject_by_id(Integer id) {
    Subject subject = new Subject();
    subject.set_id(id);
    return find_subject_by_id(subject);
  }

  public Service_response<Integer> delete_subject_by_id(Integer id) {
    Subject subject = new Subject();
    subject.set_id(id);
    return delete_subject_by_id(subject);
  }

}
