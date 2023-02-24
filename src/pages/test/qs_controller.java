
package pages.test;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import entities.Test_qs;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.Test_qs_service;
import types.check;
import utils.Jdbc_connection;
import utils.Log;

public class qs_controller implements Initializable {

    @FXML
    private Button add_qs_btn;

    @FXML
    private TextField correct_option_tf;

    @FXML
    private ListView<Test_qs> liste_qs;

    @FXML
    private TextField optionA_tf;

    @FXML
    private TextField optionB_tf;

    @FXML
    private TextField optionC_tf;

    @FXML
    private TextField optionD_tf;

    @FXML
    private TextField qs_number_tf;

    @FXML
    private TextField qs_tf;

    @FXML
    private Button return_btn;
    @FXML
    private Button delete_btn;

    @FXML
    private Button edit_qs_btn;

    Test_qs_service service = new Test_qs_service();

    @FXML
    void add_qs_btn(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("ajout_qs.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    void return_btn(MouseEvent event) {

    }

    @FXML
    void delete_btn(MouseEvent event) {
        service.delete(qs_selected.get_id());
        JOptionPane.showMessageDialog(null, "Question supprimée avec succés");
    }

    @FXML
    void edit_btn(MouseEvent event) {
        update(qs_selected);
        JOptionPane.showMessageDialog(null, "Question modifiée avec succés");
    }

    private Test_qs qs_selected;

    @FXML
    void selected_qs(MouseEvent event) {
        liste_qs.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                qs_selected = liste_qs.getSelectionModel().getSelectedItem();
                qs_number_tf.setText(Integer.toString(qs_selected.get_question_number()));
                qs_tf.setText(qs_selected.get_question());
                correct_option_tf.setText(qs_selected.get_correct_option());
                optionA_tf.setText(qs_selected.get_optionA());
                optionB_tf.setText(qs_selected.get_optionD());
                optionC_tf.setText(qs_selected.get_optionC());
                optionD_tf.setText(qs_selected.get_optionD());
            }

        });
    }

    ObservableList<Test_qs> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Test_qs_service service = new Test_qs_service();
        list = service.get_all22();
        liste_qs.setItems(list);
    }

    Connection cnx;

    public void update(Test_qs question) {
        cnx = Jdbc_connection.getInstance();
        String sql = "update test_qs set question_number=?, question=?, correct_option=?, optionA=?"
                +
                ", optionB=?, optionC=?, optionD=? where id=? ";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            //!
            int question_number = Integer.parseInt(qs_number_tf.getText());
            String optionA = optionA_tf.getText();
            String optionB = optionB_tf.getText();
            String optionC = optionC_tf.getText();
            String optionD = optionD_tf.getText();
            String correct_option = correct_option_tf.getText();
            String qs = qs_tf.getText();
            //!
            ste.setInt(1, question_number);
            ste.setString(2, qs);
            ste.setString(3, correct_option);
            ste.setString(4, optionA);
            ste.setString(5, optionB);
            ste.setString(6, optionC);
            ste.setString(7, optionD);
            ste.setInt(8, question.get_id());
            ste.executeUpdate();
            Log.file("question modifiée");

        } catch (SQLException ex) {
            Log.file(ex.getMessage());
        }

    }

}
