import base.Cell;
import main.Controller;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    @Test
    public void testGetMaxVisitedCounter() {
        Controller controller = new Controller();
        controller.initGridCellsTest();

        Cell cell = controller.getCell(0, 0);
        cell.nextState();
        cell.nextState();
        cell.nextState();

        cell = controller.getCell(1, 0);
        cell.nextState();
        cell.nextState();

        cell = controller.getCell(1, 1);
        cell.nextState();
        cell.nextState();
        cell.nextState();
        cell.nextState();

        assertEquals(4, controller.getMaxVisitedCounter().stream().max(Comparator.comparing(i -> i)).get().intValue());
    }

}
