package dialogs.file_chooser;

import java.io.File;
import java.util.function.Consumer;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class File_chooser_controller {

  Consumer<File> chose_file_callback;

  public void hydrate(Consumer<File> chose_file_callback) {

    Stage stage = new Stage();

    this.chose_file_callback = chose_file_callback;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File file = fileChooser.showOpenDialog(stage);
    chose_file_callback.accept(file);

    stage.close();

  }

}
