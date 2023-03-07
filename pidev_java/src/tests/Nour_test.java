package tests;

import java.util.List;

import entities.Test;
import entities.Test_qs;
import entities.Test_result;
import entities.User;
import services.Test_qs_service;
import services.Test_result_service;
import services.Test_service;
import services.User_service;
import utils.Log;

public class Nour_test {
    public static void main(String[] args) {
        User_service user_service = new User_service();
        Test_result res1 = new Test_result();
        res1.set_mark(10);
        List<User> list = user_service.get_all();
        User u = list.get(0);
        res1.set_user(u);

        user_service.update_score(res1);
        User result_user = user_service.find_by_id(u);
        Log.console(result_user.get_score());

        // this is  a test comment xxxxxxxxx

    }
}
