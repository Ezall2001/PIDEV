package tests;

import java.util.List;

import entities.Answer;
import entities.Question;
import entities.Subject;
import services.Answer_service;
import services.Question_service;
import services.Subject_service;
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
        Question q1 = new Question("ygcdx", "najibaa");
        Answer r1 = new Answer(10, "message2");
        Question_service q = new Question_service();
        Answer_service rs = new Answer_service();
        Subject s1 = new Subject();
        Subject_service sc = new Subject_service();
        Subject ss = new Subject(102, "subject2", "des2");

        /*------------------------add functions----------------------------- */

        //q.add(q1, ss);

        //rs.add(r1, q1);

        /*------------------------get All functions----------------------------- */

        // Log.console(q.get_All());
        //Log.console(rs.get_All());
        //Log.console(q.get_with_answer(4));
        //Log.console(q.get_with_subject(41));
        //Log.console(q.get_with_uesr(41));
        // Log.console(sc.get_by_name("subject1"));
        Log.console(q.get_subject(ss));

        /*------------------------get by id functions----------------------------- */

        // Log.console(qs.get_by_id(1));
        // Log.console(rs.get_by_id(2));

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