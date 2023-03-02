package pages.test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Course;
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
import services.Course_Service;
import services.Test_service;
import utils.Shared_model_nour;

public class test_home_course_controller implements Initializable {

    @FXML
    private Label duree_label;

    @FXML
    private Label difficulté_label;

    @FXML
    private Label nom_cours_label;

    @FXML
    private Button passer_test_cours_btn;

    @FXML
    private Label seuil_label;

    @FXML
    private Pane test_container;

    @FXML
    private Pane title_container;

    Course_Service course_service = new Course_Service();
    Test_service test_service = new Test_service();
    Shared_model_nour model = Shared_model_nour.getInstance();
    Course course_tested;
    Test course_tested_test;

    @FXML
    void passer_test_cours_btn_action(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("course_test.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // test
        model.setCourse(test_service.get_test_course(model.get_test().get_id()));
        course_tested_test = course_service.get_course_test(model.getCourse().get_id_c());

        // System.out.println(course_service.get_by_id_2(79));
        nom_cours_label.setText(model.getCourse().get_course_name());
        difficulté_label.setText(model.getCourse().get_difficulty_String());
        duree_label.setText(Integer.toString(course_tested_test.get_duration()));
        seuil_label.setText(Integer.toString(course_tested_test.get_min_points()));
    }

}
