/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;

/**
 *
 * @author moezt
 */
public class Message {
    private int id;
    private Timestamp send_dt;
    private String content;
    private Ticket ticket;

    public Message() {
    }
    
    //called by Message_service.readAll(Ticket t)
    public Message(int id, Timestamp send_dt, String content) {
        this.id = id;
        this.send_dt = send_dt;
        this.content = content;
    }

    //constructor to be used by controller
    public Message(String content) {
        this.send_dt = new Timestamp(System.currentTimeMillis());
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getSend_dt() {
        return send_dt;
    }

    public void setSend_dt(Timestamp send_dt) {
        this.send_dt = send_dt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    
    
    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", send_dt=" + send_dt + ", content=" + content + '}';
    }
    
}
