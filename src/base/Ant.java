package base;

import configuration.Configuration;
import enums.Direction;
import enums.Movement;
import javafx.application.Platform;
import main.Controller;

public class Ant implements Runnable {

    private Direction currentDirection = Direction.UP;

    private Cell currentCell;

    private Controller controller;

    private Thread antThread;

    public Ant(Cell currentCell, Controller controller) {
        this.currentCell = currentCell;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            antThread = Thread.currentThread();
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(() -> {
                    move();
                    controller.repaintGridLines();
                });
                Thread.sleep(controller.getCurrentSpeed());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            controller.stopSimulation();
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
        if (currentCell.getRowPos() - 1 < 0) stopAntThread();
        currentCell = controller.getCell(currentCell.getRowPos() - 1, currentCell.getColPos());
    }

    private void moveDown() {
        if (currentCell.getRowPos() + 1 >= Configuration.GRID_SIZE) stopAntThread();
        currentCell = controller.getCell(currentCell.getRowPos() + 1, currentCell.getColPos());
    }

    private void moveLeft() {
        if (currentCell.getColPos() - 1 <= 0) stopAntThread();
        currentCell = controller.getCell(currentCell.getRowPos(), currentCell.getColPos() - 1);
    }

    private void moveRight() {
        if (currentCell.getColPos() + 1 >= Configuration.GRID_SIZE) stopAntThread();
        currentCell = controller.getCell(currentCell.getRowPos(), currentCell.getColPos() + 1);
    }

    private void stopAntThread() {
        antThread.interrupt();
    }

}
