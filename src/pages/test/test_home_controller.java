package pages.test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Subject;
import entities.Test;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.Subject_Service;
import services.Test_service;
import utils.Shared_model_nour;

public class test_home_controller implements Initializable {

    @FXML
    private Label duree_label;

    @FXML
    private Label nom_matiere_label;

    @FXML
    private Label seuil_label;

    @FXML
    private Button passer_test_btn;

    @FXML
    private Pane test_container;

    @FXML
    private Pane title_container;

    Subject_Service subject_service = new Subject_Service();
    Test_service test_service = new Test_service();
    Shared_model_nour model = Shared_model_nour.getInstance();
    //-----------------------

    Test subject_tested_test;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // test

        model.setSubject(test_service.get_test_subject(model.get_test().get_id()));
        subject_tested_test = subject_service.get_subject_test(model.getSubject().get_id());
        // System.out.println(model.getSubject());

        // System.out.println(subject_service.get_by_id_2(101));
        nom_matiere_label.setText(model.getSubject().get_subject_name());
        duree_label.setText(Integer.toString(subject_tested_test.get_duration()));
        seuil_label.setText(Integer.toString(subject_tested_test.get_min_points()));

    }

    @FXML
    void passer_test_btn_action(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("subject_test.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

        //TODO close main window
    }

}
