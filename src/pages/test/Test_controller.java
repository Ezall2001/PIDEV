package pages.test;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

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
import javafx.scene.control.Button;
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
    void delete_test_btn(MouseEvent event) {
        service.delete(selected_test.get_id());
        JOptionPane.showMessageDialog(null, "Test supprimé avec succés");
    }

    @FXML
    void edit_test_btn(MouseEvent event) {
        update(selected_test);
        JOptionPane.showMessageDialog(null, "Test modifié avec succés !");
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
                id_tf.setText(Integer.toString(selected_test.get_id()));
                duree_tf.setText(Integer.toString(selected_test.get_duration()));
                seuil_tf.setText(Integer.toString(selected_test.get_min_points()));
                type_cb.setValue(selected_test.getType());

            }
        });

    }

    @FXML
    void consulter_test(MouseEvent event) throws IOException {

        selected_test = list_id.getSelectionModel().getSelectedItem();
        Shared_model_nour model = Shared_model_nour.getInstance();
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

    // ObservableList<Map.Entry<Test, List<Test_qs>>> qs_items = FXCollections
    //         .observableArrayList(service.get_with_questions(selected_test.get_id()).entrySet());

    @FXML
    void show_qs_btn(MouseEvent event) {
        qs_controller qs = new qs_controller();

        selected_test = list_id.getSelectionModel().getSelectedItem();
        // service.get_with_questions(selected_test.get_id());
        show_qs_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                Parent parent;
                try {
                    parent = FXMLLoader.load(getClass().getResource("qs.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.show();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        });
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
