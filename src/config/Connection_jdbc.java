package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection_jdbc {
  private Connection cnx;
  public static Connection_jdbc cnx_instance = null;

  String url = "jdbc:mysql://localhost:3306/pidev";
  String user = "root";
  String password = "";

  private Connection_jdbc() {

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      Log.console("no driver found");
    }

    try {
      cnx = DriverManager.getConnection(url, user, password);
      Log.console("connection etablie");
    } catch (SQLException e) {
      Log.console(e.getMessage());
      Log.fichier(e.getMessage());
    }
  }

  public static Connection_jdbc getInstance() {
    if (cnx_instance == null)
      cnx_instance = new Connection_jdbc();
    return cnx_instance;
  }

  public Connection getCnx() {
    return cnx;
  }

}
