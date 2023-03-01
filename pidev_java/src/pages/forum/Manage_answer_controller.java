package pages.forum;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Answer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Answer_service;
import utils.Log;
import utils.Service_Fx;
import utils.Shared_answer;
import utils.Shared_model;

public class Manage_answer_controller implements Initializable {

    @FXML // fx:id="img1"
    private ImageView img1; // Value injected by FXMLLoader

    @FXML // fx:id="img2"
    private ImageView img2; // Value injected by FXMLLoader

    @FXML // fx:id="lb1"

    private Label lb1; // Value injected by FXMLLoader
    @FXML
    private Button btn1;

    Service_Fx sf = new Service_Fx();

    @FXML
    void delete_answer(MouseEvent event) throws IOException {

        try {
            Shared_answer SharedAnswer = Shared_answer.getInstance();
            Answer q = SharedAnswer.getAnswer();
            Answer_service ps = new Answer_service();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("question supprimée");
            alert.showAndWait();
            ps.delete(q);
            sf.redirect(event, "/pages/forum/Answer_question.fxml");
        } catch (Exception e) {
            Log.console(e.getMessage());
        }

    }

    @FXML
    void update_answer(MouseEvent event) throws IOException {
        Shared_answer SharedAnswer = Shared_answer.getInstance();
        Answer q = SharedAnswer.getAnswer();

        Answer_service rs = new Answer_service();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        // Create the form controls
        Label nameLabel = new Label("Reponse:");
        TextArea nameField = new TextArea();
        nameField.setText(q.getMessage());
        Button submitButton = new Button("modifier");

        // Add the form controls to the VBox
        VBox vbox = new VBox(nameLabel, nameField, submitButton);

        // Add an event handler to the Submit button
        submitButton.setOnAction(e -> {

            String message2 = nameField.getText();
            // Handle the form submission
            try {
                if (message2 == "") {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Champs vides");
                    errorAlert.setContentText("vous devez remplir remplir les champs");
                    errorAlert.showAndWait();
                } else if (sf.is_matching(message2)) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Attention");
                    errorAlert.setContentText("vous n'avez pas le droit d utiliser ce genre des mots");
                    errorAlert.showAndWait();
                }

                else {

                    rs.update(q.getId(), message2);

                    //Log.console(q.get_id());
                    Answer q1 = rs.get_by_id(q.getId()).get(0);

                    lb1.setText(q1.getMessage());

                    stage.close();
                }

            } catch (Exception ex) {
                Log.console(ex.getMessage());
            }
        });

        // Create and show the modal dialog
        Scene scene = new Scene(vbox, 620, 280);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    void return_to(ActionEvent event) throws IOException {

        sf.redirect(event, "/pages/forum/Answer_question.fxml");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Answer_service qs = new Answer_service();
        Shared_answer SharedAnswer = Shared_answer.getInstance();
        Answer q = SharedAnswer.getAnswer();
        lb1.setText(q.getMessage());

    }

}