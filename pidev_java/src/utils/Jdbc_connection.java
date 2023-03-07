package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc_connection {

  private static String url = "jdbc:mysql://localhost:3306/pidev";
  private static String user = "root";
  private static String password = "";

  private static Connection cnx_instance = init_connection();

  private static Connection init_connection() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    try {
      Connection cnx = DriverManager.getConnection(url, user, password);
      Log.file("connection established");
      return cnx;
    } catch (SQLException e) {
      Log.file(e.getMessage());
    }

    return null;
  }

  public static Connection getInstance() {
    if (cnx_instance == null)
      cnx_instance = init_connection();

    return cnx_instance;
  }

  private Jdbc_connection() {
  }

}
