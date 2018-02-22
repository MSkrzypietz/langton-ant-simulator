package base;

import configuration.Configuration;
import enums.Direction;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Arrow extends Group {

    private Direction currentDirection = Direction.UP;

    public Arrow() {
        this(new Line(), new Line());
    }

    private Arrow(Line leftArrowHalf, Line rightArrowHalf) {
        super(leftArrowHalf, rightArrowHalf);

        leftArrowHalf.setStartX(0);
        leftArrowHalf.setStartY(0);
        leftArrowHalf.setEndX(Configuration.CELL_SIZE * 0.9);
        leftArrowHalf.setEndY(Configuration.CELL_SIZE/2);

        rightArrowHalf.setStartX(0);
        rightArrowHalf.setStartY(Configuration.CELL_SIZE);
        rightArrowHalf.setEndX(Configuration.CELL_SIZE * 0.9);
        rightArrowHalf.setEndY(Configuration.CELL_SIZE/2);

        leftArrowHalf.setStroke(Paint.valueOf("FF0000"));
        rightArrowHalf.setStroke(Paint.valueOf("FF0000"));

        leftArrowHalf.setStrokeWidth(1.5);
        rightArrowHalf.setStrokeWidth(1.5);

        setDirection(Direction.UP);
    }

    public void setDirection(Direction direction) {
        currentDirection = direction;
        switch (direction) {
            case UP: {
                this.setRotate(270);
                break;
            }
            case RIGHT: {
                this.setRotate(0);
                break;
            }
            case DOWN: {
                this.setRotate(90);
                break;
            }
            case LEFT: {
                this.setRotate(180);
                break;
            }
        }
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

}
