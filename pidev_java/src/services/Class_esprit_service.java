package services;

import java.util.Arrays;
import java.util.List;

import entities.Class_esprit;
import types.Service_response;

public class Class_esprit_service extends Base_service {
  public Class_esprit_service() {

  }

  public Service_response<Integer> add_class_esprit(Class_esprit class_esprit) {
    return super.add(class_esprit, null);
  }

  public Service_response<Integer> delete_class_esprit_by_id(Class_esprit class_esprit) {
    return super.delete(class_esprit, Arrays.asList("id"));
  }

  public Service_response<Integer> modify_class_esprit_by_id(Class_esprit search_class_esprit,
      Class_esprit modification_class_esprit) {
    return super.modify(search_class_esprit, modification_class_esprit, Arrays.asList("id"), null);
  }

  public Service_response<List<Class_esprit>> find_class_esprit_by_id(Class_esprit class_esprit) {
    return super.find(class_esprit, Arrays.asList("id"));
  }
}
