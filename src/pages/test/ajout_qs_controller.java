package pages.test;

import java.io.IOException;

import javax.swing.JOptionPane;

import entities.Test;
import entities.Test_qs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.Test_qs_service;

public class ajout_qs_controller {

    @FXML
    private Button add_qs_btn;

    @FXML
    private TextField correct_option_tf;

    @FXML
    private TextField optionA_tf;

    @FXML
    private TextField optionB_tf;

    @FXML
    private TextField optionC_tf;

    @FXML
    private TextField optionD_tf;

    @FXML
    private TextField qs_num_tf;

    @FXML
    private TextField qs_tf;

    @FXML
    void add_qs_btn(MouseEvent event) throws IOException {

        int question_number = Integer.parseInt(qs_num_tf.getText());
        String question = qs_tf.getText();
        String optionA = optionA_tf.getText();
        String optionB = optionB_tf.getText();
        String optionC = optionC_tf.getText();
        String optionD = optionD_tf.getText();
        String correct_option = correct_option_tf.getText();

        Test_qs q = new Test_qs(1, question_number, question, correct_option, optionA, optionB, optionC, optionD);
        Test test = new Test(1, 70, 60, "cours");
        Test_qs_service service = new Test_qs_service();

        if (question.length() > 0 && optionA.length() > 0 && optionB.length() > 0 && optionC.length() > 0
                && optionD.length() > 0 && correct_option.length() > 0 && question_number != 0) {
            q.set_test(test);
            service.add(q);

            JOptionPane.showMessageDialog(null, "question ajoutée avec succés");
        } else if (question.length() == 0 || optionA.length() == 0 || optionB.length() == 0 || optionC.length() == 0
                || optionD.length() == 0 || correct_option.length() == 0 || question_number == 0
                || qs_num_tf.getText().length() == 0) {
            System.out.println("test");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("données invalides");
            alert.showAndWait();
        }

    }
}