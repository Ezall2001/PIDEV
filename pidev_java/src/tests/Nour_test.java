package tests;

import java.io.IOException;
import java.util.List;

import entities.Test;
import entities.Test_qs;
import entities.Test_result;
import entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.Test_qs_service;
import services.Test_result_service;
import services.Test_service;
import services.User_service;
import services.User_session_service;
import utils.Log;
import utils.Router;

public class Nour_test extends Application {

    private static User_session_service user_session_service = new User_session_service();

    public static void main(String[] args) {
        // User_service user_service = new User_service();
        // Test_result res1 = new Test_result();
        // res1.set_mark(10);
        // List<User> list = user_service.get_all();
        // User u = list.get(0);
        // res1.set_user(u);

        // user_service.update_score(res1);
        // User result_user = user_service.find_by_id(u);
        // Log.console(result_user.get_score());

        // this is  a test comment xxxxxxxxx

        launch(args);

    }

    @Override
    public void start(Stage main_stage) throws IOException {
        User user = user_session_service.get_user();
        Router.init(main_stage);

        if (user == null)
            Router.render_admin_template("Login", null);

        else if (user.get_type().equals(User.Type.ADMIN))
            Router.render_admin_template("Dashboard", null);

        else
            Router.render_admin_template("Profile", null);

    }

}
