package Model;
import Model.Player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectionTest {
    @Test
    void testGetDirectionInt() {
        assertEquals(Direction.NORTH, Direction.getDirectionInt(0));
        assertEquals(Direction.EAST, Direction.getDirectionInt(1));
        assertEquals(Direction.SOUTH, Direction.getDirectionInt(2));
        assertEquals(Direction.WEST, Direction.getDirectionInt(3));
        assertThrows(IllegalArgumentException.class, () -> Direction.getDirectionInt(-1));
        assertThrows(IllegalArgumentException.class, () -> Direction.getDirectionInt(4));
    }

    @Test
    void testGetPlayerDirection() {
        Player.getInstance().setMyLocationRow(0);
        Player.getInstance().setMyLocationCol(0);

        assertEquals(Direction.SOUTH, Direction.getPlayerDirection(1, 0)); // Move South to (1,0)
        assertEquals(Direction.NORTH, Direction.getPlayerDirection(-1, 0)); // Move North to (-1,0)
        assertEquals(Direction.EAST, Direction.getPlayerDirection(0, 1)); // Move East to (0,1)
        assertEquals(Direction.WEST, Direction.getPlayerDirection(0, -1)); // Move West to (0,-1)
    }

    @Test
    void testToString() {
        assertEquals("up", Direction.NORTH.toString());
        assertEquals("right", Direction.EAST.toString());
        assertEquals("down", Direction.SOUTH.toString());
        assertEquals("left", Direction.WEST.toString());
    }
}
