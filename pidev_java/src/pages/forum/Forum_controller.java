package pages.forum;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import entities.Question;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.Question_service;
import utils.Log;
import utils.Router;
import utils.Service_Fx;
import utils.Shared_model;

public class Forum_controller implements Initializable {

    Service_Fx sf = new Service_Fx();

    Stage main_stage;

    @FXML
    private MenuButton btn2;
    @FXML
    private Button btn1;

    @FXML
    private ListView<Question> list1;

    private List<Question> l2;

    @FXML
    void add_qs(ActionEvent event) throws IOException {
        sf.redirect(event, "/pages/forum/Add_question.fxml");

    }

    @FXML
    void filter_re(ActionEvent event) {
        Question_service qs = new Question_service();
        List<Question> l1 = qs.filter_qs(2);
        sf.show_list(list1, l1);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Question_service qs = new Question_service();
        List<Question> l1 = qs.get_all();
        sf.show_list(list1, l1);
        list1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                try {
                    l2 = list1.getSelectionModel().getSelectedItems();
                    Log.console(l2);
                    Shared_model sharedModel = Shared_model.getInstance();
                    sharedModel.setUser(l2.get(0));

                    sf.redirect(event, "/pages/forum/Answer_question.fxml");

                } catch (Exception e) {

                }
            }
        });
    }

}
