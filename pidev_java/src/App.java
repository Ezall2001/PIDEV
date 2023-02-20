
// import entities.Student;
import services.User_service;
import config.Log;
import config.Jdbc_connection;

public class App {
    public static void main(String[] args) {
        Jdbc_connection.getInstance();
        User_service se = new User_service();
        // s1 = new Student("ayouta@faza", "azertouta", "eya", "harbi", 22, "studenta",
        // "PATH OM TASWIRA");
        // Student s2 = new Student("barbouta@faza", "armoun", "behy", "bch nebki", 229,
        // "studenta",
        // "PATH OM TASWIRA");
        // se.add(s2);
        // se.update(s1);
        // se.delete(s2);
        Log.console(se.get_All());
        // Log.console(se.find_by_id(1));
        // Log.console(se.find_by_level("beginner"));

    }
}
