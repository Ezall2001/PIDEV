package tests;

import utils.Jdbc_connection;

import java.util.List;
import utils.Log;
import entities.Test;
import entities.Test_result;
import entities.User;
import services.Test_result_service;
import services.Test_service;
import services.User_service;

public class Eya_test {
    public static void main(String[] args) {
        //method tasnae test result add / 
        // test result id user w course 
        User_service user_service = new User_service();
        Test_service test_service = new Test_service();
        Test_result_service test_result_service = new Test_result_service();
        List<User> user_list = user_service.get_all();
        List<Test> test_list = test_service.get_all();

        // Log.file(test);
        // Log.file(user_list);

        User user_1 = user_list.get(0);
        User user_2 = user_list.get(1);
        Test test_1 = test_list.get(0);
        Test test_2 = test_list.get(1);

        Test_result test_result_1 = new Test_result(20, user_1, test_1);
        Test_result test_result_2 = new Test_result(10, user_2, test_2);
        //   ADD METHOD TEST RESULT TEST
        Log.file(test_result_service.add(test_result_1));
        Log.file(test_result_service.add(test_result_2));

        Test_result test_result_3 = new Test_result(15, user_1, test_1);
        Test_result test_result_4 = new Test_result(16, user_2, test_2);
        // TEST UPDATE TEST_RESULT
        Log.file(test_result_service.add(test_result_3).get_mark());
        Log.file(test_result_service.add(test_result_4).get_mark());

    }

}