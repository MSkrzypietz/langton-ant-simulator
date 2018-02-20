package main;

import base.Ant;
import base.Cell;
import enums.Movement;
import base.State;
import configuration.Configuration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Arrays;
import java.util.List;

import static configuration.Configuration.CELL_SIZE;
import static configuration.Configuration.GRID_SIZE;

public class Controller {

    private int counter = 0;
    private int currentSpeed = 1000;
    private Cell[][] matrix = new Cell[GRID_SIZE][GRID_SIZE];

    @FXML
    private GridPane grid;

    @FXML
    private ComboBox<String> modeComboBox;

    @FXML
    private Label stepCounter;

    @FXML
    private Slider speedSlider;

    public void initialize() {
        initModeComboBox();
        initGridConstraints();
        initGridCells();
        initSpeedSliderListener();

        repaintGridLines();
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

    private void initGridConstraints() {
        for (int i = 0; i < GRID_SIZE; i++) {
            ColumnConstraints column = new ColumnConstraints(CELL_SIZE);
            grid.getColumnConstraints().add(column);

            RowConstraints row = new RowConstraints(CELL_SIZE);
            grid.getRowConstraints().add(row);
        }
    }

    private void initGridCells() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                matrix[i][j] = new Cell(i, j, CELL_SIZE);
                grid.add(matrix[i][j], i, j);
            }
        }
    }

    @FXML
    private void startSimulation(ActionEvent event) throws InterruptedException {
        if (modeComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Field");
            alert.setHeaderText(null);
            alert.setContentText("Please select a simulation mode.");
            alert.showAndWait();
            return;
        }
        initStateList(modeComboBox.getValue());

        Ant ant = new Ant(matrix[GRID_SIZE/2][GRID_SIZE/2], this);
        Thread thread = new Thread(ant);
        thread.start();

    }

    private void initStateList(String movementString) {
        String[] movements = movementString.split("");
        for (String movement : movements)
            if (movement.equals("R"))
                Configuration.instance.states.add(new State(Movement.RIGHT));
            else
                Configuration.instance.states.add(new State(Movement.LEFT));
    }

    public void repaintGridLines() {
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);
    }

    public Cell getCell(int rowPos, int colPos) {
        //TODO: fix row/col pos
        return matrix[colPos][rowPos];
    }

    public void incStepCounter() {
        stepCounter.setText("Steps: " + ++counter);
    }

    private void initSpeedSliderListener() {
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> currentSpeed = (int) (5 + (995 - 9.95*newValue.doubleValue())));
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

}
