package main;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Arrays;
import java.util.List;

public class Controller {

    @FXML
    private GridPane grid;

    @FXML
    private ComboBox<String> modeComboBox;

    public void initialize() {
        initModeComboBox();
        initGrid();
    }

    private void initModeComboBox() {
        ObservableList<String> modes = new ObservableListBase<String>() {
            private List<String> modes = Arrays.asList("RL", "RLR", "LLRR");

            @Override
            public String get(int index) {
                return modes.get(index);
            }

            @Override
            public int size() {
                return modes.size();
            }
        };
        modeComboBox.setItems(modes);
    }

    private void initGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ColumnConstraints column = new ColumnConstraints(8);
                grid.getColumnConstraints().add(column);

                RowConstraints row = new RowConstraints(8);
                grid.getRowConstraints().add(row);
            }
        }
    }

}
