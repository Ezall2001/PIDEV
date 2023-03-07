package tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.ListView;

import entities.Answer;
import entities.Question;
import entities.Subject;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.Answer_service;
import services.Vote_service;
import utils.Log;

public class Najiba_test {
    private Stage stage;
    private Parent root;
    private Scene scene;
    Answer_service s = new Answer_service();
    Answer a = new Answer();

    public void redirect(Event event, String chemain) throws IOException {
        Log.console("najiba");

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

    // public void show_list(ListView<Question> list1, List<Question> l1) {
    //   list1.setItems(FXCollections.observableArrayList(l1));
    //   Subject s = new Subject();
    //   list1.setCellFactory(param -> new ListCell<Question>() {
    //     @Override
    //     protected void updateItem(Question item, boolean empty) {
    //       super.updateItem(item, empty);
    //       if (empty || item == null) {
    //         setText(null);
    //       } else {
    //         setText(item.get_title());
    //         //setText(item.get_by_id(s.get_id()));
    //       }
    //     }

    //   });

    // }

    /* public void show_tab(TableView<Answer> list1, List<Answer> l1) {
      ObservableList<Answer> data = FXCollections.observableArrayList(l1);
      list1.setItems(data);
    
      TableColumn<Answer, String> titleColumn = new TableColumn<>("Réponse");
      titleColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
      titleColumn.setPrefWidth(1245);
    
      list1.getColumns().add(titleColumn);
    
      list1.setItems(data);
      TableColumn<Answer, Button> titleColumn1 = new TableColumn<>("Vote");
      titleColumn1.setCellFactory(col -> {
          // Create a Button Cell
          TableCell<Answer, Button> cell = new TableCell<>() {
    
              // Override updateItem to set the Button
              @Override
              public void updateItem(Button item, boolean empty) {
    
                  super.updateItem(item, empty);
                  if (empty) {
                      setGraphic(null);
                  } else {
    
                      Button doubleSidedButton = new Button();
                      doubleSidedButton.setStyle("-fx-background-color: transparent;");
                      Button leftButton = new Button("+");
    
                      Label nb_vote = new Label();
                      //l1.get(0).get_vote_nb()
                      nb_vote.setPadding(new Insets(30));
                      Button rightButton = new Button("-");
    
                      GridPane gridPane = new GridPane();
                      gridPane.addRow(0, leftButton, nb_vote, rightButton);
                      doubleSidedButton.setGraphic(gridPane);
    
                      leftButton.setOnAction(event -> {
    
                          Answer rowDataa = getTableView().getItems().get(getIndex());
                          s.incri(rowDataa);
                          System.out.println("*********************");
                          System.out.println(rowDataa);
    
                      });
                      rightButton.setOnAction(event -> {
                          // effectue l'action pour le bouton de droite
                          Answer rowDataa = getTableView().getItems().get(getIndex());
                          s.déc(rowDataa);
                          //System.out.println(rowDataa.get_vote_nb());
    
                      });
                      //ToggleButton button = new ToggleButton("Click Me");
    
                      setGraphic(doubleSidedButton);
    
                      doubleSidedButton.setOnAction((ActionEvent event) -> {
                          // Do something when button is clicked
                          Answer rowData = getTableView().getItems().get(getIndex());
                          nb_vote.setText(Integer.toString(rowData.get_vote_nb()));
    
                      });
    
                  }
              }
          };
    
          return cell;
      });
      list1.getColumns().add(titleColumn1);
    
    } */
    // public void show_tab(ListView<Answer> list1, List<Answer> l1) {
    //   ObservableList<Answer> data = FXCollections.observableArrayList(l1);
    //   list1.setItems(data);

    //   list1.setCellFactory(lv -> new ListCell<>() {
    //     @Override
    //     public void updateItem(Answer answer, boolean empty) {
    //       super.updateItem(answer, empty);
    //       if (empty) {
    //         setText(null);
    //         setGraphic(null);
    //       } else {
    //         setText(answer.getMessage());
    //         Button doubleSidedButton = new Button();
    //         doubleSidedButton.setStyle("-fx-background-color: transparent;");
    //         Button leftButton = new Button("+");

    //         Label nb_vote = new Label(Integer.toString(data.get(getIndex()).get_vote_nb()));
    //         nb_vote.setPadding(new Insets(30));
    //         System.out.println("*********************");
    //         System.out.println(data);
    //         Button rightButton = new Button("-");

    //         GridPane gridPane = new GridPane();
    //         gridPane.addRow(0, leftButton, nb_vote, rightButton);
    //         doubleSidedButton.setGraphic(gridPane);

    //         leftButton.setOnAction(event -> {
    //           Answer rowDataa = answer;
    //           Answer_question_controller as = Answer_question_controller.getInstance();
    //           User user = as.get_user();
    //           Vote_service vs = new Vote_service();
    //           vs.incri(rowDataa, user, 1);

    //           System.out.println(rowDataa);
    //         });
    //         rightButton.setOnAction(event -> {
    //           Answer rowDataa = answer;
    //           Vote_service vs = new Vote_service();
    //           // vs.add_vote2(rowDataa, rowDataa.get_user(), 0);
    //         });

    //         setGraphic(doubleSidedButton);

    //         doubleSidedButton.setOnAction((ActionEvent event) -> {
    //           nb_vote.setText(Integer.toString(answer.get_vote_nb()));
    //         });
    //       }
    //     }
    //   });
    // }

}