package pages.subjects_table;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dialogs.course_input.Course_input_controller;
import dialogs.subject_input.Subject_input_controller;
import entities.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import services.Subject_service;
import utils.Router;
import utils.Table_view_helpers;

public class Subjects_table_controller implements Initializable {

    static private Subject_service subject_service = new Subject_service();

    @FXML
    private VBox table_wrapper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableView<Subject> table = new TableView<>();

        TableColumn<Subject, String> name_column = new TableColumn<>("Nom");
        name_column.setCellValueFactory(new PropertyValueFactory<Subject, String>("_name"));

        TableColumn<Subject, String> classes_esprit_column = new TableColumn<>("Classe");
        classes_esprit_column.setCellValueFactory(new PropertyValueFactory<Subject, String>("_classes_esprit_string"));

        TableColumn<Subject, String> description_column = new TableColumn<>("Description");
        description_column.setCellValueFactory(new PropertyValueFactory<Subject, String>("_description"));
        description_column = Table_view_helpers.set_text_wrap_cell(description_column);
        description_column.setMinWidth(500);

        table.getColumns().add(name_column);
        table.getColumns().add(classes_esprit_column);
        table.getColumns().add(description_column);

        table = Table_view_helpers.add_action_column(table,
                (Subject subject) -> {
                    Router.render_dialog("Subject_input",
                            (Subject_input_controller controller) -> controller.hydrate(subject));
                },
                (Subject subject) -> {
                    if (!Table_view_helpers.delete_confirmation())
                        return;

                    subject_service.delete(subject);
                    Router.render_admin_template("Subjects_table", null);
                });

        List<Subject> subjects = subject_service.get_all();
        table.getItems().addAll(subjects);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table = Table_view_helpers.set_style(table);

        table_wrapper.getChildren().add(table);

    }

    @FXML
    void on_add_button_pressed(ActionEvent event) {
        Router.render_dialog("Subject_input", (Subject_input_controller controller) -> controller.hydrate());
    }

}
