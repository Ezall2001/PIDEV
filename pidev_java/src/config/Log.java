package config;

public class Log {

  public Log() {

  }

  private static String verifier_implementation_toString(Object obj) {
    try {
      if (obj.getClass().getMethod("toString").getDeclaringClass() != Object.class)
        return obj.toString();
      return String.format("%s n'implemente pas la methode toString", obj.getClass());

    } catch (NoSuchMethodException e) {
      return e.getMessage();
    }

  }

  public static void console(Object obj) {
    String message = verifier_implementation_toString(obj);
    System.out.println(message);
  }

  /// TODO: finish the file output log
  public static void fichier(Object obj) {
    String message = verifier_implementation_toString(obj);
    System.out.println(message);
  }

}
