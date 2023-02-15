package config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Base_entity;

public class Statement_helpers {

  int parameter_index;

  public Statement_helpers() {
    parameter_index = 1;
  }

  public <T extends Base_entity> String build_add_sql(List<String> fields, T entity) {
    String table_name = Sql_helpers.get_sql_table_name(entity);
    String columns = Sql_helpers.get_sql_columns(fields);
    String place_holders = Sql_helpers.get_sql_place_holders(fields);

    String sql = String.format("insert into %s (%s) values (%s)", table_name, columns, place_holders);
    return sql;
  }

  public <T extends Base_entity> String build_modify_sql(List<String> modification_fields,
      List<String> search_fields, T search_entity, T modifcation_entity) {

    String table_name = Sql_helpers.get_sql_table_name(search_entity);
    String modified_columns = Sql_helpers.get_sql_columns_equal_place_holders(modification_fields, ",");
    String search_columns = Sql_helpers.get_sql_columns_equal_place_holders(search_fields, "and");

    String sql = String.format("update %s set %s where %s", table_name, modified_columns, search_columns);
    return sql;
  }

  public <T extends Base_entity> String build_find_sql(List<String> fields, T entity) {

    String table_name = Sql_helpers.get_sql_table_name(entity);
    String columns = Sql_helpers.get_sql_columns_equal_place_holders(fields, "and");

    if (columns.length() == 0)
      columns = "1";

    String sql = String.format("select * from %s where %s", table_name, columns);
    return sql;
  }

  public <T extends Base_entity> String build_delete_sql(List<String> fields, T entity) {

    String table_name = Sql_helpers.get_sql_table_name(entity);
    String columns = Sql_helpers.get_sql_columns_equal_place_holders(fields, "and");

    String sql = String.format("delete from %s where %s", table_name, columns);
    return sql;
  }

  public <T extends Base_entity> List<String> normalize_fields(List<String> fields, T entity) {
    if (fields == null)
      fields = entity.get_field_names();
    return fields;
  }

  public String build_statment_method(String perfix, String type) {
    if (type.equals("Integer"))
      type = "Int";

    return String.format("%s%s", perfix, type);

  }

  public String get_statment_getter_return_type(String getter_name, ResultSet result) throws Exception {
    String statement_getter_return_type = result.getClass().getDeclaredMethod(
        getter_name, String.class).getReturnType().getTypeName();

    if (statement_getter_return_type.equals("int"))
      statement_getter_return_type = "java.lang.Integer";
    else if (statement_getter_return_type.equals("double"))
      statement_getter_return_type = "java.lang.Double";
    else if (statement_getter_return_type.equals("float"))
      statement_getter_return_type = "java.lang.Float";
    else if (statement_getter_return_type.equals("string"))
      statement_getter_return_type = "java.lang.String";

    return statement_getter_return_type;
  }

  public <T extends Base_entity> PreparedStatement set_fields(PreparedStatement stmt, List<String> fields,
      T entity) throws Exception {

    for (String field : fields) {
      String entity_getter = entity.get_field_getter(field);
      Object field_value = entity.getClass().getDeclaredMethod(entity_getter).invoke(entity);
      stmt.setObject(parameter_index, field_value);
      parameter_index++;
    }

    return stmt;

  }

  public <T extends Base_entity> List<T> get_fields(ResultSet result, T entity) throws Exception {
    List<T> rows = new ArrayList<>();
    List<String> field_names = entity.get_field_names();

    while (result.next()) {
      @SuppressWarnings("unchecked")
      T row = (T) entity.getClass().newInstance();

      for (String field_name : field_names) {
        String field_getter_name = entity.get_field_getter(field_name);
        String field_getter_return_type = entity.get_field_getter_return_type(field_getter_name);
        String statment_getter_name = build_statment_method("get", field_getter_return_type);
        String statement_getter_return_type = get_statment_getter_return_type(statment_getter_name, result);
        Object value = result.getClass().getDeclaredMethod(
            statment_getter_name, String.class).invoke(result, field_name);

        String field_setter_name = entity.get_field_setter(field_name);
        entity.getClass().getDeclaredMethod(field_setter_name, Class.forName(statement_getter_return_type)).invoke(row,
            value);
      }

      rows.add(row);

    }

    return rows;
  }

}
