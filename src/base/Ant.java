package base;

import configuration.Configuration;
import enums.Direction;
import enums.Movement;
import javafx.application.Platform;
import main.Controller;

public class Ant implements Runnable {

    private Direction currentDirection;

    private Cell currentCell;

    private Controller controller;

    private Thread antThread;

    private Arrow arrow;

    public Ant(Cell currentCell, Controller controller, Arrow arrow) {
        this.currentCell = currentCell;
        this.controller = controller;
        this.arrow = arrow;
        this.currentDirection = arrow.getCurrentDirection();
    }

    @Override
    public void run() {
        try {
            antThread = Thread.currentThread();
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(() -> {
                    controller.getGrid().getChildren().remove(arrow);
                    move();
                    controller.updateCounterLabel();
                    arrow.setDirection(currentDirection);
                    controller.getGrid().add(arrow, currentCell.getColPos(), currentCell.getRowPos());
                    controller.repaintGridLines();
                });
                Thread.sleep(controller.getCurrentSpeed());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            stopAntThread();
        }
    }

    private void move() {
        if (Configuration.instance.states.get(currentCell.getCurrentStateIndex()).getMovement() == Movement.LEFT) {
            turnLeft();
        } else {
            turnRight();
        }
        currentCell.nextState();
        switch (currentDirection) {
            case UP: {
                moveUp();
                break;
            }
            case RIGHT: {
                moveRight();
                break;
            }
            case DOWN: {
                moveDown();
                break;
            }
            case LEFT: {
                moveLeft();
                break;
            }
        }
        controller.incStepCounter();
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

    private void moveUp() {
        if (currentCell.getRowPos() - 2 <= 0) { stopAntThread(); return; }
        currentCell = controller.getCell(currentCell.getRowPos() - 1, currentCell.getColPos());
    }

    private void moveDown() {
        if (currentCell.getRowPos() + 2 >= Configuration.GRID_SIZE - 1) { stopAntThread(); return; }
        currentCell = controller.getCell(currentCell.getRowPos() + 1, currentCell.getColPos());
    }

    private void moveLeft() {
        if (currentCell.getColPos() - 2 <= 0) { stopAntThread(); return; }
        currentCell = controller.getCell(currentCell.getRowPos(), currentCell.getColPos() - 1);
    }

    private void moveRight() {
        if (currentCell.getColPos() + 2 >= Configuration.GRID_SIZE - 1) { stopAntThread(); return; }
        currentCell = controller.getCell(currentCell.getRowPos(), currentCell.getColPos() + 1);
    }

    private void stopAntThread() {
        antThread.interrupt();
        controller.stopSimulation();
    }

}
