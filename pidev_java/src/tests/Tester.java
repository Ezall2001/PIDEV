package tests;

import config.Log;
import entities.User;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import config.Jdbc_connection;
import services.User_service;
import services.User_session_service;
import entities.User;
import config.Jdbc_connection;

public class Tester {
  public static void main(String[] args) {
    Jdbc_connection.getInstance();
    File picture_path1 = new File("C:/Users/MSI/Pictures/WallPaper/picture.jpg");
    User_service user_service = new User_service();
    User u1 = new User("eya", "harbi", 21, "many many words here", "eya@fza.tn", "azerty", picture_path1);
    User u2 = new User("armen", "bakir", 22, "LOVELY KHARYA", "armen@eya.tn", "azertouta", picture_path1);
    User u3 = new User("najiba", "rym", 22, "NOUR", "ourTeam@eya.tn", "somepassword", picture_path1);
    User_session_service user_session_service = new User_session_service();
    /**
     * *USER_SERVICE
     */
    /*  
    * * add student
    */
    // user_service.add(u1);
    // user_service.add(u2);
    // user_service.add(u3);
    /*  
    * update student
    */
    // user_service.update(u2);
    /*  
    * delete student
    */
    // user_service.delete(u1);
    /*  
    * advanced methods
    */
    // Log.console(user_service.get_All());
    // Log.console(user_service.find_by_id(3));
    // Log.console(user_service.find_by_level("newcommer"));

    // List<User> users = user_service.sort();

    /**
     * *USER_SESSION_SERVICE
     */
    /*  
    * * create session
    */
    user_session_service.create_session(u1, 100);
    /*  
    * * delete session
    */
    // user_session_service.delete_session(
    // "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcm1lbkBleWEudG4ifQ.o6oDigD7eJxNLbgIFmOmFM46NG1Lgrt0NZWZIhvMc2I");
    /*  
    * * check if session exists and not expired
    */
    // user_session_service.get_session(
    //     "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdXJUZWFtQGV5YS50biJ9.XKkugbd2BKFt8PtazNygIFE-2h9VEYOjrQuSbl6OR9g");
    /*  
    * * delete expired sessions
    */
    // user_session_service.delete_expired_sessions();
    // Log.console(user_session_service.get_All());
  }
}
