package config;

import java.lang.reflect.Field;

import entities.Base_entity;
import types.Getter_setter_map;
import types.Getter_setter_pair;

public class Entity_getter_setter_mapper {

  public static <T extends Base_entity> Getter_setter_map build_map(T entity) {
    Getter_setter_map map = new Getter_setter_map();
    Field[] fields = entity.getClass().getDeclaredFields();

    for (Field field : fields) {
      String field_name = field.getName();
      String getter_name = String.format("get_%s", field_name);
      String setter_name = String.format("set_%s", field_name);
      Getter_setter_pair pair = new Getter_setter_pair(field_name, getter_name, setter_name);

      map.put(pair);
    }

    return map;

  }

  public static <T extends Base_entity> Getter_setter_map build_map(T entity, Getter_setter_pair... overrides) {
    Getter_setter_map map = build_map(entity);

    for (Getter_setter_pair override : overrides) {
      if (map.containsKey(override.getKey()))
        map.put(override);
    }

    return map;

  }

  public static <T extends Base_entity> void verify_map(T entity, Getter_setter_map map) throws Exception {
    Boolean is_verified = map.values().stream().allMatch(getter_setter_pair -> {
      String getter = getter_setter_pair.get_f();
      String setter = getter_setter_pair.get_l();
      return entity.check_method_exists(getter) && entity.check_method_exists(setter);
    });

    if (!is_verified)
      throw new Exception(
          String.format("method(s) in getter_setter_map not implemented in entity %s\nhInteger: check override pairs",
              entity.get_class_name()));
  }

}
