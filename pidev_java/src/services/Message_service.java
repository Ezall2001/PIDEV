/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Message;
import entities.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.Jdbc_connection;

/**
 *
 * @author moezt
 */
public class Message_service implements ServicesInterface<Message, Ticket> {

    Connection cnx;

    public Message_service() {
        cnx=Jdbc_connection.getInstance();
    }  
    
    /**
     *
     * @param m
     * @throws SQLException
     */
    @Override
    public void add(Message m) throws SQLException {
        int idTicket = m.getTicket().getId();
        Timestamp send_dt = m.getSend_dt();
        String content = m.getContent();
        
        String sqlSt = "insert into messages (id_ticket, send_dt, content) values (?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        
        ps.setInt(1, idTicket);
        ps.setTimestamp(2, send_dt);
        ps.setString(3, content);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
    //read method called by views for all user types
    @Override
    public List<Message> readAll(Ticket t) throws SQLException, ObjectComplianceException {
        int id_ticket =t.getId();
        
        List<Message> messages = new ArrayList<>();
        
        String sqlSt = "select id, send_dt, content from messages where id_ticket=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        
        ps.setInt(1, id_ticket);
        
        ResultSet rs = ps.executeQuery();
        
        if(!rs.first())
            throw (new ObjectComplianceException("Aucun message. Un ticket doit avoir au moins un message"));
        
        rs.beforeFirst();
        
        while(rs.next()) {
            int id = rs.getInt(1);
            Timestamp send_dt = rs.getTimestamp(2);
            String content = rs.getString(3);
            
            Message m = new Message(id, send_dt, content);
            messages.add(m);
        }
        
        rs.close();
        ps.close();
        
        return messages;
    }
    
     @Override
    public List<Message> readAll() throws SQLException, ObjectComplianceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    //useless method. messages aren't supposed to be updated. implemented to conform to criteria grid
    @Override
    public void update(Message m) throws SQLException {
        
        String content = m.getContent();
        int id_ticket = m.getTicket().getId();
        int id = m.getId();
        
        String sqlSt = "update messages set content=? where id_ticket=? and id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        
        ps.setString(1, content);
        ps.setInt(2, id_ticket);
        ps.setInt(3, id);
        
        ps.executeUpdate();
        
        ps.close();
        
        }

    @Override
    public void delete(Message m) throws SQLException {
        
        int id = m.getId();
        int id_ticket = m.getTicket().getId();
        
        String sqlSt = "delete from messages where id_ticket=? and id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        
        ps.setInt(1, id_ticket);
        ps.setInt(2, id);
        
        ps.executeUpdate();
        
        ps.close();
        
        }
    
}
