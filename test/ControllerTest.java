import base.Cell;
import main.Controller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    @Test
    public void testGetMaxVisitedCounter() {
        Controller controller = new Controller();

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

        assertEquals(4, controller.getMaxVisitedCounter());
    }

}
