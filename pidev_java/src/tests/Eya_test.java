package tests;

import utils.Log;
import entities.User;
import entities.User.Level;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.lang.model.util.ElementScanner6;

import utils.Jdbc_connection;
import services.User_service;
import services.User_session_service;
import entities.User;
import utils.Jdbc_connection;

public class Eya_test {
    public static void main(String[] args) {
        Jdbc_connection.getInstance();
        // File picture_path1 = new File("C:/Users/MSI/Pictures/WallPaper/picture.jpg");
        User_service user_service = new User_service();
        User u1 = new User("eya", "harbi", 21, "many many words here", "eya@esprit.tn", "azerty");
        User u2 = new User("armen", "bakir", 22, "LOVELY KHARYA", "armen@esprit.tn", "azertouta");
        User u5 = new User("armoun", "bakour", 22, "LOVELY bhima", "armoun@esprit.tn", "azertouta");
        User u3 = new User("najiba", "rym", 22, "NOURrrrrrrr", "ourTeam@eya.tn", "somepassword");
        User u4 = new User("k;", "lmlm", 22, "heyarrr", "ourTeam@eya.tn", "someblapassword");
        User_session_service user_session_service = new User_session_service();
        /**
         * *USER_SERVICE
         */
        // if (((user_service.find_by_email(u2.get_email()) == null)) && (user_service.check_user_infos(u2)))
        //     System.out.println(" mijoud");
        // else
        //     System.out.println(" mch mijoud");
        /*  
        * * add student
        */
        user_service.add(u5);
        // user_service.delete(u2);
        // user_service.add(u2);
        // Log.console(user_service.add(u1));
        // user_service.add(u4);
        // Log.console(u2);
        /*  
        * update student
        */
        // user_service.update(u2);
        /*  
        * delete student
        */

        // user_service.login("eya@esprit.tn", "azerty");
        // Log.console(u1.get_level_string());
        /*  
        
        
        * advanced methods
        */
        Log.console(user_service.get_all());
        // Log.console(user_service.find_by_id(19));
        // if (user_service.check_password("azerty", u1.get_hashed_password())) {
        //     Log.console("nafssou wallah");
        // } else
        //     Log.console("nice try ");
        /**
         * *USER_SESSION_SERVICE
         */
        /*  
        * * create session
        */

        // user_session_service.create_session(u1);
        // user_session_service.create_session(u2);
        // user_session_service.create_session(u3);
        /*  
        * * delete session
        */
        // user_session_service.delete_session_by_email(u2.get_email());

        // Log.console(user_service.login("armen@esprit.tn", "azertouta"));
        // user_service.logout(u2);
        // if (user_service.logged_in(u2))
        //     Log.console("loged in");
        // else
        //     Log.console("Logged out");
        /*  
        
        * * check if session exists and not expired
        */
        // Log.console(user_session_service.get_all());
        /*  
        * * delete expired sessions
        */
        // user_session_service.delete_expired_sessions();
        // Log.console(user_session_service.get_all());
    }

    // private static Level valueOf(String string) {
    //     return null;
    // }
}