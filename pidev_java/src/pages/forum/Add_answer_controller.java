package pages.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import entities.Answer;
import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import services.Answer_service;
import utils.Router;
import utils.Service_Fx;
import utils.Shared_model;

public class Add_answer_controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO Auto-generated method stub

    }

    @FXML
    private Button btn1;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private TextArea text1;
    Service_Fx sf = new Service_Fx();

    @FXML
    void return_to(ActionEvent event) throws IOException {
        sf.redirect(event, "/pages/forum/Answer_question.fxml");

    }

    @FXML
    void add_rep(ActionEvent event) {
        try {
            Shared_model sharedModel = Shared_model.getInstance();
            Question q = sharedModel.getUser();
            String message = text1.getText();

            if (message == "") {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("Champs vides");
                errorAlert.setContentText("vous devez remplir remplir les champs");
                errorAlert.showAndWait();
            } else {
                Answer p = new Answer(message);

                Answer_service ps = new Answer_service();
                ps.add(p, q);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("votre reponse est ajoutée");
                alert.showAndWait();
                text1.setText("");
                sf.redirect(event, "/pages/forum/Answer_question.fxml");

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
