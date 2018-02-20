package base;

import configuration.Configuration;
import enums.CellColor;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    private int currentStateIndex = 0;
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

    public CellColor getCurrentCellColor() {
        return currentCellColor;
    }

    public void nextState() {
        invertColor();
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

}
