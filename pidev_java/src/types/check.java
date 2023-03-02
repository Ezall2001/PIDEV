package types;

import java.util.StringTokenizer;

import javax.swing.BorderFactory;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Labeled;

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

    // make sure the user selects one option
    public static void select_one_option(RadioButton optionA, RadioButton optionB, RadioButton optionC,
            RadioButton optionD) {
        ToggleGroup toggleGroup = new ToggleGroup();
        optionA.setToggleGroup(toggleGroup);
        optionB.setToggleGroup(toggleGroup);
        optionC.setToggleGroup(toggleGroup);
        optionD.setToggleGroup(toggleGroup);
    }

    // clear radiobtns
    public static void clear_options(RadioButton optionA, RadioButton optionB, RadioButton optionC,
            RadioButton optionD) {
        optionA.setSelected(false);
        optionB.setSelected(false);
        optionC.setSelected(false);
        optionD.setSelected(false);
    }

    public static String get_remarque(int note, int total_qs) {
        if (note == total_qs)
            return "Excellent";
        else if (note >= (total_qs / 2) && note != total_qs)
            return "Assez bien";
        else if (note <= (total_qs / 2) && note != 0)
            return "Pas assez";
        else if (note == 0)
            return "Test échoué";
        else
            return "";
    }

    public static void set_resultat_color(Label label_remarque, Label resultat_test_label, VBox box,
            Pane résultat_container) {
        if (label_remarque.getText().equals("Excellent") || label_remarque.getText().equals("Assez bien")) {
            resultat_test_label.setTextFill(Color.rgb(211, 228, 205, 1));
            // résultat_container.setBorder(BorderFactory.createLineBorder(Color.rgb(0, 0, 0, 0)));
            for (Node child : box.getChildren()) {
                ((Labeled) child).setTextFill(Color.rgb(211, 228, 205, 1));
            }

        }

        else if (label_remarque.getText().equals("Pas assez") || label_remarque.getText().equals("Test échoué")) {
            resultat_test_label.setTextFill(Color.rgb(205, 4, 4, 1));

            for (Node child : box.getChildren()) {
                ((Labeled) child).setTextFill(Color.rgb(205, 4, 4, 1));
            }
        }
    }

    // check whether one radio btn is selected or not 
    public static Boolean check_option_selected(RadioButton optionA, RadioButton optionB, RadioButton optionC,
            RadioButton optionD) {
        if (!optionA.isSelected() && !optionB.isSelected() && !optionC.isSelected() && !optionD.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réponse ! ");
            alert.showAndWait();
            return false;
        } else
            return true;

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
