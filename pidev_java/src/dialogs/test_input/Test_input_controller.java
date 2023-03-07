package dialogs.test_input;

import dialogs.Base_dialog_controller;
import entities.Test;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class Test_input_controller extends Base_dialog_controller {

    @FXML
    private Button add_btn;

    @FXML
    private TextField duration;

    @FXML
    private TextField min_points;

    @FXML
    private MenuButton type;

    public void hydrate(Test test) {

    }

    public void hydrate() {

    }

}
