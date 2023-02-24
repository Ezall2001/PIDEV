package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc_connection {
  private static Connection cnx_instance = null;

  String url = "jdbc:mysql://localhost:3306/pidev";
  String user = "root";
  String password = "";

  private Jdbc_connection() {

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    try {
      cnx_instance = DriverManager.getConnection(url, user, password);
      Log.file("connection etablis");
    } catch (SQLException e) {
      Log.file(e.getMessage());
    }
  }

  public static Connection getInstance() {
    if (cnx_instance == null)
      new Jdbc_connection();
    return cnx_instance;
  }

  public Connection getCnx() {
    return cnx_instance;
  }

}
