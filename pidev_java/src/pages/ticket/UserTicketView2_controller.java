/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages.ticket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author moezt
 */
public class UserTicketView2_controller implements Initializable {

    @FXML
    private ImageView checkmarkImg;
    @FXML
    private Button returnTicketBtn;
    @FXML
    private Button returnHomeBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void on_returnTicketBtn_clicked(ActionEvent event) {
        
                try {
            Parent root = FXMLLoader.load(getClass().getResource("UserTicketView1.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(UserTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void on_returnHomeBtn_clicked(ActionEvent event) {
        
                        try {
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(UserTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
