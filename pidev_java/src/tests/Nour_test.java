package tests;

import entities.Test;
import entities.Test_qs;
import services.Test_qs_service;
import services.Test_service;
import config.Log;

public class Nour_test {
    public static void main(String[] args) {
        Test test = new Test(2, 0, 0, "cours");
        Test_service service = new Test_service();
        // service.add(test);
        // Log.console(service.get_all());
        // service.delete(test, 2);
        // Log.console(service.get_by_id(1));
        // service.modify(test, 2, "matiere", 333, 99999);
        // Log.console(service.get_with_questions(1));

        Test_qs question = new Test_qs(8, 6, "SIMBA", "SIMBA", "MESHMESH", "TAMA",
                "NONE", "ALL");
        // question.set_test(test);
        Test_qs_service qs_service = new Test_qs_service();
        // qs_service.add(question);
        // qs_service.delete(question, 9);
        // Log.console(qs_service.get_all());
        // qs_service.modify(question, 0, 0, "null", "null", "null", "null", "null",
        // "null");
        // Log.console(qs_service.get_by_id(7));
    }
}
