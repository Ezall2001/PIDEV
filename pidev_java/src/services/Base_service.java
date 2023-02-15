package services;

import config.Jdbc_connection;
import entities.Base_entity;
import types.Service_response;
import config.Log;
import config.Statement_helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Base_service {
  protected static final Connection cnx = Jdbc_connection.getInstance();
  protected String service_name;

  protected Base_service(String service_name) {
    this.service_name = service_name;
  }

  private String get_service_class_name() {
    return this.getClass().getSimpleName();
  }

  private <T extends Base_entity> void validate_fields(T entity, List<String> fields) throws Exception {
    if (!entity.check_fields_exist(fields))
      throw new Exception(String.format("fields %s do not exist in entity %s", fields, entity.get_class_name()));
  }

  protected <T> Service_response<T> get_success_response(String method_name, String entity_name, T body) {
    Log.file(String.format("Success : %s : %s : %s", get_service_class_name(), method_name, entity_name));
    return new Service_response<T>(body);
  }

  protected <T> Service_response<T> get_error_response(String method_name, String entity_name, Exception erreur) {
    Log.file(String.format("Erreur : %s : %s : %s : %s", get_service_class_name(), method_name, entity_name,
        erreur.getMessage()));
    return new Service_response<T>(erreur);
  }

  protected <T extends Base_entity> Service_response<Integer> add(T entity, List<String> fields) {
    String method_name = "ajout";
    Statement_helpers stmt_helpers = new Statement_helpers();

    try {
      fields = stmt_helpers.normalize_fields(fields, entity);
      validate_fields(entity, fields);
      String sql = stmt_helpers.build_add_sql(fields, entity);
      PreparedStatement stmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt = stmt_helpers.set_fields(stmt, fields, entity);
      stmt.executeUpdate();
      ResultSet key = stmt.getGeneratedKeys();
      if (key.next())
        return get_success_response(method_name, entity.get_class_name(), key.getInt(1));

      throw new Exception("no key retruned");
    } catch (Exception e) {
      return get_error_response(method_name, entity.get_class_name(), e);
    }
  }

  protected <T extends Base_entity> Service_response<Integer> modify(T search_entity, T modifcation_entity,
      List<String> search_fields, List<String> modification_fields) {
    String method_name = "modification";
    Statement_helpers stmt_helpers = new Statement_helpers();

    try {
      search_fields = stmt_helpers.normalize_fields(search_fields, search_entity);
      modification_fields = stmt_helpers.normalize_fields(modification_fields, modifcation_entity);
      validate_fields(search_entity, search_fields);
      validate_fields(modifcation_entity, modification_fields);

      String sql = stmt_helpers.build_modify_sql(modification_fields, search_fields, search_entity,
          modifcation_entity);

      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt = stmt_helpers.set_fields(stmt, modification_fields, modifcation_entity);
      stmt = stmt_helpers.set_fields(stmt, search_fields, search_entity);
      Integer updated_rows_count = stmt.executeUpdate();
      return get_success_response(method_name, search_entity.get_class_name(), updated_rows_count);

    } catch (Exception e) {
      return get_error_response(method_name, search_entity.get_class_name(), e);
    }
  }

  protected <T extends Base_entity> Service_response<Integer> delete(T entity,
      List<String> fields) {
    String method_name = "deletion";
    Statement_helpers stmt_helpers = new Statement_helpers();

    try {
      fields = stmt_helpers.normalize_fields(fields, entity);
      validate_fields(entity, fields);

      String sql = stmt_helpers.build_delete_sql(fields, entity);
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt = stmt_helpers.set_fields(stmt, fields, entity);
      Integer updated_rows_count = stmt.executeUpdate();
      return get_success_response(method_name, entity.get_class_name(), updated_rows_count);

    } catch (Exception e) {
      return get_error_response(method_name, entity.get_class_name(), e);
    }
  }

  protected <T extends Base_entity> Service_response<List<T>> find(T entity,
      List<String> fields) {
    String method_name = "search";
    Statement_helpers stmt_helpers = new Statement_helpers();

    try {
      fields = stmt_helpers.normalize_fields(fields, entity);
      validate_fields(entity, fields);

      String sql = stmt_helpers.build_find_sql(fields, entity);
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt = stmt_helpers.set_fields(stmt, fields, entity);

      ResultSet result = stmt.executeQuery();
      List<T> rows = stmt_helpers.get_fields(result, entity);

      return get_success_response(method_name, entity.get_class_name(), rows);

    } catch (Exception e) {
      return get_error_response(method_name, entity.get_class_name(), e);
    }
  }

}
