package main;

import base.Ant;
import base.Arrow;
import base.Cell;
import base.State;
import configuration.Configuration;
import enums.Direction;
import enums.Movement;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.omg.PortableInterceptor.DISCARDING;

import java.util.Arrays;
import java.util.List;

import static configuration.Configuration.CELL_SIZE;
import static configuration.Configuration.GRID_SIZE;

public class Controller {

    private int counter = 0;
    private int currentSpeed = 1000;
    private Cell[][] matrix = new Cell[GRID_SIZE][GRID_SIZE];
    private Thread antThread;
    private Arrow arrow = new Arrow();

    @FXML
    private GridPane grid;

    @FXML
    private ComboBox<String> modeComboBox;

    @FXML ComboBox<String> directionComboBox;

    @FXML
    private Label stepCounter;

    @FXML
    private Slider speedSlider;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button heatMapButton;

    @FXML
    private Button clearButton;

    public void initialize() {
        initModeComboBox();
        initDirectionComboBox();
        initGridConstraints();
        initGridCells();
        initSpeedSliderListener();
        repaintGridLines();
        Platform.runLater(() -> {
            grid.add(arrow, GRID_SIZE/2, GRID_SIZE/2);
        });
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

    private void initDirectionComboBox() {
        ObservableList<String> directions = new ObservableListBase<String>() {
            private List<String> directions = Arrays.asList("UP", "RIGHT", "DOWN", "LEFT");

            @Override
            public String get(int index) {
                return directions.get(index);
            }

            @Override
            public int size() {
                return directions.size();
            }
        };
        directionComboBox.setItems(directions);
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
        grid.getChildren().clear();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                matrix[i][j] = new Cell(i, j, CELL_SIZE);
                grid.add(matrix[i][j], i, j);
            }
        }
    }

    @FXML
    private void startSimulation() {
        if (modeComboBox.getValue() == null || directionComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Field");
            alert.setHeaderText(null);
            if (modeComboBox.getValue() == null)
                alert.setContentText("Please select a simulation mode.");
            else
                alert.setContentText("Please select a starting direction.");
            alert.showAndWait();
            return;
        }
        initStateList(modeComboBox.getValue());
        setArrowDirection(directionComboBox.getValue());

        startButton.disableProperty().setValue(true);
        stopButton.disableProperty().setValue(false);
        modeComboBox.disableProperty().setValue(true);
        directionComboBox.disableProperty().setValue(true);
        clearButton.disableProperty().setValue(true);

        Ant ant = new Ant(matrix[GRID_SIZE/2][GRID_SIZE/2], this, arrow);
        antThread = new Thread(ant);
        antThread.start();
    }

    @FXML
    public void stopSimulation() {
        antThread.interrupt();
        stopButton.disableProperty().setValue(true);
        heatMapButton.disableProperty().setValue(false);
        clearButton.disableProperty().setValue(false);
    }

    @FXML
    private void startHeatMapShow() {
        heatMapButton.disableProperty().setValue(true);
        showHeatMap();
    }

    private void showHeatMap(){
        int maxVisitedCounter = getMaxVisitedCounter();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                matrix[i][j].setCellHeatColor(maxVisitedCounter);
            }
        }
    }

    private int getMaxVisitedCounter(){
        int maxVisitedCounter =0;
        for (int i = 0; i < GRID_SIZE; i++)
            for (int j = 0; j < GRID_SIZE; j++)
                if (matrix[i][j].getVisitedCounter() > maxVisitedCounter)
                    maxVisitedCounter = matrix[i][j].getVisitedCounter();
        return  maxVisitedCounter;
    }

    private void initStateList(String movementString) {
        Configuration.instance.states.clear();
        String[] movements = movementString.split("");
        for (String movement : movements)
            if (movement.equals("R"))
                Configuration.instance.states.add(new State(Movement.RIGHT));
            else
                Configuration.instance.states.add(new State(Movement.LEFT));
    }

    private void setArrowDirection(String direction) {
        switch (direction) {
            case "UP" : {
                arrow.setDirection(Direction.UP);
                return;
            }
            case "RIGHT" : {
                arrow.setDirection(Direction.RIGHT);
                return;
            }
            case "DOWN" : {
                arrow.setDirection(Direction.DOWN);
                return;
            }
            case "LEFT" : {
                arrow.setDirection(Direction.LEFT);
            }
        }
    }

    public void repaintGridLines() {
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);
    }

    public Cell getCell(int rowPos, int colPos) {
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

    public GridPane getGrid() { return grid; }

    @FXML
    private void clearSimulation() {
        counter = 0;
        stepCounter.setText("Steps: " + counter);
        matrix = new Cell[GRID_SIZE][GRID_SIZE];
        initGridCells();
        updateArrowDirection();
        repaintGridLines();

        startButton.disableProperty().setValue(false);
        stopButton.disableProperty().setValue(true);
        modeComboBox.disableProperty().setValue(false);
        directionComboBox.disableProperty().setValue(false);
        heatMapButton.disableProperty().setValue(true);
    }

    @FXML
    private void updateArrowDirection() {
        Platform.runLater(() -> {
            grid.getChildren().remove(arrow);
            setArrowDirection(directionComboBox.getValue());
            grid.add(arrow, GRID_SIZE/2, GRID_SIZE/2);
        });
    }

}
