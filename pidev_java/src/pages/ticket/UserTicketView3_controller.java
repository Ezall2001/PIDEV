/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages.ticket;

import entities.Message;
import entities.Ticket;
import entities.User;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import services.Message_service;
import services.ObjectComplianceException;
import services.Ticket_service;
import services.User_session_service;

/**
 * FXML Controller class
 *
 * @author moezt
 */
public class UserTicketView3_controller implements Initializable {

    @FXML
    private TextField categoryTF;
    @FXML
    private TextField titleTF;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox messagesWrapper;
    @FXML
    private Button sendBtn;
    @FXML
    private Label contentCtrlLbl;
    @FXML
    private TextArea contentTA;

    private Ticket t;

    private final int TA_MAX_CHARACTERS = 300;

    /**
     * Initializes the controller class.
     */
    private void populateMessages(Ticket ticket) {
        List<Message> messages = ticket.getMessages();
        messagesWrapper.getChildren().clear();
        for (Message message : messages) {
            TextArea messageArea = new TextArea(message.getContent());
            messageArea.setEditable(false);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Label dateLabel = new Label(message.getSend_dt().toLocalDateTime().format(formatter));
            VBox messageBox = new VBox(messageArea, dateLabel);
            messageBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 5px;");
            messageBox.setSpacing(5);
            messagesWrapper.getChildren().add(messageBox);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            contentTA.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > TA_MAX_CHARACTERS) {
                    contentTA.setText(oldValue);
                } else {
                    contentCtrlLbl.setText(newValue.length() + "/300");
                }
            });

            /*User_session_service u_s_s = new User_session_service();
            User u = u_s_s.get_user();*/
            //Dummy user
            Integer user_id = 16;
            User u = new User();
            u.set_id(user_id);
            u.set_first_name("eya");
            u.set_last_name("harbi");
            u.set_type("STUDENT");

            Ticket_service t_s = new Ticket_service();
            t = t_s.read(u);

            Ticket.Category categorySwitch = t.getCategory();
            String category = null;

            switch (categorySwitch) {
                case TECHNICAL:
                    category = "Problème technique";
                    break;
                case ACCOUNT_ACCESS:
                    category = "Accès au compte";
                    break;
                case FEEDBACK:
                    category = "Avis";
                    break;
                case INQUIRY:
                    category = "Demande de renseignements";
                    break;
                case CONFLICT:
                    category = "Conflit";
                    break;
            }
            categoryTF.setText(category);

            String title = t.getTitle();
            titleTF.setText(title);

            populateMessages(t);
        } catch (SQLException ex) {
            Logger.getLogger(UserTicketView3_controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UserTicketView3_controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectComplianceException ex) {
            Logger.getLogger(UserTicketView3_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveMessage(ActionEvent event) {

        contentCtrlLbl.setText("");

        String content = contentTA.getText();
        Message m = new Message(content);
        t.addMessage(m);

        Message_service m_s = new Message_service();
        try {
            m_s.add(m);
        } catch (SQLException ex) {
            Logger.getLogger(UserTicketView3_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        populateMessages(t);

        contentTA.setText("");

    }

}
