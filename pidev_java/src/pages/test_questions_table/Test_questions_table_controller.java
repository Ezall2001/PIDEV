package pages.test_questions_table;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dialogs.course_input.Course_input_controller;
import dialogs.test_question_input.Test_question_input_controller;
import entities.Test;
import entities.Test_qs;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import services.Test_qs_service;
import utils.Log;
import utils.Router;
import utils.Table_view_helpers;

public class Test_questions_table_controller implements Initializable {

  static private Test_qs_service test_qs_service = new Test_qs_service();

  @FXML
  private VBox table_wrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    TableView<Test_qs> table = new TableView<>();

    TableColumn<Test_qs, String> test_name_column = new TableColumn<>("Test");
    test_name_column
        .setCellValueFactory(test_qs -> {
          String test_name = "";
          Test.Type test_type = test_qs.getValue().get_test().get_type();

          if (test_type == Test.Type.COURSE)
            test_name = test_qs.getValue().get_test().get_course().get_name();
          else
            test_name = test_qs.getValue().get_test().get_subject().get_name();

          return new ReadOnlyStringWrapper(test_name);
        });
    test_name_column = Table_view_helpers.set_text_wrap_cell(test_name_column);

    TableColumn<Test_qs, Integer> question_number_column = new TableColumn<>("Numéro");
    question_number_column.setCellValueFactory(new PropertyValueFactory<Test_qs, Integer>("_question_number"));

    TableColumn<Test_qs, String> question_column = new TableColumn<>("Question");
    question_column.setCellValueFactory(new PropertyValueFactory<Test_qs, String>("_question"));
    question_column = Table_view_helpers.set_text_wrap_cell(question_column);
    question_column.setMinWidth(150);

    TableColumn<Test_qs, String> correct_option_column = new TableColumn<>("Option Correct");
    correct_option_column.setCellValueFactory(new PropertyValueFactory<Test_qs, String>("_correct_option"));
    correct_option_column = Table_view_helpers.set_text_wrap_cell(correct_option_column);
    correct_option_column.setMinWidth(150);

    TableColumn<Test_qs, String> optionA_column = new TableColumn<>("Option A");
    optionA_column.setCellValueFactory(new PropertyValueFactory<Test_qs, String>("_optionA"));
    optionA_column = Table_view_helpers.set_text_wrap_cell(optionA_column);
    optionA_column.setMinWidth(150);

    TableColumn<Test_qs, String> optionB_column = new TableColumn<>("Option B");
    optionB_column.setCellValueFactory(new PropertyValueFactory<Test_qs, String>("_optionB"));
    optionB_column = Table_view_helpers.set_text_wrap_cell(optionB_column);
    optionB_column.setMinWidth(150);

    TableColumn<Test_qs, String> optionC_column = new TableColumn<>("Option C");
    optionC_column.setCellValueFactory(new PropertyValueFactory<Test_qs, String>("_optionC"));
    optionC_column = Table_view_helpers.set_text_wrap_cell(optionC_column);
    optionC_column.setMinWidth(150);

    TableColumn<Test_qs, String> optionD_column = new TableColumn<>("Option D");
    optionD_column.setCellValueFactory(new PropertyValueFactory<Test_qs, String>("_optionD"));
    optionD_column = Table_view_helpers.set_text_wrap_cell(optionD_column);
    optionD_column.setMinWidth(150);

    table.getColumns().add(test_name_column);
    table.getColumns().add(question_number_column);
    table.getColumns().add(question_column);
    table.getColumns().add(correct_option_column);
    table.getColumns().add(optionA_column);
    table.getColumns().add(optionB_column);
    table.getColumns().add(optionC_column);
    table.getColumns().add(optionD_column);

    table = Table_view_helpers.add_action_column(table,
        (Test_qs test_qs) -> {
          // Router.render_dialog("Test_question_input",
          //     (Test_question_input_controller controller) -> controller.hydrate(test_qs));
        },
        (Test_qs test_qs) -> {
          if (!Table_view_helpers.delete_confirmation())
            return;

          test_qs_service.delete(test_qs);
          Router.render_admin_template("Test_questions_table", null);
        });

    List<Test_qs> test_qs = test_qs_service.get_all();
    table.getItems().addAll(test_qs);

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table = Table_view_helpers.set_style(table);

    table_wrapper.getChildren().add(table);

  }

  @FXML
  void on_add_button_pressed(ActionEvent event) {
    Router.render_dialog("Test_question_input", (Course_input_controller controller) -> controller.hydrate());
  }
}
