
import entities.User;
import javafx.application.Application;
import javafx.stage.Stage;
import services.User_session_service;
import utils.Router;

public class App extends Application {

    private static User_session_service user_session_service = new User_session_service();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage main_stage) {
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