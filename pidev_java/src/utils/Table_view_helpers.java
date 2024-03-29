package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class Table_view_helpers {

  public static <E> TableView<E> add_action_column(TableView<E> table, Consumer<E> edit_callback,
      Consumer<E> delete_callback) {
    TableColumn<E, Void> action_column = new TableColumn<>("Actions");
    action_column.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
    action_column.setSortable(false);

    Callback<TableColumn<E, Void>, TableCell<E, Void>> cellFactory = //
        new Callback<TableColumn<E, Void>, TableCell<E, Void>>() {
          @Override
          public TableCell<E, Void> call(final TableColumn<E, Void> param) {
            final TableCell<E, Void> cell = new TableCell<E, Void>() {

              final ImageView edit_button = new ImageView();
              final ImageView delete_button = new ImageView();

              @Override
              public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setGraphic(null);
                  setText(null);
                } else {

                  if (edit_callback != null)
                    edit_button.setOnMouseClicked(event -> {
                      edit_callback.accept(getTableView().getItems().get(getIndex()));
                    });

                  delete_button.setOnMouseClicked(event -> {
                    delete_callback.accept(getTableView().getItems().get(getIndex()));
                  });

                  HBox actions_wrapper = new HBox();
                  if (edit_callback != null)
                    actions_wrapper.getChildren().add(set_edit_button(edit_button));
                  actions_wrapper.getChildren().add(set_delete_button(delete_button));
                  actions_wrapper.setSpacing(20);
                  actions_wrapper.setAlignment(Pos.CENTER);

                  setGraphic(actions_wrapper);
                  setText(null);
                }
              }
            };
            return cell;
          }
        };

    action_column.setCellFactory(cellFactory);
    action_column.setMaxWidth(80);
    action_column.setMinWidth(80);
    table.getColumns().add(action_column);

    return table;
  }

  private static ImageView set_edit_button(ImageView button) {
    return set_button_image(button, "public/image/modify_icon.png");
  }

  private static ImageView set_delete_button(ImageView button) {
    return set_button_image(button, "public/image/delete_icon.jpg");
  }

  private static ImageView set_button_image(ImageView button, String image_path) {
    try {
      InputStream stream = new FileInputStream(image_path);
      Image edit_image = new Image(stream);
      button.setImage(edit_image);
    } catch (Exception e) {
      Log.file(e.getMessage());
    }

    button.setFitWidth(20);
    button.setFitHeight(20);
    button.setCursor(Cursor.HAND);

    return button;
  }

  public static <E> TableView<E> set_style(TableView<E> table) {
    String style = ".table-cell {-fx-padding: 10px;-fx-font-size: 14px;-fx-text-fill: #333333; -fx-font-weight: bold  } , .table-view .column-header .label {-fx-text-fill: rgb(165, 79, 215);-fx-font-weight: bold;-fx-alignment: CENTER_LEFT; }, .table-view .focused {-fx-focus-color: transparent; -fx-faint-focus-color: transparent; } ,.table-view .column-header-background {-fx-background-color: #4b4b4b;},.table-row-cell:odd {-fx-background-color: #f1f1f1;}, .table-view {-fx-background-color: #e9e9e9;-fx-padding: 10px;},.table-row-cell:hover {-fx-background-color: #d8d8d8; }, .table-view .column-resize-line {-fx-stroke: #0077be;} ,.table-view:focused -fx-focus-color: transparent;-fx-faint-focus-color: transparent;}";
    ///TODO: najiba
    table.setStyle(style);

    // Scene scene = table.getScene();
    // scene.getStylesheets().add("public/css/styling.css");

    table.setCenterShape(true);
    table.setFixedCellSize(70);
    return table;
  }

  public static <E> TableColumn<E, String> set_text_wrap_cell(TableColumn<E, String> column) {
    column.setCellFactory(tc -> {
      TableCell<E, String> cell = new TableCell<>();
      Text text = new Text();
      cell.setGraphic(text);
      cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
      text.wrappingWidthProperty().bind(column.widthProperty());
      text.textProperty().bind(cell.itemProperty());
      return cell;
    });

    return column;
  }

  public static Boolean delete_confirmation() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmer Suppression");
    alert.setContentText("Êtes-vous sûr de votre choix ?");
    Optional<ButtonType> is_confirmed = alert.showAndWait();

    if (is_confirmed.get() != ButtonType.OK)
      return false;

    return true;
  }

}
