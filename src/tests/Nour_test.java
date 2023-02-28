package tests;

import entities.Course;
import entities.Subject;
import entities.Test;
import entities.Test_qs;
import entities.Course.Difficulty;
import entities.Subject.Level;
import services.Course_Service;
import services.Subject_Service;
import services.Test_qs_service;
import services.Test_service;
import utils.Log;

public class Nour_test {
    public static void main(String[] args) {

        ///////
        Test test = new Test(227, 10, 21, "cours");
        Test_service service = new Test_service();
        // service.add(test);
        // Log.console(service.get_all2());
        // service.delete(3);
        // Log.console(service.get_by_id(1));
        // service.modify(test);
        // Log.console(service.get_with_questions(1));
        // System.out.println(s.get_id());

        // System.out.println(service.get_with_questions_list(2));

        Test t = new Test(230, 22222, 222, "cours");

        // ! add virgin test
        // service.add(t);

        // !! add subject test
        Subject x = new Subject(101, "zzzzz", "zzzzz", Level.CLASS_A);
        Subject_Service sub_service = new Subject_Service();
        // sub_service.add_subject(x);
        // System.out.println(x.get_id());
        // t.setSubject(x);
        // service.add_with_subject(t);
        // System.out.println(sub_service.get_subject_test(101));
        // !! add subject test
        Course y = new Course(79, "espace", "desccc", Difficulty.hard);
        Course_Service course_service = new Course_Service();
        // y.setSubject(x);
        // System.out.println(y.getSubject().get_id());
        // course_service.add_course(y);
        // t.setCourse(y);
        // service.add_with_course(t);
        // service.modify(t);
        //-----------------------------------------------------
        // System.out.println(course_service.get_course_test(79));
        // System.out.println(course_service.get_by_id_2(79));
        // System.out.println(course_service.get_course_test_questions(79));
        // System.out.println(course_service.count_course_questions(79));

        Test_qs question = new Test_qs(8, 6, "zz", "SIMBA", "MESHMESH", "TAMA",
                "NONE", "ALL");
        Test_qs_service qs_service = new Test_qs_service();
        // System.out.println(qs_service.sort_qs_by_number());
        // question.set_test(test);
        // qs_service.add(question);
        // qs_service.add(question);
        // qs_service.delete(6);
        // Log.console(qs_service.get_all());
        // qs_service.modify(question);
        // Log.console(qs_service.get_by_id(7));
        //

        // System.out.println(sub_service.get_subject_test_questions(101));
        // System.out.println(sub_service.count_subject_questions(101));

    }
}
