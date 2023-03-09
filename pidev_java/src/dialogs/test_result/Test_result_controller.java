package dialogs.test_result;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import dialogs.Base_dialog_controller;
import entities.Test_result;
import utils.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test_result_controller extends Base_dialog_controller implements Initializable {

  @FXML
  private Label correct_questions_label;

  @FXML
  private ImageView image;

  @FXML
  private Label incorrect_questions_label;

  @FXML
  private Label total_questions_label;

  @FXML
  private Label test_state_label;

  private static String fail_image = "public/image/test_fail_hero.png";

  private Test_result test_result;
  private Integer test_questions_number, correct_options_number, incorrect_options_number;
  private Boolean is_success;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    test_questions_number = 0;
    correct_options_number = 0;
    incorrect_options_number = 0;
  }

  public void hydrate(Test_result test_result, Boolean is_success) {
    this.test_result = test_result;
    this.is_success = is_success;

    test_questions_number = test_result.get_test().get_questions().size();
    correct_options_number = test_result.get_mark();
    incorrect_options_number = test_questions_number - correct_options_number;

    total_questions_label.setText(test_questions_number.toString());
    correct_questions_label.setText(correct_options_number.toString());
    incorrect_questions_label.setText(incorrect_options_number.toString());
    set_image();
    set_state();
  }

  @FXML
  void on_print_button_pressed(ActionEvent event) throws FileNotFoundException, DocumentException {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    String file_path = "public/test_pdf.pdf";
    Document document = new Document(PageSize.A4);
    document.setMargins(60, 60, 50, 50);

    PdfWriter.getInstance(document, new FileOutputStream(file_path));

    Font note_font = FontFactory.getFont(FontFactory.COURIER_BOLD, 15f);
    Font results_font = FontFactory.getFont(FontFactory.COURIER, 14f);
    Font Title_font = FontFactory.getFont(FontFactory.COURIER, 25f, Font.BOLD, BaseColor.BLACK);
    Font anchor_font = FontFactory.getFont(FontFactory.COURIER_OBLIQUE, 10f, Font.NORMAL, BaseColor.BLUE);

    Paragraph date = new Paragraph("Date :" + dtf.format(now));
    date.setAlignment(Element.ALIGN_RIGHT);

    Paragraph email = new Paragraph("Email : esprit.myalo@gmail.com ");
    email.setAlignment(Element.ALIGN_LEFT);

    Paragraph title = new Paragraph(" \n Résultat test \n \n ", Title_font);
    title.setAlignment(Element.ALIGN_CENTER);

    Paragraph big_title = new Paragraph(
        "Mr/Mme " + test_result.get_user().get_full_name().toString()
            + ", vous trouvez ci-dessous votre résultat. \n \n",
        results_font);
    big_title.setAlignment(Element.ALIGN_CENTER);

    Paragraph questions_number = new Paragraph(" - Total questions : " + total_questions_label.getText() + "\n",
        results_font);
    questions_number.setAlignment(Element.ALIGN_LEFT);

    Paragraph correct_options_number = new Paragraph(
        " - Nombre de réponses correctes : " + correct_questions_label.getText() + "\n",
        results_font);
    correct_options_number.setAlignment(Element.ALIGN_LEFT);

    Paragraph incorrect_options_number = new Paragraph(
        " - Nombre de réponses incorrectes : " + incorrect_questions_label.getText() + "\n ",
        results_font);
    incorrect_options_number.setAlignment(Element.ALIGN_LEFT);

    Paragraph note = new Paragraph("Note finale : " + test_result.get_mark().toString() + "\n \n", note_font);

    Paragraph paragraph_4 = new Paragraph("Merci pour joindre Myalo ! Bonne journée :) ", results_font);
    paragraph_4.setAlignment(Element.ALIGN_CENTER);
    Paragraph paragraph_5 = new Paragraph("N'oubliez pas de visiter notre page Facebook ! \n", results_font);
    paragraph_5.setAlignment(Element.ALIGN_CENTER);

    Anchor anchor = new Anchor(" ----> Cliquer sur le lien pour en savoir plus ", anchor_font);

    anchor.setReference("https://www.facebook.com/profile.php?id=100090591443416");

    document.open();
    document.add(date);
    document.add(email);
    document.add(title);
    document.add(big_title);
    document.add(questions_number);
    document.add(correct_options_number);
    document.add(incorrect_options_number);
    document.add(note);
    document.add(paragraph_4);
    document.add(paragraph_5);
    document.add(anchor);

    Log.console("printed");

    document.close();

  }

  private void set_image() {

    if (is_success)
      return;

    try {
      InputStream stream = new FileInputStream(fail_image);
      Image fail_image = new Image(stream);
      image.setImage(fail_image);

    } catch (IOException e) {
      Log.file(e.getMessage());
    }

  }

  private void set_state() {
    if (is_success)
      return;

    test_state_label.setText("Test Echoué");
    test_state_label.setTextFill(Paint.valueOf("#E54242"));

  }

}
