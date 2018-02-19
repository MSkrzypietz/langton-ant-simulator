package base;

import configuration.Configuration;
import javafx.application.Platform;
import main.Controller;

public class Ant implements Runnable {

    private Direction currentDirection = Direction.UP;

    private Cell currentCell;
    private boolean isRunning = true;

    private Controller controller;

    public Ant(Cell currentCell, Controller controller) {
        this.currentCell = currentCell;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (isRunning) {
            Platform.runLater(() -> {
                move();
                controller.repaintGridLines();
            });

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean move() {
        if (Configuration.instance.states.get(currentCell.getCurrentStateIndex()).getMovement() == Movement.LEFT) {
            turnLeft();
        } else {
            turnRight();
        }
        currentCell.nextState();
        switch (currentDirection) {
            case UP: {
                isRunning = moveUp();
                break;
            }
            case RIGHT: {
                isRunning = moveRight();
                break;
            }
            case DOWN: {
                isRunning = moveDown();
                break;
            }
            case LEFT: {
                isRunning = moveLeft();
                break;
            }
        }
        controller.incStepCounter();
        return isRunning;
    }

    private void turnLeft() {
        switch (currentDirection) {
            case UP: {
                currentDirection = Direction.LEFT;
                break;
            }
            case LEFT: {
                currentDirection = Direction.DOWN;
                break;
            }
            case DOWN: {
                currentDirection = Direction.RIGHT;
                break;
            }
            case RIGHT: {
                currentDirection = Direction.UP;
                break;
            }
        }
    }

    private void turnRight() {
        switch (currentDirection) {
            case UP: {
                currentDirection = Direction.RIGHT;
                break;
            }
            case RIGHT: {
                currentDirection = Direction.DOWN;
                break;
            }
            case DOWN: {
                currentDirection = Direction.LEFT;
                break;
            }
            case LEFT: {
                currentDirection = Direction.UP;
                break;
            }
        }
    }

    private boolean moveUp() {
        if (currentCell.getRowPos() - 1 < 0) return false;
        currentCell = controller.getCell(currentCell.getRowPos() - 1, currentCell.getColPos());
        return true;
    }

    private boolean moveDown() {
        if (currentCell.getRowPos() + 1 >= Configuration.GRID_SIZE) return false;
        currentCell = controller.getCell(currentCell.getRowPos() + 1, currentCell.getColPos());
        return true;
    }

    private boolean moveLeft() {
        if (currentCell.getColPos() - 1 <= 0) return false;
        currentCell = controller.getCell(currentCell.getRowPos(), currentCell.getColPos() - 1);
        return true;
    }

    private boolean moveRight() {
        if (currentCell.getColPos() + 1 >= Configuration.GRID_SIZE) return false;
        currentCell = controller.getCell(currentCell.getRowPos(), currentCell.getColPos() + 1);
        return true;
    }

}
