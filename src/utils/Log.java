package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log {

  private static String get_output(Object obj) {
    if (obj == null)
      return "NULL";

    try {
      if (obj.getClass().getMethod("toString").getDeclaringClass() != Object.class)
        return obj.toString();
      return String.format("%s n'implemente pas la methode toString", obj.getClass());

    } catch (NoSuchMethodException e) {
      return e.getMessage();
    }
  }

  ///TODO: handle null obj
  public static void console(Object obj) {
    String message = get_output(obj);
    System.out.println(message);
  }

  public static void file(Object obj) {
    LocalDateTime timestamp = LocalDateTime.now();
    String message = String.format("%s : %s\n", timestamp, get_output(obj));

    try {

      FileWriter file_writer = new FileWriter("logs/log.txt", true);
      file_writer.write(message);
      file_writer.close();

    } catch (IOException e) {
      console(e.getMessage());

    }
  }

}
