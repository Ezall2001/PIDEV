package tests;

import java.util.List;

import entities.Answer;
import entities.Question;
import services.Answer_service;
import services.Question_service;
import utils.Jdbc_connection;
import utils.Log;

public class Najiba_test {
    public static void main(String[] args) {
        Jdbc_connection.getInstance();
        /*
         * ***************************************************najiba's
         * test*************************************************************************
         * ***
         */
        Question q1 = new Question("najibaservice", "azertyuiopp");
        Answer r1 = new Answer(10, "message2");
        Question_service q = new Question_service();
        Answer_service rs = new Answer_service();

        /*------------------------add functions----------------------------- */

        q.add(q1);
        //rs.add(r1, q1);

        /*------------------------get All functions----------------------------- */

        // Log.console(q.get_All());
        //Log.console(rs.get_All());
        //Log.console(q.get_with_answer(4));

        /*------------------------get by id functions----------------------------- */

        // Log.console(qs.get_by_id(1));
        //Log.console(rs.get_by_id(2));

        /*------------------------delete functions----------------------------- */

        //q.delete(q1);
        // rs.delete(r1);

        /*------------------------update functions----------------------------- */

        //q.update(1, "najiba123", "ccccccccc");

        //rs.update(2, "najiba123");

        /*------------------------filter functions----------------------------- */
        //Log.console(q.filter_qs(2));
        /*------------------------trie----------------------------- */
        // Log.console(rs.trie());

    }
}
