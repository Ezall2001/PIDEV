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
import javafx.application.Application;
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
import utils.Router;

public class Najiba_test extends Application {
    Answer_service s = new Answer_service();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage main_stage) {
        Router.init(main_stage);
        Router.render_user_template("Subjects_table", null);
    }

}