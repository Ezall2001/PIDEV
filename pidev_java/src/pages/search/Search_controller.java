package pages.search;

import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import pages.forum_thread.Forum_thread_controller;
import utils.Router;

public class Search_controller {
  @FXML
  private Button return_button;

  @FXML
  private WebView web_view;

  private Question question;

  public void hydrate(Question question) {
    this.question = question;
    WebEngine webEngine = web_view.getEngine();

    webEngine.load(
        "https://bejewelled-cat-145248.netlify.app?" + question.get_title());
  }

  @FXML
  void on_return_button_pressed(ActionEvent event) {
    Router.render_user_template("Forum_thread",
        (Forum_thread_controller controller) -> controller.hydrate(question));
  }

}