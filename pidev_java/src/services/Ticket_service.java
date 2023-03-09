/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Message;
import entities.Ticket;
import entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Jdbc_connection;

/**
 *
 * @author moezt
 */
public class Ticket_service implements ServicesInterface<Ticket, User> {
    
    Connection cnx;

    public Ticket_service() {
        
        cnx=Jdbc_connection.getInstance();
    }   
    

    @Override
    public void add(Ticket t) throws SQLException {
        Ticket.Category category = t.getCategory();
        Ticket.State state = t.getState();
        String title = t.getTitle();
        Ticket.Priority priority = t.getPriority();
        Timestamp creat_dt = t.getCreat_dt();
        int id_user = t.getUser().get_id();
        
        String sqlSt = "insert into tickets (category, state, title, priority, creat_dt, id_user) values (?,?,?,?,?,?) ";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        
        ps.setString(1, category.name());
        ps.setString(2, state.name());
        ps.setString(3, title);
        ps.setString(4, priority.name());
        ps.setTimestamp(5, creat_dt);
        ps.setInt(6, id_user);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
    //read method called by User.Type.ETUDIANT view controller
    public Ticket read(User user) throws SQLException, IllegalArgumentException, ObjectComplianceException {
        
        Integer id_user = user.get_id();
        
        String sqlSt = "select id, category, state, title, priority, creat_dt from tickets where id_user=? and state not in ('REJECTED', 'CLOSED') and destruct_dt is null";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        
        ps.setInt(1, id_user);
        
        ResultSet rs = ps.executeQuery();
        
        if(!(rs.last()))
        {
            rs.close();
            ps.close();
            return null;
        }
        if(rs.getRow()!=1)
            throw (new ObjectComplianceException("Plusieurs tickets ouverts"));
        
        int id = rs.getInt(1);
        Ticket.Category category = Ticket.Category.valueOf(rs.getString(2));
        Ticket.State state = Ticket.State.valueOf(rs.getString(3));
        String title = rs.getString(4);
        Ticket.Priority priority = Ticket.Priority.valueOf(rs.getString(5));
        Timestamp creat_dt = rs.getTimestamp(6);
        
        Ticket t = new Ticket(id, category, state, priority, title, creat_dt);
        
        Message_service m_s = new Message_service();
        
        List<Message> messages = m_s.readAll(t);
        
        System.out.println(messages.toString());
        t.setMessages(messages);
        
        rs.close();
        ps.close();
        
        return t;
        
    }
    
    //returns -1 if user has no tickets
    public int readTicketIdByUser(User user) throws SQLException, IllegalArgumentException {
        
        Integer id_user = user.get_id();
        
        String sqlSt = "select id from tickets where id_user=? and state not in ('REJECTED', 'CLOSED') and destruct_dt is null";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        
        ps.setInt(1, id_user);
        
        ResultSet rs = ps.executeQuery();
        
        if(!(rs.last()))
        {
            rs.close();
            ps.close();
            return -1;
        }

        int id = rs.getInt(1);
        
        rs.close();
        ps.close();
        
        return id;
        
    }

    //read method called by User.Type.ADMIN view controller to read all tickets 
    @Override
    public List<Ticket> readAll() throws SQLException, ObjectComplianceException {
        
        Statement s = cnx.createStatement();
        String sqlSt="select * from tickets";
        ResultSet rs = s.executeQuery(sqlSt);
        
        if(!rs.first())
        {
            rs.close();
            s.close();
            return null;
        }
        
        rs.beforeFirst();
        
        User_service u_s = new User_service();
        
        List<Ticket> tickets = new ArrayList<>();
        
        while(rs.next()) {
            int id = rs.getInt(1);
            Ticket.Category category = Ticket.Category.valueOf(rs.getString(2));
            Ticket.State state = Ticket.State.valueOf(rs.getString(3));
            String title = rs.getString(4);
            Ticket.Priority priority = Ticket.Priority.valueOf(rs.getString(5));
            Timestamp creat_dt = rs.getTimestamp(6);
            Timestamp destruct_dt = rs.getTimestamp(7);
            int id_user = rs.getInt(8);
            
            Ticket t = new Ticket(id, category, state, priority, title, creat_dt, destruct_dt);
            
            if (!rs.wasNull())
            {
            User u = u_s.find_by_id(id_user);
            t.setUser(u);
            }
            
            tickets.add(t);
        }
        
        rs.close();
        s.close();
        
        return tickets;
        
     }
    
        @Override
    public List<Ticket> readAll(User user) throws SQLException, ObjectComplianceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Ticket t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
    
    public void update(int id, Ticket.Category category, Ticket.State state, Ticket.Priority priority) throws SQLException {
        String sqlSt="update tickets set category=?, state=?, priority=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        ps.setString(1, category.name());
        ps.setString(2, state.name());
        ps.setString(3, priority.name());
        ps.setInt(4, id);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(int id, Ticket.State state, Ticket.Priority priority) throws SQLException {
        String sqlSt="update tickets set state=?, priority=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        ps.setString(1, state.name());
        ps.setString(2, priority.name());
        ps.setInt(3, id);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(int id, Ticket.Category category, Ticket.Priority priority) throws SQLException {
        String sqlSt="update tickets set category=?, priority=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        ps.setString(1, category.name());
        ps.setString(2, priority.name());
        ps.setInt(3, id);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(int id, Ticket.Category category, Ticket.State state) throws SQLException {
        String sqlSt="update tickets set category=?, state=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        ps.setString(1, category.name());
        ps.setString(2, state.name());
        ps.setInt(3, id);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(int id, Ticket.Category category) throws SQLException {
        String sqlSt="update tickets set category=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        ps.setString(1, category.name());
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(int id, Ticket.State state) throws SQLException {
        String sqlSt="update tickets set state=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        ps.setString(1, state.name());
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(int id, Ticket.Priority priority) throws SQLException {
        String sqlSt="update tickets set priority=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        ps.setString(1, priority.name());
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }
    public void update(int id, Timestamp destruct_dt) throws SQLException {
        String sqlSt="update tickets set destruct_dt=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        ps.setTimestamp(1, destruct_dt);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }
    

    //entité faible-entité forte / relation de composition. Suppression composant requière suppression tous composés
    @Override
    public void delete(Ticket t) throws SQLException {
        
        Message_service m_s = new Message_service();
        List<Message> messages = t.getMessages();
        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()) {
            Message m = iterator.next();
            m_s.delete(m);
        }
        
        int id = t.getId();
        
        String sqlSt = "delete from tickets where id=?";
        PreparedStatement ps = cnx.prepareStatement(sqlSt);
        
        ps.setInt(1, id);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
}
