package config;

import java.util.List;
import java.util.stream.Collectors;

import entities.Base_entity;

public class Sql_helpers {

  public static <T extends Base_entity> String get_sql_table_name(T entity) {
    String[] package_chemain = entity.getClass().getTypeName().split("\\.");
    return package_chemain[package_chemain.length - 1].toLowerCase();
  }

  public static String get_sql_columns(List<String> fields) {
    String sql_columns = "";
    for (String field : fields)
      sql_columns = sql_columns.concat(field).concat(",");

    sql_columns = sql_columns.substring(0, sql_columns.length() - 1);
    return sql_columns;
  }

  public static String get_sql_place_holders(List<String> fields) {
    String sql_place_holders = "";
    for (Integer i = 0; i < fields.size(); i++)
      sql_place_holders = sql_place_holders.concat("?").concat(",");

    sql_place_holders = sql_place_holders.substring(0, sql_place_holders.length() - 1);
    return sql_place_holders;
  }

  public static String get_sql_columns_equal_place_holders(List<String> fields, String seperator) {
    List<String> columns_equal_place_holders = fields.stream().map(field -> field.concat("=?"))
        .collect(Collectors.toList());

    return String.join(String.format(" %s ", seperator), columns_equal_place_holders);

  }
}
