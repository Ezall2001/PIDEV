
import javafx.application.Application;
import javafx.stage.Stage;
import utils.Router;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage main_stage) {
        Router.init(main_stage);
        Router.render_user_template("Profile", null);
    }

}