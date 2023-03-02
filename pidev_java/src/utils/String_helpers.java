package utils;

public class String_helpers {
  public static String capitalize(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
  }
}
