package pages.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import services.Question_service;
import utils.Log;
import utils.Router;
import utils.Service_Fx;

public class Add_question_controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO Auto-generated method stub

    }

    @FXML
    private Button btn;

    @FXML
    private Label lb1;

    @FXML
    private Label lb2;

    @FXML
    private Label lb3;

    @FXML
    private ImageView qs;

    @FXML
    private TextField tx1;

    @FXML
    private TextArea tx2;

    Service_Fx sf = new Service_Fx();

    @FXML
    void add_question(ActionEvent event) throws IOException {
        try {
            String title = tx1.getText();
            String description = tx2.getText();
            if (title == "" || description == "") {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("Champs vides");
                errorAlert.setContentText("vous devez remplir remplir les champs");
                errorAlert.showAndWait();
            } else if (title.length() > 40) {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("votre question ne doit pas depasser 40 caractéres");
                errorAlert.setContentText("vous devez remplir de noveau");
                errorAlert.showAndWait();
                tx1.setText("");
                tx2.setText("");

            } else {
                Question p = new Question(title, description);
                Question_service ps = new Question_service();
                ps.add(p);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("question ajoutée");
                alert.showAndWait();
                tx1.setText("");
                tx2.setText("");
                Consumer<Forum_controller> consumer = forumontroller -> {

                };

                Router.render_user_template("Forum", consumer);

            }

            //Router.render_user_template("Forum", consumer);

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @FXML
    void return_to(ActionEvent event) throws IOException {
        //sf.redirect(event, "/pages/forum/Forum.fxml");

        Consumer<Forum_controller> consumer = forumontroller -> {

        };

        Router.render_user_template("Forum", consumer);

    }

}
