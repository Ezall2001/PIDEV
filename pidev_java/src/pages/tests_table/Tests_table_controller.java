package pages.tests_table;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import dialogs.test_input.Test_input_controller;
import entities.Test;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import services.Test_service;
import utils.Router;
import utils.Table_view_helpers;

public class Tests_table_controller implements Initializable {

  static private Test_service test_service = new Test_service();

  @FXML
  private VBox table_wrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    TableView<Test> table = new TableView<>();

    TableColumn<Test, String> test_name_column = new TableColumn<>("Nom test");
    test_name_column
        .setCellValueFactory(test -> {
          String name = "";
          Test.Type test_type = test.getValue().get_type();
          if (test_type == Test.Type.COURSE)
            name = test.getValue().get_course().get_name();
          else
            name = test.getValue().get_subject().get_name();

          return new ReadOnlyStringWrapper(name);
        });

    TableColumn<Test, String> type_column = new TableColumn<>("Type");
    type_column.setCellValueFactory(new PropertyValueFactory<Test, String>("_type_string"));

    TableColumn<Test, String> min_points_column = new TableColumn<>("Seuil");
    min_points_column.setCellValueFactory(test -> {
      String min_points = test.getValue().get_min_points().toString();
      min_points = String.format("%s points", min_points);
      return new ReadOnlyStringWrapper(min_points);
    });

    TableColumn<Test, String> duration_column = new TableColumn<>("Durée (min)");
    duration_column.setCellValueFactory(
        test -> {
          String test_duration = String.format("%s min", test.getValue().get_duration());
          return new ReadOnlyStringWrapper(test_duration);
        });

    table.getColumns().add(test_name_column);
    table.getColumns().add(type_column);
    table.getColumns().add(min_points_column);
    table.getColumns().add(duration_column);

    table = Table_view_helpers.add_action_column(table,
        (Test test) -> {
          Router.render_dialog("Test_input", (Test_input_controller controller) -> controller.hydrate(test));
        },
        (Test test) -> {
          if (!Table_view_helpers.delete_confirmation())
            return;

          test_service.delete(test);
          Router.render_admin_template("Tests_table", null);
        });

    List<Test> tests = test_service.get_all();
    table.getItems().addAll(tests);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table = Table_view_helpers.set_style(table);

    table_wrapper.getChildren().add(table);

  }

  @FXML
  void on_add_button_pressed(ActionEvent event) {
    Router.render_dialog("Test_input", (Test_input_controller controller) -> controller.hydrate());
  }

}
