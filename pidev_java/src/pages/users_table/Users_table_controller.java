package pages.users_table;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import entities.User;
import entities.User.Type;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import services.User_service;
import utils.Router;
import utils.Table_view_helpers;

public class Users_table_controller implements Initializable {

    @FXML
    private VBox table_wrapper;

    static private User_service user_service = new User_service();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableView<User> table = new TableView<>();

        TableColumn<User, String> first_name_column = new TableColumn<>("Prénom");
        first_name_column.setCellValueFactory(new PropertyValueFactory<User, String>("_first_name"));

        TableColumn<User, String> last_name_column = new TableColumn<>("Nom");
        last_name_column.setCellValueFactory(new PropertyValueFactory<User, String>("_last_name"));

        TableColumn<User, String> email_column = new TableColumn<>("Email");
        email_column.setCellValueFactory(new PropertyValueFactory<User, String>("_email"));
        email_column.setMinWidth(100);

        TableColumn<User, Integer> age_column = new TableColumn<>("Age");
        age_column.setCellValueFactory(new PropertyValueFactory<User, Integer>("_age"));

        TableColumn<User, String> bio_column = new TableColumn<>("Biographie");
        bio_column.setCellValueFactory(new PropertyValueFactory<User, String>("_bio"));
        bio_column = Table_view_helpers.set_text_wrap_cell(bio_column);
        bio_column.setMinWidth(500);

        TableColumn<User, Integer> score_column = new TableColumn<>("Score");
        score_column.setCellValueFactory(new PropertyValueFactory<User, Integer>("_score"));

        table.getColumns().add(first_name_column);
        table.getColumns().add(last_name_column);
        table.getColumns().add(email_column);
        table.getColumns().add(age_column);
        table.getColumns().add(bio_column);
        table.getColumns().add(score_column);

        table = Table_view_helpers.add_action_column(table,
                null,
                (User user) -> {
                    if (!Table_view_helpers.delete_confirmation())
                        return;

                    user_service.delete(user);
                    Router.render_admin_template("Users_table", null);
                });

        List<User> users = user_service
                .get_all()
                .stream()
                .filter(user -> user.get_type() == Type.STUDENT)
                .collect(Collectors.toList());

        table.getItems().addAll(users);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table = Table_view_helpers.set_style(table);

        table_wrapper.getChildren().add(table);

    }
}
