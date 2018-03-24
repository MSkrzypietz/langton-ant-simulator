package base;

import configuration.Configuration;
import enums.CellColor;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Cell extends Rectangle {

    private int currentStateIndex = 0;
    private int visitedCounter = 0;
    private CellColor currentCellColor = CellColor.WHITE;

    private int rowPos;
    private int colPos;

    public Cell(int colPos, int rowPos, int size) {
        super(size, size, Paint.valueOf("FFFFFF"));
        this.colPos = colPos;
        this.rowPos = rowPos;
    }

    public int getRowPos() {
        return rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public int getCurrentStateIndex() {
        return currentStateIndex;
    }

    public int getVisitedCounter() {
        return visitedCounter;
    }

    public void nextState() {
        invertColor();
        visitedCounter++;
        currentStateIndex = (currentStateIndex + 1) % Configuration.instance.states.size();
    }

    private void invertColor() {
        if (currentCellColor == CellColor.WHITE) {
            super.setFill(Paint.valueOf("000000"));
            currentCellColor = CellColor.BLACK;
        } else {
            super.setFill(Paint.valueOf("FFFFFF"));
            currentCellColor = CellColor.WHITE;
        }
    }

    public void setCellHeatColor(ArrayList<Integer> sectionsForColor){

        if(visitedCounter == 0){
            super.setFill(Paint.valueOf("FFFFFF"));
        }
        else if (sectionsForColor.get(1)>visitedCounter) {
            super.setFill(Paint.valueOf("10102f"));
        } else if (sectionsForColor.get(2)>visitedCounter) {
            super.setFill(Paint.valueOf("17107f")); //Blue

        } else if (sectionsForColor.get(3)>visitedCounter) {
            super.setFill(Paint.valueOf("13edf4")); //Turqouise

        } else if (sectionsForColor.get(4) > visitedCounter) {
            super.setFill(Paint.valueOf("2eed21")); //Green

        } else if (sectionsForColor.get(5) > visitedCounter) {
            super.setFill(Paint.valueOf("fcfc28")); //Yellow
        } else if (sectionsForColor.get(6) > visitedCounter) {
            super.setFill(Paint.valueOf("ffa216")); //Orange

        } else if (sectionsForColor.get(7) >= visitedCounter ) {  //red
            super.setFill(Paint.valueOf("ef0e37"));
        }

    }

}
