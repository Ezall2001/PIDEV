package pages.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import entities.Question;
import entities.Subject;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import services.Question_service;
import services.Subject_service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Log;
import utils.Router;
import utils.Service_Fx;

public class Add_question_controller implements Initializable {

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
    @FXML
    private ChoiceBox<String> myChoiceBox;
    ObservableList<String> observableList;
    List<Subject> s2;
    String subject;

    Service_Fx sf = new Service_Fx();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Subject_service s = new Subject_service();
        List<Subject> subjects = s.get_all();
        List<Subject> s2;

        //Log.console(subjects);

        for (Subject m : subjects) {
            s2 = s.get_by_id(m.get_id());
            //Log.console(s2);
            myChoiceBox.getItems().add(s2.get(0).get_subject_name());
            observableList = FXCollections.observableArrayList(myChoiceBox.getItems());
            myChoiceBox.setOnAction(event -> {
                subject = myChoiceBox.getSelectionModel().getSelectedItem();

                Log.console("subject sélectionnée : " + subject);
            });
            //myChoiceBox.getItems().get(s2.get(0).get_id());
            //String ss= myChoiceBox.getItems().
            //Log.console(myChoiceBox.getItems());
        }
        myChoiceBox.setItems(FXCollections.observableArrayList(myChoiceBox.getItems()));

    }

    @FXML
    void add_question(ActionEvent event) throws IOException {
        Question_service qs = new Question_service();
        Answer_question_controller as = Answer_question_controller.getInstance();

        User user = as.get_user();
        try {
            String title = tx1.getText();
            String description = tx2.getText();
            String choiceBox = subject;
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

            } else if (sf.is_matching(title) || sf.is_matching(description)) {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("Attention");
                errorAlert.setContentText("vous n'avez pas le droit d utiliser ce genre des mots");
                errorAlert.showAndWait();
                tx1.setText("");
                tx2.setText("");

            } else {
                Question p = new Question(title, description);
                List<Subject> s = new ArrayList<>();
                Subject_service sb = new Subject_service();
                s = sb.get_by_name(choiceBox);
                Question_service ps = new Question_service();
                ps.add(p, s.get(0), user);
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

    // @FXML
    // public void handleSelection(ActionEvent event) throws IOException {
    //     String selectedElement = myChoiceBox.getValue();
    //     Log.console(selectedElement);
    // }

    @FXML
    void return_to(ActionEvent event) throws IOException {
        //sf.redirect(event, "/pages/forum/Forum.fxml");
        sf.redirect(event, "/pages/forum/Forum.fxml");

        // Consumer<Forum_controller> consumer = forumontroller -> {

        // };

        // Router.render_user_template("Forum", consumer);

    }

}
