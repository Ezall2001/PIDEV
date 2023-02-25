package pages.forum;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Answer_service;
import utils.Service_Fx;
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
        Shared_model sharedModel = Shared_model.getInstance();
        Answer q = sharedModel.get_answer();
        Answer_service ps = new Answer_service();
        ps.delete(q);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("question supprimée");
        alert.showAndWait();
        sf.redirect(event, "/pages/forum/Answer_question.fxml");

    }

    @FXML
    void update_answer(MouseEvent event) throws IOException {
        Shared_model sharedModel = Shared_model.getInstance();
        Answer q = sharedModel.get_answer();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        // Create the form controls
        Label nameLabel = new Label("Reponse:");
        TextArea nameField = new TextArea();
        nameField.setText(q.get_message());
        Button submitButton = new Button("modifier");

        // Add the form controls to the VBox
        VBox vbox = new VBox(nameLabel, nameField, submitButton);

        // Add an event handler to the Submit button
        submitButton.setOnAction(e -> {
            // Handle the form submission
            Answer_service ps = new Answer_service();

            String message = nameField.getText();
            ps.update(q.get_id(), message);
            Answer q1 = ps.get_by_id(q.get_id()).get(0);

            lb1.setText(q1.get_message());

            stage.close();
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
        Shared_model sharedModel = Shared_model.getInstance();
        Answer q = sharedModel.get_answer();
        lb1.setText(q.get_message());

    }

}