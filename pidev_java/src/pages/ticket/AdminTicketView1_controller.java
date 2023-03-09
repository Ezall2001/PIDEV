/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages.ticket;

import entities.Ticket;
import entities.User;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import services.ObjectComplianceException;
import services.Ticket_service;

/**
 * FXML Controller class
 *
 * @author moezt
 */
public class AdminTicketView1_controller implements Initializable {

    @FXML
    private TableColumn<Ticket, Integer> idCol;
    @FXML
    private TableColumn<Ticket, Ticket.Category> categoryCol;
    @FXML
    private TableColumn<Ticket, Ticket.State> stateCol;
    @FXML
    private TableColumn<Ticket, Ticket.Priority> priorityCol;
    @FXML
    private TableColumn<Ticket, String> titleCol;
    @FXML
    private TableColumn<Ticket, Timestamp> creat_dtCol;
    @FXML
    private TableColumn<Ticket, Timestamp> destruct_dtCol;
    @FXML
    private TableColumn<Ticket, User> studentCol;
    //@FXML
 //   private TableColumn<null, null> deleteCol;
    @FXML
    private TableView<Ticket> ticketView;

 
    public void createTicketTable() throws SQLException {
        try {
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
            priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            creat_dtCol.setCellValueFactory(new PropertyValueFactory<>("creat_dt"));
            destruct_dtCol.setCellValueFactory(new PropertyValueFactory<>("destruct_dt"));
            studentCol.setCellValueFactory(new PropertyValueFactory<>("user"));
            //deleteCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            /*studentCol.setCellValueFactory(cellData -> {
            User user = cellData.getValue().getUser();
            return new SimpleStringProperty( ""+user.get_first_name() + " " + user.get_last_name()+"" );
            });*/
            
            Callback<TableColumn<Ticket, Ticket.Category>, TableCell<Ticket, Ticket.Category>> categoryCellFactory =
                    ComboBoxTableCell.forTableColumn(Ticket.Category.values());
            Callback<TableColumn<Ticket, Ticket.State>, TableCell<Ticket, Ticket.State>> stateCellFactory =
                    ComboBoxTableCell.forTableColumn(Ticket.State.values());
            Callback<TableColumn<Ticket, Ticket.Priority>, TableCell<Ticket, Ticket.Priority>> priorityCellFactory =
                    ComboBoxTableCell.forTableColumn(Ticket.Priority.values());
            
            
            categoryCol.setCellFactory(categoryCellFactory);
            stateCol.setCellFactory(stateCellFactory);
            priorityCol.setCellFactory(priorityCellFactory);
            
            
            
            Ticket_service t_s = new Ticket_service();
            
            List<Ticket> ticketList = t_s.readAll();
            
            ObservableList<Ticket> observableTicketList = FXCollections.observableArrayList(ticketList);
            
            ticketView.setItems(observableTicketList);
        } catch (ObjectComplianceException ex) {
            Logger.getLogger(AdminTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            createTicketTable();
        } catch (SQLException ex) {
            Logger.getLogger(AdminTicketView1_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
