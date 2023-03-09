/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author moezt
 */


public class Ticket {
    
    public static enum Category{
    TECHNICAL,
    ACCOUNT_ACCESS,
    FEEDBACK,
    INQUIRY,
    CONFLICT
}
    
    public static enum State{
    NEW,
    WAITING,
    ONGOING,
    REJECTED,
    RESOLVED,
    CLOSED
}
    
   public static enum Priority{
    LOW,
    NORMAL,
    URGENT,
    CRITICAL
} 
    
    private int id;
    private Category category;
    private State state;
    private Priority priority;
    private String title;
    private Timestamp creat_dt;
    private Timestamp destruct_dt;
    private List<Message> messages /*= new ArrayList<>()*/;
    private User user;

    
    //constructors
    
    public Ticket() {
    }
    
    //used in Ticket_service.readAll()
    public Ticket(int id, Category category, State state, Priority priority, String title, Timestamp creat_dt, Timestamp destruct_dt) {
        this.id = id;
        this.category = category;
        this.state = state;
        this.priority=priority;
        this.title = title;
        this.creat_dt = creat_dt;
        this.destruct_dt = destruct_dt;
    }

    //used in Ticket_service.read()
    public Ticket(int id, Category category, State state, Priority priority, String title, Timestamp creat_dt) {
        this.id = id;
        this.category = category;
        this.state = state;
        this.priority=priority;
        this.title = title;
        this.creat_dt = creat_dt;
    }
    
    
    //to be called by user.role==student when creating new ticket
    public Ticket(String title, Category category) {
        this.title = title;
        this.category = category;
        this.state = State.NEW;
        this.priority = Priority.NORMAL;
        this.creat_dt = new Timestamp(System.currentTimeMillis());
    }
    
    //to be called by user.role==administrator when creating new ticket
    public Ticket(String title, Category category, Priority priority) {
        this.title = title;
        this.category = category;
        this.state = State.NEW;
        this.priority = priority;
        this.creat_dt = new Timestamp(System.currentTimeMillis());
        this.messages= new ArrayList<>();
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }    

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreat_dt() {
        this.creat_dt =  new Timestamp(System.currentTimeMillis()); // set creation timestamp to current time
    }

    public void setDestruct_dt() {
        this.destruct_dt =  new Timestamp(System.currentTimeMillis()); // set destruction timestamp to current time
    }

    //initializing association for each message using addMessage()
    public void setMessages(List<Message> messages) {
        this.messages = new ArrayList<>();
        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()) {
            Message m = iterator.next();
            addMessage(m);
        }
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public State getState() {
        return state;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getCreat_dt() {
        return creat_dt;
    }

    public Timestamp getDestruct_dt() {
        return destruct_dt;
    }

    public List<Message> getMessages() {
        return messages;
    }
    
    public User getUser() {
        return user;
    }

    public void addMessage(Message message) {
        message.setTicket(this);
        messages.add(message);
    }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", category=" + category + ", state=" + state + ", priority=" + priority + ", title=" + title + ", creat_dt=" + creat_dt + ", destruct_dt=" + destruct_dt + ", messages=" + messages + '}';
    }



    
    
}
