package types;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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

    public static void select_one_option(RadioButton optionA, RadioButton optionB, RadioButton optionC,
            RadioButton optionD) {
        ToggleGroup toggleGroup = new ToggleGroup();
        optionA.setToggleGroup(toggleGroup);
        optionB.setToggleGroup(toggleGroup);
        optionC.setToggleGroup(toggleGroup);
        optionD.setToggleGroup(toggleGroup);
    }

    public static void clear_options(RadioButton optionA, RadioButton optionB, RadioButton optionC,
            RadioButton optionD) {
        optionA.setSelected(false);
        optionB.setSelected(false);
        optionC.setSelected(false);
        optionD.setSelected(false);
    }

    // public static void selected_radio_btn(RadioButton optionA, RadioButton optionB, RadioButton optionC,
    //         RadioButton optionD, final int note, String correct_option) {

    //     optionA.setOnAction(event -> {
    //         if (correct_option.equals(optionA.getText()) && optionA.isSelected())
    //             note++;
    //     });
    //     optionB.setOnAction(event -> {
    //         if (correct_option.equals(optionB.getText()) && optionB.isSelected())
    //             note++;
    //     });

    //     optionC.setOnAction(event -> {
    //         if (correct_option.equals(optionC.getText()) && optionC.isSelected())
    //             note++;
    //     });

    //     optionD.setOnAction(event -> {
    //         if (correct_option.equals(optionD.getText()) && optionD.isSelected())
    //             note++;
    //     });
    // }

}
