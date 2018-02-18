package base;

public class Ant {

    private int currentRowPos;
    private int currentColPos;

    private final int rowCount;
    private final int colCount;

    public Ant(int currentRowPos, int currentColPos, int rowCount, int colCount) {
        this.currentRowPos = currentRowPos;
        this.currentColPos = currentColPos;
        this.rowCount = rowCount;
        this.colCount = colCount;
    }

    public int getCurrentRowPos() {
        return currentRowPos;
    }

    public int getCurrentColPos() {
        return currentColPos;
    }

    public boolean moveUp() {
        return false;
    }

    public boolean moveDown() {
        return false;
    }

    public boolean moveLeft() {
        return false;
    }

    public boolean moveRight() {
        return false;
    }

}
