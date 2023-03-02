package pages.dashboard;

import java.util.List;

import entities.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.User_service;

public class Dashboard_controller {
    private static User_service user_service = new User_service();

    public void table_view() {

        List<User> users = user_service.get_all();

        TableColumn<User, Integer> id_column = new TableColumn<>("ID");
        TableColumn<User, String> first_name_column = new TableColumn<>("First Name");
        TableColumn<User, String> last_name_column = new TableColumn<>("Last Name");
        TableColumn<User, String> bio_column = new TableColumn<>("Bio");
        TableColumn<User, Integer> age_column = new TableColumn<>("Age");
        TableColumn<User, Integer> score_column = new TableColumn<>("Score");
        TableColumn<User, String> email_column = new TableColumn<>("Email");
        TableColumn<User, String> avatar_path_column = new TableColumn<>("Avatar Path");
        TableColumn<User, String> type_path_column = new TableColumn<>("Type");

        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        first_name_column.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name_column.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        bio_column.setCellValueFactory(new PropertyValueFactory<>("bio"));
        age_column.setCellValueFactory(new PropertyValueFactory<>("age"));
        score_column.setCellValueFactory(new PropertyValueFactory<>("score"));
        email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        avatar_path_column.setCellValueFactory(new PropertyValueFactory<>("avatar_path"));
        type_path_column.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableView<User> tableView = new TableView<>();
        tableView.getColumns().clear();
        tableView.getColumns().add(id_column);
        tableView.getColumns().add(first_name_column);
        tableView.getColumns().add(last_name_column);
        tableView.getColumns().add(bio_column);
        tableView.getColumns().add(age_column);
        tableView.getColumns().add(score_column);
        tableView.getColumns().add(email_column);
        tableView.getColumns().add(avatar_path_column);
        tableView.getColumns().add(type_path_column);

        tableView.getItems().addAll(users);

    }

}
