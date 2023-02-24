package types;

// import java.lang.reflect.Field;

public class check {

    // public static boolean checkNull(Object o) throws IllegalAccessException {
    //     Field[] all_fields = Object.class.getDeclaredFields();

    //     for (Field f : all_fields)
    // f.setAccessible(true);
    // Type t = f.getType();
    // if (t.equals(String.class) && f != null) {
    //     return true;
    // }

    //         if (f != null)
    //             return false;
    //     return true;

    // }

    public static boolean StringValidator(String s) {
        if (s.length() > 0)
            return true;
        else
            return false;
    }

    public static boolean StringsValidator(String a, String b, String c, String d, String e, String f) {
        if (a.length() > 0 && b.length() > 0 && c.length() > 0 && d.length() > 0 && e.length() > 0 && f.length() > 0)
            return true;
        else
            return false;
    }

    public static boolean IntValidator(int i) {
        if (i != 0)
            return true;
        else
            return false;
    }

}
