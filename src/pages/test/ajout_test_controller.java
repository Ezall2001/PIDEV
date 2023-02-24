package pages.test;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import entities.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.Test_service;
import types.check;

public class ajout_test_controller implements Initializable {

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_clear;

    @FXML
    private TextField duree_tf;

    @FXML
    private TextField seuil_tf;

    @FXML
    private ComboBox<String> type_cb;

    @FXML
    void add_test_action(MouseEvent event) {

        int min_points = Integer.parseInt(seuil_tf.getText());
        int duree = Integer.parseInt(duree_tf.getText());
        String type = type_cb.getValue();

        Test test = new Test(22, min_points, duree, type);
        Test_service service = new Test_service();

        if (min_points != 0 && duree != 0 && !type.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Test ajoutée avec succée");
            service.add(test);

        } else if (seuil_tf.getText().length() == 0 || duree_tf.getText().length() == 0 || type.length() == 0
                || duree == 0 || min_points == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("données invalides");
            alert.showAndWait();
        }

    }

    @FXML
    void clear_fields_btn(MouseEvent event) {
        seuil_tf.setText("");
        duree_tf.setText("");
        type_cb.setValue(null);
    }

    ObservableList<String> cb = FXCollections.observableArrayList("cours", "matiere");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type_cb.setItems(cb);
    }

}
