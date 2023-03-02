package pages.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import entities.Answer;
import entities.Question;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import services.Answer_service;
import services.Question_service;
import utils.Api_mail;
import utils.Log;
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
    Api_mail api = new Api_mail();

    @FXML
    void return_to(ActionEvent event) throws IOException {
        sf.redirect(event, "/pages/forum/Answer_question.fxml");

    }

    @FXML
    void add_rep(ActionEvent event) {
        try {
            Shared_model sharedModel = Shared_model.getInstance();
            Question q = sharedModel.getUser();
            Question_service qs = new Question_service();
            Answer_question_controller as = Answer_question_controller.getInstance();
            User user = as.get_user();

            List<User> value = new ArrayList<>();

            String message = text1.getText();

            if (message == "") {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("Champs vides");
                errorAlert.setContentText("vous devez remplir remplir les champs");
                errorAlert.showAndWait();
            } else if (sf.is_matching(message)) {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("Attention");
                errorAlert.setContentText("vous n'avez pas le droit d utiliser ce genre des mots");
                errorAlert.showAndWait();
                text1.setText("");

            } else {
                Answer p = new Answer(message);

                Answer_service ps = new Answer_service();
                ps.add(p, q, user);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("votre reponse est ajoutée");
                alert.showAndWait();
                text1.setText("");
                HashMap<Question, List<User>> map = qs.get_user(q);
                Log.console("/////////" + map);
                for (HashMap.Entry<Question, List<User>> entry : map.entrySet()) {

                    value = entry.getValue();

                }
                Log.console("######" + user.get_email());
                String mail_em = user.get_email();
                String mdps = "14330531";
                Log.console(value.get(0));
                String mail_dest = value.get(0).get_email();
                Log.console("email*********" + mail_dest);
                Log.console(mail_dest);
                sf.redirect(event, "/pages/forum/Answer_question.fxml");
                api.mail(mail_em, mdps, mail_dest);

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
