package pages.test;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import entities.Test_qs;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import javafx.scene.input.MouseEvent;

import services.Subject_Service;
import types.check;
import utils.Shared_model_nour;

public class subject_test_controller implements Initializable {

    @FXML
    private Label nom_matiere_label;

    @FXML
    private RadioButton optionA_radio_btn;

    @FXML
    private RadioButton optionB_radio_btn;

    @FXML
    private RadioButton optionC_radio_btn;

    @FXML
    private RadioButton optionD_radio_btn;

    @FXML
    private Label question_label;

    @FXML
    private Button terminer_test_btn;

    @FXML
    private Button question_suivante_btn;

    Shared_model_nour model = Shared_model_nour.getInstance();
    Subject_Service sub_service = new Subject_Service();
    List<Test_qs> question_list = sub_service.get_subject_test_questions(model.getSubject().get_id());
    Test_qs[] array = question_list.toArray(new Test_qs[0]);
    int i = 0;
    String correct_option;
    int note = 0;

    @FXML
    void terminer_test_btn_action(MouseEvent event) {

    }

    @FXML
    void question_suivante_btn_action(MouseEvent event) throws IOException {

        i++;
        if (i < sub_service.count_subject_questions(model.getSubject().get_id())) {
            check.clear_options(optionA_radio_btn, optionB_radio_btn, optionC_radio_btn, optionD_radio_btn);
            question_label.setText(array[i].get_question());
            nom_matiere_label.setText(model.getSubject().get_subject_name());
            optionA_radio_btn.setText(array[i].get_optionA());
            optionB_radio_btn.setText(array[i].get_optionB());
            optionC_radio_btn.setText(array[i].get_optionC());
            optionD_radio_btn.setText(array[i].get_optionD());
            correct_option = array[i].get_correct_option();
        }
        if (i == sub_service.count_subject_questions(model.getSubject().get_id()) - 1)
            terminer_test_btn.setVisible(true);
        System.out.println(note);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        terminer_test_btn.setVisible(false);
        //----------------------------------- Only one option can be selected ----------------------------------------
        check.select_one_option(optionA_radio_btn, optionB_radio_btn, optionC_radio_btn, optionD_radio_btn);
        //--------------------------------------------------------------------------------------------------  
        question_label.setText(array[i].get_question());
        nom_matiere_label.setText(model.getSubject().get_subject_name());
        optionA_radio_btn.setText(array[i].get_optionA());
        optionB_radio_btn.setText(array[i].get_optionB());
        optionC_radio_btn.setText(array[i].get_optionC());
        optionD_radio_btn.setText(array[i].get_optionD());
        correct_option = array[i].get_correct_option();

        // --------------------------------- get the selected option and compare it with the correct one ------------------

        optionA_radio_btn.setOnAction(event -> {
            if (correct_option.equals(optionA_radio_btn.getText()) && optionA_radio_btn.isSelected())
                note++;
        });
        optionB_radio_btn.setOnAction(event -> {
            if (correct_option.equals(optionB_radio_btn.getText()) && optionB_radio_btn.isSelected())
                note++;
        });

        optionC_radio_btn.setOnAction(event -> {
            if (correct_option.equals(optionC_radio_btn.getText()) && optionC_radio_btn.isSelected())
                note++;
        });

        optionD_radio_btn.setOnAction(event -> {
            if (correct_option.equals(optionD_radio_btn.getText()) && optionD_radio_btn.isSelected())
                note++;
        });

        System.out.println(note);
    }

}