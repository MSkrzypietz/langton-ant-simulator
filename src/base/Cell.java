package base;

import configuration.Configuration;
import enums.CellColor;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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

    public void setCellHeatColor(int MaxVisited){

        //Min zu Max in 6 Bereiche teilen (6 Farben)
        //Bereich größe ausrechnen

        double gapSize = ((double) MaxVisited) / 6;
        double gap = (double) visitedCounter / gapSize;
        /*
            0 = white
            0-1 = Blue
            1-2 = turquoise
            2-3 = Green
            3-4 = Yellow
            4-5 = Orange
            5-6 = Red
         */
        //White
        if (gap == 0.0) {
            super.setFill(Paint.valueOf("FFFFFF"));
        } else if (gap < 1.0) {
            super.setFill(Paint.valueOf("17107f")); //Blue

        } else if (gap < 2.0) {
            super.setFill(Paint.valueOf("13edf4")); //Turqouise

        } else if (gap < 3.0) {
            super.setFill(Paint.valueOf("2eed21")); //Green

        } else if (gap < 4.0) {
            super.setFill(Paint.valueOf("fcfc28")); //Yellow

        } else if (gap < 5.0) {
            super.setFill(Paint.valueOf("ffa216")); //Orange

        } else if (gap >= 5.0) {                    //red
            super.setFill(Paint.valueOf("ef0e37"));
        }

    }

}
