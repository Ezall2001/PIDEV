package components;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class question_window {
    private Stage stage = new Stage();

    public void start_question_window() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("subject_test.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

}
