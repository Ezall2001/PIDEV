/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import entities.Message;
import entities.Ticket;
import entities.User;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.Message_service;
import services.ObjectComplianceException;
import services.Ticket_service;

/**
 *
 * @author moezt
 */
public class Moez_test {
    
    public static void main(String[] args) {
        
        Integer user_id = 16;
        User u = new User();
        u.set_id(user_id);
        u.set_first_name("eya");
        u.set_last_name("harbi"); 
        u.set_type("STUDENT");
        
        
        /*Ticket t = new Ticket("Oh no! My account gone Bye Bye", Ticket.Category.ACCOUNT_ACCESS);
        t.setUser(u);
        t.setId(2);
        System.out.println("The id is " + t.getUser().get_id() + " and the full name is " + t.getUser().get_full_name());
        Message m = new Message("Eh ohh je vous parle! Vous ignorez vos clients?!");
        t.addMessage(m);*/
        Ticket t = new Ticket();
        t.setId(3);
        t.setDestruct_dt();
        /*Message_service m_s = new Message_service();
        try {
            List<Message> messages = m_s.readAll(t);
            t.setMessages(messages);
        } catch (SQLException ex) {
            Logger.getLogger(Moez_test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectComplianceException ex) {
            Logger.getLogger(Moez_test.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //t.setCategory(Ticket.Category.FEEDBACK);
        //t.setState(Ticket.State.ONGOING);
        //t.setPriority(Ticket.Priority.LOW);
        //Ticket_service t_s = new Ticket_service();
        Ticket_service t_s = new Ticket_service();
        
        try {
            //t_s.add(t);
            //Ticket t = t_s.read(u);
            t_s.update(t.getId(), t.getDestruct_dt());
            //t_s.delete(t);
        } catch (SQLException ex) {
            Logger.getLogger(Moez_test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Moez_test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
