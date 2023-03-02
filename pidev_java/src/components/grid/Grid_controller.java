package components.grid;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class Grid_controller {

  private static Integer max_computed_width = 3840;

  @FXML
  private GridPane grid;

  List<Parent> components;
  Number width = 0, height = 0;
  Integer component_h = 0, component_w = 0;
  Integer h_spacing = 0, v_spacing = 0;
  List<Integer> breakpoints;
  Integer curr_breakpoint_index;

  public void hydrate(List<Parent> components, Integer component_w, Integer component_h, Integer h_spacing,
      Integer v_spacing) {
    grid.setHgap(h_spacing);
    grid.setVgap(v_spacing);
    this.h_spacing = h_spacing;
    this.v_spacing = v_spacing;
    this.component_w = component_w;
    this.component_h = component_h;
    this.components = components;
    this.breakpoints = new ArrayList<>();
    this.curr_breakpoint_index = 0;
    compute_breakpoints();
    compute_current_breakpoint_index();
    on_grid_resize();

  }

  private void on_grid_resize() {
    grid.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable_value, Number prv_width,
          Number curr_width) {
        width = observable_value.getValue().intValue();
        render_grid();
      }
    });

    grid.setPrefWidth(grid.getWidth());
  }

  private void compute_breakpoints() {
    Integer n_columns = 1;
    Integer computed_width = 0;

    while (computed_width < max_computed_width) {
      computed_width = n_columns * (component_w + h_spacing) - h_spacing;
      if (computed_width < max_computed_width)
        breakpoints.add(computed_width);
      n_columns++;
    }

  }

  private void compute_current_breakpoint_index() {
    if (width.doubleValue() > breakpoints.get(curr_breakpoint_index))
      while (curr_breakpoint_index != (breakpoints.size() - 2)
          && width.doubleValue() > breakpoints.get(curr_breakpoint_index + 1))
        curr_breakpoint_index++;
    else
      while (curr_breakpoint_index != 0 && width.doubleValue() <= breakpoints.get(curr_breakpoint_index))
        curr_breakpoint_index--;
  }

  private void compute_grid_height() {
    Integer n_columns = curr_breakpoint_index + 1;
    Integer n_components = components.size();
    Integer n_rows = ((n_components + n_columns - 1) / n_columns) + 1;
    Integer grid_height = (n_rows * (component_h + v_spacing)) - v_spacing;
    grid.setPrefHeight(grid_height);

  }

  private void render_grid() {
    Integer low_bound_breakpoint = breakpoints.get(curr_breakpoint_index);
    Integer high_bound_breakpoint = breakpoints.get(curr_breakpoint_index + 1);
    if (width.doubleValue() < high_bound_breakpoint && width.doubleValue() > low_bound_breakpoint)
      return;

    compute_current_breakpoint_index();

    Integer n_columns = curr_breakpoint_index + 1;
    Double column_width = ((width.doubleValue() - ((n_columns - 1) * h_spacing)) / n_columns);

    Integer i = 0;
    grid.getChildren().removeAll(components);
    while (i < components.size()) {
      Integer column_index = i % n_columns;
      Integer row_index = i / n_columns;
      grid.add(components.get(i), column_index, row_index);
      i++;
    }

    grid.getColumnConstraints().stream().forEach(column -> {
      column.setPrefWidth(column_width);
    });
    grid.getRowConstraints().stream().forEach(row -> row.setPrefHeight(component_h));

    compute_grid_height();
  }

}
