package entities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import config.Entity_getter_setter_mapper;
import config.Log;
import types.Getter_setter_map;
import types.Getter_setter_pair;

public class Base_entity {
  protected Getter_setter_map getter_setter_map;

  protected Base_entity() {
  }

  protected Base_entity(Getter_setter_map getter_setter_map) {
    this.getter_setter_map = getter_setter_map;
  }

  public Getter_setter_map get_getter_setter_map() {
    return getter_setter_map;
  }

  public void set_getter_setter_map(Getter_setter_map getter_setter_map) {
    try {
      Entity_getter_setter_mapper.verify_map(this, getter_setter_map);
      this.getter_setter_map = getter_setter_map;
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public Getter_setter_map build_getter_setter_map(Getter_setter_pair... overrides) {
    return Entity_getter_setter_mapper.build_map(this, overrides);
  }

  public Getter_setter_map build_getter_setter_map() {
    return Entity_getter_setter_mapper.build_map(this);
  }

  public String get_field_getter(String field_name) {
    return get_getter_setter_map().get(field_name).get_f();
  }

  public String get_field_setter(String field_name) {
    return get_getter_setter_map().get(field_name).get_l();
  }

  public String get_field_type(String field_name) throws Exception {
    String type = this.getClass().getDeclaredField(field_name).getType().getSimpleName();
    return type.substring(0, 1).toUpperCase().concat(type.substring(1).toLowerCase());
  }

  public String get_field_getter_return_type(String getter_name) throws Exception {
    String type = this.getClass().getDeclaredMethod(getter_name).getReturnType().getSimpleName();
    return type.substring(0, 1).toUpperCase().concat(type.substring(1).toLowerCase());
  }

  public Boolean check_method_exists(String method_name) {
    List<Method> methods = Arrays.asList(this.getClass().getDeclaredMethods());
    return methods.stream().anyMatch(method -> method.getName().equals(method_name));
  }

  public Boolean check_methods_exist(List<String> method_names) {
    return method_names.stream().allMatch(method_name -> check_method_exists(method_name));
  }

  public List<String> get_field_names() {
    return new ArrayList<String>(this.get_getter_setter_map().keySet());
  }

  public Boolean check_field_exists(String field_name) {
    try {
      Field field = this.getClass().getDeclaredField(field_name);
      if (field == null)
        return false;
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean check_fields_exist(List<String> field_names) {
    return field_names.stream().allMatch(field_name -> check_field_exists(field_name));
  }

  public String get_class_name() {
    return this.getClass().getSimpleName();
  }

}
