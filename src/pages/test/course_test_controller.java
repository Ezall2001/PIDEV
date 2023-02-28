package pages.test;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

import entities.Test_qs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.Course_Service;
import types.check;
import utils.Shared_model_nour;

public class course_test_controller implements Initializable {

    @FXML
    private Button imprimer_resultat_btn;

    @FXML
    private Label minutes_label;

    @FXML
    private Label nom_cours_label;

    @FXML
    private RadioButton optionA_radio_btn;

    @FXML
    private RadioButton optionB_radio_btn;

    @FXML
    private RadioButton optionC_radio_btn;

    @FXML
    private RadioButton optionD_radio_btn;

    @FXML
    private VBox qs_labels_vbox;

    @FXML
    private VBox qs_valeurs_labels_vbox;

    @FXML
    private Label question_label;

    @FXML
    private Button question_suivante_btn;

    @FXML
    private Label remarque_label;

    @FXML
    private Label remarque_valeur_label;

    @FXML
    private Label reponses_correctes_label;

    @FXML
    private Label reponses_correctes_valeur_label;

    @FXML
    private Label reponses_incorrectes_label;

    @FXML
    private Label reponses_incorrectes_valeur_label;

    @FXML
    private Label resultat_test_label;

    @FXML
    private Pane résultat_container;

    @FXML
    private Label secondes_label;

    @FXML
    private Button terminer_test_btn;

    @FXML
    private Label total_qs_label;

    @FXML
    private Label total_questions_valeur_label;

    Shared_model_nour model = Shared_model_nour.getInstance();
    Course_Service sub_service = new Course_Service();
    List<Test_qs> question_list = sub_service.get_course_test_questions(model.getCourse().get_id_c());
    Test_qs[] array = question_list.toArray(new Test_qs[0]);
    // ------------------------------------------------------------------

    int i = 0;
    String correct_option;
    int note = 0;
    int reponses_correctes = 0;
    int reponses_incorrectes = 0;
    Timer time;

    @FXML
    void imprimer_resultat_btn_action(MouseEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
                Printer.MarginType.DEFAULT);

        job.getJobSettings().setPageLayout(pageLayout);

        if (job != null) {

            Boolean printed = job.printPage(résultat_container);

            if (printed)
                job.endJob();
            else
                System.out.println("impression échouée");
        }

    }

    @FXML
    void question_suivante_btn_action(MouseEvent event) {

        i++;
        if (i < sub_service.count_course_questions(model.getCourse().get_id_c())) {
            check.clear_options(optionA_radio_btn, optionB_radio_btn, optionC_radio_btn, optionD_radio_btn);
            question_label.setText(array[i].get_question());
            nom_cours_label.setText(model.getCourse().get_course_name());
            optionA_radio_btn.setText(array[i].get_optionA());
            optionB_radio_btn.setText(array[i].get_optionB());
            optionC_radio_btn.setText(array[i].get_optionC());
            optionD_radio_btn.setText(array[i].get_optionD());
            correct_option = array[i].get_correct_option();
        }
        if (i == sub_service.count_course_questions(model.getCourse().get_id_c()) - 1) {
            terminer_test_btn.setVisible(true);
            question_suivante_btn.setVisible(false);
        }

        System.out.println(note);
    }

    @FXML
    void terminer_test_btn_action(MouseEvent event) {
        résultat_container.setVisible(true);
        terminer_test_btn.setVisible(false);
        imprimer_resultat_btn.setVisible(true);

        total_questions_valeur_label
                .setText(String.valueOf(sub_service.count_course_questions(model.getCourse().get_id_c())));
        reponses_correctes_valeur_label.setText(String.valueOf(note));
        reponses_incorrectes_valeur_label
                .setText(String.valueOf(sub_service.count_course_questions(model.getCourse().get_id_c()) - note));

        remarque_valeur_label
                .setText(check.get_remarque(note, sub_service.count_course_questions(model.getCourse().get_id_c())));
        // TODO : container border
        check.set_resultat_color(remarque_valeur_label, resultat_test_label, qs_labels_vbox, résultat_container);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        terminer_test_btn.setVisible(false);
        //----------------------------------- Only one option can be selected ----------------------------------------
        check.select_one_option(optionA_radio_btn, optionB_radio_btn, optionC_radio_btn, optionD_radio_btn);
        //--------------------------------------------------------------------------------------------------  
        question_label.setText(array[i].get_question());
        nom_cours_label.setText(model.getCourse().get_course_name());
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

        //-------------------------------- invisible items ----------------------------------

        résultat_container.setVisible(false);
        imprimer_resultat_btn.setVisible(false);

        System.out.println(note);
    }

}