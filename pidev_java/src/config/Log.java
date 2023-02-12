package config;

public class Log {

  private static String verify_toString_implementation(Object obj) {
    try {
      if (obj.getClass().getMethod("toString").getDeclaringClass() != Object.class)
        return obj.toString();
      return String.format("%s n'implemente pas la methode toString", obj.getClass());

    } catch (NoSuchMethodException e) {
      return e.getMessage();
    }

  }

  public static void console(Object obj) {
    String message = verify_toString_implementation(obj);
    System.out.println(message);
  }

  /// TODO: finish the file output log
  public static void file(Object obj) {
    String message = verify_toString_implementation(obj);
    System.out.println(message);
  }

  public static void fichier(String message) {
  }

}
