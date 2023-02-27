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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Answer_service;
import services.Question_service;
import utils.Log;
import utils.Router;
import utils.Service_Fx;
import utils.Shared_answer;
import utils.Shared_model;
import javafx.scene.Node;

public class Answer_question_controller implements Initializable {
    Shared_model sharedModel = Shared_model.getInstance();
    Question q = sharedModel.getUser();

    @FXML
    private MenuButton btn1;
    @FXML
    private Button btn5;

    @FXML
    private Button btn2;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private Label lb1;

    @FXML
    private ListView<Answer> list2;
    @FXML
    private TableView<Answer> tab1;

    @FXML
    private TableColumn<Answer, String> cl1;

    @FXML
    private Label list3;

    @FXML
    private Label list4;

    @FXML
    private Label tx3;

    Service_Fx sf = new Service_Fx();

    public void setList2(ListView<Answer> list2) {
        this.list2 = list2;
    }

    @FXML
    void add_rp(ActionEvent event) throws IOException {
        sf.redirect(event, "/pages/forum/add_answer.fxml");
        //Log.console("gi");

    }

    @FXML
    void trier_rp(ActionEvent event) {
        /*  Answer_service qs = new Answer_service();
        Shared_model sharedModel = Shared_model.getInstance();
        Question q = sharedModel.getUser();
        List<Answer> l1 = qs.trie();
        list2.setItems(FXCollections.observableArrayList(l1));
        list2.setCellFactory(param -> new ListCell<Answer>() {
            @Override
            protected void updateItem(Answer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.get_message());
                }
            }
        });*/

    }

    @FXML
    private void handleImageClicked() {
        // Handle the image click event
    }

    @FXML
    void delete_qs(MouseEvent event) throws IOException {
        Shared_model sharedModel = Shared_model.getInstance();
        Question q = sharedModel.getUser();
        Question_service ps = new Question_service();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("question supprimée");
        alert.showAndWait();
        ps.delete(q);
        sf.redirect(event, "/pages/forum/Forum.fxml");

    }

    @FXML
    void update_qs(MouseEvent event) throws IOException {
        Shared_model sharedModel = Shared_model.getInstance();
        Question q = sharedModel.getUser();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        // Create the form controls
        Label nameLabel = new Label("title:");
        TextField nameField = new TextField();
        nameField.setText(q.get_title());
        Label nameLabel2 = new Label("description:");
        TextArea nameField2 = new TextArea();
        nameField2.setText(q.get_description());
        Button submitButton = new Button("modifier");

        // Add the form controls to the VBox
        VBox vbox = new VBox(nameLabel, nameField, nameLabel2, nameField2, submitButton);

        // Add an event handler to the Submit button
        submitButton.setOnAction(e -> {
            // Handle the form submission
            Question_service ps = new Question_service();

            String title = nameField.getText();
            String description = nameField2.getText();
            try {
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
                } else if (ps.is_matching(title) || ps.is_matching(description)) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Attention");
                    errorAlert.setContentText("vous n'avez pas le droit d utiliser ce genre des mots");
                    errorAlert.showAndWait();
                }

                else {
                    ps.update(q.get_id(), title, description);
                    Question q1 = ps.get_by_id(q.get_id()).get(0);

                    list3.setText(q1.get_title());
                    list4.setText(q1.get_description());

                    stage.close();
                }
            } catch (Exception ev) {
                // TODO: handle exception
            }
        });

        // Create and show the modal dialog
        Scene scene = new Scene(vbox, 620, 280);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    void return_to(ActionEvent event) throws IOException {
        Consumer<Forum_controller> consumer = forumontroller -> {

        };

        Router.render_user_template("Forum", consumer);

    }

    public void get_question() {

    }

    List<Answer> value;
    int number_answer = 0;
    String number_answer_string = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Question_service qs = new Question_service();
            Shared_model sharedModel = Shared_model.getInstance();
            Question q = sharedModel.getUser();
            list3.setText(q.get_title());
            list4.setText(q.get_description());

            HashMap<Question, List<Answer>> map = qs.get_with_answer(q.get_id());

            for (HashMap.Entry<Question, List<Answer>> entry : map.entrySet()) {

                value = entry.getValue();
                //Log.console(value.get(0));
                if (value.get(0).getMessage() == null) {
                    number_answer = 0;
                } else {
                    number_answer = entry.getValue().size();
                }
                number_answer_string = Integer.toString(number_answer);

                //Log.console(value);

            }
            sf.show_tab(tab1, value);

            // // get list
            // list2.setItems(FXCollections.observableArrayList(value));
            // tx3.setText(number_answer_string);
            // list2.setCellFactory(param -> new ListCell<Answer>() {
            //     @Override
            //     protected void updateItem(Answer item, boolean empty) {
            //         super.updateItem(item, empty);
            //         if (empty || item == null) {
            //             setText(null);
            //         } else {
            //             setText(item.get_message());

            //         }
            //     }

            // });
            tab1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                List<Answer> l2 = new ArrayList<>();

                @Override
                public void handle(MouseEvent event) {

                    try {
                        l2 = tab1.getSelectionModel().getSelectedItems();

                        Shared_answer SharedAnswer = Shared_answer.getInstance();
                        SharedAnswer.setAnswer(l2.get(0));
                        Log.console("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
                        Log.console(l2);

                        sf.redirect(event, "/pages/forum/Manage_answer.fxml");

                        //Log.console(sharedModel.getUser());

                    } catch (Exception e) {

                    }
                }
            });

        } catch (Exception e) {
            Log.console(e);

        }

    }

}
