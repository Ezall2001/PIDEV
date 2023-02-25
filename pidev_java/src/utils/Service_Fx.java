package utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import entities.Question;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Service_Fx {
    private Stage stage;
    private Parent root;
    private Scene scene;

    public void redirect(Event event, String chemain) throws IOException {

        root = FXMLLoader.load(getClass().getResource(chemain));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        // resize window
        stage.setResizable(true);
        // zoom window
        stage.setWidth(1460);
        stage.setHeight(930);

        stage.show();

    }

    public void show_list(ListView<Question> list1, List<Question> l1) {
        list1.setItems(FXCollections.observableArrayList(l1));
        list1.setCellFactory(param -> new ListCell<Question>() {
            @Override
            protected void updateItem(Question item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.get_title());
                }
            }
        });

    }

}