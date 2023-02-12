package tests;

import config.Jdbc_connection;
import config.Log;
import services.AnswerService;
import services.QuestionService;

public class Tester {
  public static void main(String[] args) {
    Jdbc_connection.getInstance();
    /* ***************************************************najiba's test**************************************************************************** */
    QuestionService qs = new QuestionService();
    AnswerService rs = new AnswerService();
    /*------------------------add functions----------------------------- */

    //qs.add();
    // rs.add_by_id(2);

    /*------------------------get All functions----------------------------- */

    Log.console(qs.get_All());
    //Log.console(rs.get_All());
    //Log.console(qs.get_with_answer(1));

    /*------------------------get by id functions----------------------------- */

    //Log.console(qs.get_by_id(1));
    //Log.console(rs.get_by_id(2));

    /*------------------------delete functions----------------------------- */

    //qs.delete(2);
    //rs.delete(3);

    /*------------------------update functions----------------------------- */

    //qs.update(1, "najiba12", "ccccccccc");

    //rs.update(2, "najiba123");

    /*------------------------add functions----------------------------- */

  }
}
