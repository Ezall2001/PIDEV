/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages.ticket;

import entities.Ticket;
import entities.Message;
import entities.User;
import java.io.IOException;
import services.Ticket_service;
import services.Message_service;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import services.User_session_service;

/**
 * FXML Controller class
 *
 * @author moezt
 */
public class UserTicketView1_controller implements Initializable {

    ObservableList<String> categoryList = FXCollections.observableArrayList("Problème technique", "Accès au compte", "Avis", "Demande de renseignements", "Conflit");

    @FXML
    private ComboBox<String> categoryBx;
    @FXML
    private Label categoryCtrlLbl;
    @FXML
    private TextField titleTF;
    @FXML
    private Label titleCtrlLbl;
    @FXML
    private TextArea contentTA;
    @FXML
    private Label contentCtrlLbl;
    @FXML
    private Button sendBtn;
    @FXML
    private Button cancelBtn;

    private final int TA_MAX_CHARACTERS = 300;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryBx.setItems(categoryList);

        titleTF.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().length() <= 100 ? change : null));

        // Add listener to text property of text field
        titleTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 99) {
                // Show warning label when user types more than 100 characters
                titleCtrlLbl.setText("Impossible de dépasser 100 caractères");
            } else {
                titleCtrlLbl.setText("");
            }
        });

        contentTA.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > TA_MAX_CHARACTERS) {
                contentTA.setText(oldValue);
            } else {
                contentCtrlLbl.setText(newValue.length() + "/300");
            }
        });
    }

    @FXML
    private void saveTicket(ActionEvent event) {

        categoryCtrlLbl.setText("");
        titleCtrlLbl.setText("");
        contentCtrlLbl.setText("");

        Ticket.Category category = null;
        String categorySwitch = categoryBx.getValue();
        String title = titleTF.getText();
        String content = contentTA.getText();

        if ((categorySwitch == null) && (title.isEmpty()) && (content.isEmpty())) {
            categoryCtrlLbl.setText("Veuillez entrer une catégorie!");
            titleCtrlLbl.setText("Veuillez entrer un sujet!");
            contentCtrlLbl.setText("Veuillez expliquer votre réclamation!");
        } else if ((categorySwitch == null) && (title.isEmpty())) {
            categoryCtrlLbl.setText("Veuillez entrer une catégorie!");
            titleCtrlLbl.setText("Veuillez entrer un sujet!");
        } else if ((categorySwitch == null) && (content.isEmpty())) {
            categoryCtrlLbl.setText("Veuillez entrer une catégorie!");
            contentCtrlLbl.setText("Veuillez expliquer votre réclamation!");
        } else if ((title.isEmpty()) && (content.isEmpty())) {
            titleCtrlLbl.setText("Veuillez entrer un sujet!");
            contentCtrlLbl.setText("Veuillez expliquer votre réclamation!");
        } else if ((categorySwitch == null)) {
            categoryCtrlLbl.setText("Veuillez entrer une catégorie!");
        } else if ((title.isEmpty())) {
            titleCtrlLbl.setText("Veuillez entrer un sujet!");
        } else if ((content.isEmpty())) {
            contentCtrlLbl.setText("Veuillez expliquer votre réclamation!");
        } else {

            switch (categorySwitch) {
                case "Problème technique":
                    category = Ticket.Category.TECHNICAL;
                    break;
                case "Accès au compte":
                    category = Ticket.Category.ACCOUNT_ACCESS;
                    break;
                case "Avis":
                    category = Ticket.Category.FEEDBACK;
                    break;
                case "Demande de renseignements":
                    category = Ticket.Category.INQUIRY;
                    break;
                case "Conflit":
                    category = Ticket.Category.CONFLICT;
                    break;
            }

            Ticket t = new Ticket(title, category);
            Message m = new Message(content);

            Ticket_service t_s = new Ticket_service();
            Message_service m_s = new Message_service();
            //User_session_service u_s_s = new User_session_service();

            //User u = u_s_s.get_user();
            Integer user_id = 16;
            User u = new User();
            u.set_id(user_id);
            u.set_first_name("eya");
            u.set_last_name("harbi");
            u.set_type("STUDENT");
            t.setUser(u);
            try {
                t_s.add(t);
            } catch (SQLException ex) {
                Logger.getLogger(UserTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                int i = t_s.readTicketIdByUser(u);
                t.setId(i);
            } catch (SQLException ex) {
                Logger.getLogger(UserTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(UserTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            m.setTicket(t);

            try {
                m_s.add(m);
            } catch (SQLException ex) {
                Logger.getLogger(UserTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("UserTicketView2.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(UserTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
