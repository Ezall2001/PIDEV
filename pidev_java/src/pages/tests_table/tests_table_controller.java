// package pages.tests_table;

// import java.net.URL;
// import java.util.ResourceBundle;

// import entities.Test;
// import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;
// import javafx.scene.control.cell.PropertyValueFactory;
// import javafx.scene.layout.VBox;
// import services.Test_service;
// import utils.Router;
// import utils.Table_view_helpers;

// public class tests_table_controller implements Initializable {

//     static private Test_service test_service = new Test_service();

//     @FXML
//     private VBox table_wrapper;

//     @Override
//     public void initialize(URL location, ResourceBundle resources) {
//         TableView<Test> table = new TableView<>();

//         TableColumn<Test, Integer> min_points_column = new TableColumn<>("Min_points");
//         min_points_column.setCellValueFactory(new PropertyValueFactory<Test, Integer>("_min_points"));

//         TableColumn<Test, Integer> duration_column = new TableColumn<>("Duration");
//         duration_column.setCellValueFactory(new PropertyValueFactory<Test, Integer>("_duration"));

//         TableColumn<Test, String> type_column = new TableColumn<>("Type");
//         type_column.setCellValueFactory(new PropertyValueFactory<Test, String>("_type_string"));

//         TableColumn<Test, String> subject_name_column = new TableColumn<>("Subject");
//         subject_name_column.setCellValueFactory(new PropertyValueFactory<Test, String>("_subject.get_name"));

//         TableColumn<Test, String> course_name_column = new TableColumn<>("Course");
//         course_name_column.setCellValueFactory(new PropertyValueFactory<Test, String>("_course.get_name"));

//         table.getColumns().add(min_points_column);
//         table.getColumns().add(duration_column);
//         table.getColumns().add(type_column);
//         table.getColumns().add(subject_name_column);
//         table.getColumns().add(course_name_column);

//         //         table = Table_view_helpers.add_action_column(table,
//         //         (Test test) -> {
//         // Router.render_dialog(null, null);
//         //         });

//     }

// }
