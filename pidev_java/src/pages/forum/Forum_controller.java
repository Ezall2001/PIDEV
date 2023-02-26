package pages.forum;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import entities.Question;
import entities.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Question_service;
import services.Subject_service;
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

    Scene scene;

    @FXML
    void onscelect(ActionEvent event) {
        Stage stage = new Stage();
        Subject_service subject = new Subject_service();
        List<Subject> sub = subject.get_all();
        ObservableList<Subject> subjects = FXCollections.observableArrayList();
        subjects.addAll(sub);
        ListView<Subject> listView = new ListView<>(subjects);
        listView.setCellFactory(param -> new ListCell<Subject>() {
            @Override
            protected void updateItem(Subject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.get_subject_name());
                    //setText(item.get_by_id(s.get_id()));
                }
            }

        });

        ;
        scene = new Scene(listView);
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        Label nameLabel = new Label("matiéres:");
        Button selectButton = new Button("Selecter");
        selectButton.setOnAction(e -> {
            Subject selectedSubject = listView.getSelectionModel().getSelectedItem();
            Log.console(selectedSubject);
            List<Question> question;
            Question_service qs = new Question_service();

            question = qs.get_subject(selectedSubject);
            sf.show_list(list1, question);
            Log.console(question);

            // Faites quelque chose avec la matière sélectionnée, par exemple, affichez-la dans une fenêtre ou effectuez toute autre action requise
            modalStage.close();
        });
        VBox vBox = new VBox(listView, nameLabel, selectButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        // Créez une nouvelle scène contenant la VBox
        scene = new Scene(vBox, 300, 600);

        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    };

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
