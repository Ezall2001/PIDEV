package tests;

import entities.Test;
import entities.Test_qs;
import services.Test_qs_service;
import services.Test_service;
import utils.Log;

public class Nour_test {
    public static void main(String[] args) {
        Test test = new Test(227, 10, 21, "cours");
        Test_service service = new Test_service();
        // service.add(test);
        // Log.console(service.get_all2());
        // service.delete(3);
        // Log.console(service.get_by_id(1));
        // service.modify(test);
        // Log.console(service.get_with_questions(1));

        Test t = new Test(10, 2, 222, "cours");
        // service.add(t);
        // service.modify(t);

        Test_qs question = new Test_qs(8, 6, "zz", "SIMBA", "MESHMESH", "TAMA",
                "NONE", "ALL");
        Test_qs_service qs_service = new Test_qs_service();
        // question.set_test(test);
        // qs_service.add(question);
        // qs_service.add(question);
        // qs_service.delete(6);
        // Log.console(qs_service.get_all());
        // qs_service.modify(question);
        // Log.console(qs_service.get_by_id(7));
        //
    }
}
