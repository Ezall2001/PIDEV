package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Resource;
import utils.Jdbc_connection;
import utils.Log;

public class Resource_service {
  Connection cnx;

  public Resource_service() {
    cnx = Jdbc_connection.getInstance();
  }

  public Resource add(Resource resource) {
    String sql = "insert into resources(name,file_path,id_session) values (?,?,?)";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setString(1, resource.get_name());
      stmt.setString(2, resource.get_file_path());
      stmt.setInt(3, resource.get_id_session());
      stmt.executeUpdate();

      ResultSet generated_id = stmt.getGeneratedKeys();
      generated_id.next();
      resource.set_id(generated_id.getInt(1));
      return resource;

    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }

  }

  public void update(Resource resource) {
    String sql = "UPDATE resources SET name=? where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setString(1, resource.get_name());
      stmt.setInt(2, resource.get_id());
      stmt.executeUpdate();

    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public void delete_by_id(Integer id) {
    String sql = "delete from resources where id=?";
    try {
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public List<Resource> find_by_id_session(Integer id_session) {
    List<Resource> resources = new ArrayList<>();
    try {
      String sql = "select * from resources where id_session=?";
      PreparedStatement stmt = cnx.prepareStatement(sql);
      stmt.setInt(1, id_session);
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        Resource session = new Resource(
            result.getInt("id"),
            result.getString("name"),
            result.getString("file_path"));

        resources.add(session);

      }
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
    return resources;
  }
}
