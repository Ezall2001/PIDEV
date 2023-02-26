package entities;

import java.sql.SQLException;

import services.User_service;
import services.User_session_service;

//TODO: ****things i changed
public class Current_user_data {
    private static User current_user = new User();
    private static User_session current_session = new User_session();

    public static boolean is_logged_in() {
        // check if there's a current user and a non-expired session for that user
        if (current_user != null && current_session != null && !current_session.is_expired() &&
                User_service.get_user_service_instance().logged_in(current_user)) {
            return true;
        }
        // otherwise, clear the current user and session and return false
        current_user = null;
        current_session = null;
        return false;
    }

    public static User get_current_user() {
        return current_user;
    }

    public static void set_current_user(User user) {
        Current_user_data.current_user = user;
    }

    public static User_session get_current_session() {
        return current_session;
    }

    public static void set_current_session(User_session session) {
        Current_user_data.current_session = session;
    }

    public static void update_current_user(User user) throws SQLException {
        current_user = user;
        current_session = User_session_service.get_user_session(user);
    }

}
