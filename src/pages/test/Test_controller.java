package pages.test;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;
import entities.Test;
import entities.Test_qs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import services.Test_service;
import utils.Jdbc_connection;
import utils.Log;

import utils.Shared_model_nour;

public class Test_controller implements Initializable {

    @FXML
    private Button delete_btn;

    @FXML
    private TextField duree_tf;

    @FXML
    private Button edit_btn;

    @FXML
    private Button refresh_btn;

    @FXML
    private TextField id_tf;

    @FXML
    private TextField seuil_tf;

    @FXML
    private ComboBox<String> type_cb;

    @FXML
    private Button add_test;

    @FXML
    private ListView<Test> list_id;

    @FXML
    private Button show_qs_btn;

    @FXML
    private Button consulter_test_btn;

    @FXML
    private Button passer_test_btn;

    @FXML
    private Button tri_btn;

    @FXML
    private Button chercher_test_btn;

    @FXML
    private TextField chercher_test_tf;

    Shared_model_nour model = Shared_model_nour.getInstance();

    @FXML
    void add_test_btn(MouseEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("ajout_test.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    Test_service service = new Test_service();

    @FXML
    void passer_test_btn_action(MouseEvent event) throws IOException {

        selected_test = list_id.getSelectionModel().getSelectedItem();
        model.set_test(selected_test);

        if (selected_test == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un test");
            alert.showAndWait();
        }

        System.out.println(selected_test);

        if (model.get_test().getType() == "matiere") {
            Parent parent = FXMLLoader.load(getClass().getResource("test_home.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else if (model.get_test().getType() == "cours") {
            Parent parent = FXMLLoader.load(getClass().getResource("test_home_course.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }

    }

    @FXML
    void tri_btn_action(MouseEvent event) {
        //desc
        List<Test> x = service.sort_tests_by_id();
        //* converted list -> observable list */
        ObservableList<Test> ob_list = FXCollections.observableArrayList(x);
        // System.out.println(a);
        list_id.setItems(ob_list);

    }

    ObservableList<Test> test_searched;

    @FXML
    void chercher_test_btn_action(MouseEvent event) {
        t = service.get_all2();
        t.clear();
        test_searched = service.search_test(chercher_test_tf.getText());
        list_id.setItems(test_searched);
    }

    @FXML
    void delete_test_btn(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Supprimer ce test ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            service.delete(model.get_test().get_id());
            JOptionPane.showMessageDialog(null, "Test supprimé avec succés");
        }

    }

    @FXML
    void edit_test_btn(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Modifier ce test ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            update(selected_test);
            JOptionPane.showMessageDialog(null, "Test modifié ");
        }
    }

    //!
    ObservableList<String> cb = FXCollections.observableArrayList("cours", "matiere");
    ObservableList<Test> t;
    int index = -1;
    ResultSet rs = null;
    PreparedStatement ps = null;
    private Test selected_test;
    private Test_qs qs;

    //!
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Test_service service = new Test_service();
        t = service.get_all2();
        list_id.setItems(t);
        type_cb.setItems(cb);

    }

    @FXML
    void list_view_selected_test(MouseEvent event) {
        list_id.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {

                selected_test = list_id.getSelectionModel().getSelectedItem();
                model.set_test(selected_test);
                duree_tf.setText(Integer.toString(selected_test.get_duration()));
                seuil_tf.setText(Integer.toString(selected_test.get_min_points()));
                type_cb.setValue(selected_test.getType());

            }
        });

    }

    @FXML
    void consulter_test(MouseEvent event) throws IOException {

        selected_test = list_id.getSelectionModel().getSelectedItem();

        model.set_test(selected_test);

        // Log.console(model.get_test().toString());

        if (selected_test != null) {
            Parent parent = FXMLLoader.load(getClass().getResource("qs.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }

    }

    @FXML
    void refresh_btn(MouseEvent event) {
        t.clear();
        t = service.get_all2();
        list_id.setItems(t);

    }

    //! needed to retrieve data from textfields

    Connection cnx;

    public void update(Test test) {
        cnx = Jdbc_connection.getInstance();
        String sql = "update tests set type=? ,min_points=?, duration=?  where id=? ";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            //!
            int duration = Integer.parseInt(duree_tf.getText());
            int min_points = Integer.parseInt(seuil_tf.getText());
            String type = type_cb.getValue();
            //!
            if (duration != 0 && min_points != 0 && type.length() > 0) {
                ste.setString(1, type);
                ste.setInt(2, min_points);
                ste.setInt(3, duration);
                ste.setInt(4, test.get_id());
                ste.executeUpdate();
                Log.console("test modifié");
            } else {
                Log.console("invalides");
            }

        } catch (SQLException ex) {
            Log.console(ex.getMessage());
        }
    }

}
