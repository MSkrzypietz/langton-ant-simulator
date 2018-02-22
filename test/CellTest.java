import base.Cell;
import base.State;
import configuration.Configuration;
import enums.Movement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {

    @Test
    public void testNextState() {
        int colPos = Configuration.GRID_SIZE/2;
        int rowPos = Configuration.GRID_SIZE/2;
        Cell cell = new Cell(colPos, rowPos, Configuration.CELL_SIZE);
        Configuration.instance.states.add(new State(Movement.RIGHT));
        Configuration.instance.states.add(new State(Movement.LEFT));

        cell.nextState();
        cell.nextState();

        assertEquals(0, cell.getCurrentStateIndex());

        cell.nextState();
        cell.nextState();
        cell.nextState();

        assertEquals(1, cell.getCurrentStateIndex());

        assertEquals(5, cell.getVisitedCounter());
    }

}
