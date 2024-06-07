package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    Maze testMaze = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getInstance() {
        Maze testMaze = Maze.getInstance();
        assertEquals(testMaze, Maze.getInstance());
    }

    @org.junit.jupiter.api.Test
    void getMyEntranceRow() {
        Maze.getInstance().setMyEntranceRow(0);
        assertEquals(Maze.getInstance().getMyEntranceRow(), 0);
    }

    @org.junit.jupiter.api.Test
    void getMyEntranceColumn() {
        Maze.getInstance().setMyEntranceColumn(0);
        assertEquals(Maze.getInstance().getMyEntranceColumn(), 0);
    }

    @org.junit.jupiter.api.Test
    void getMyExitRow() {
        Maze.getInstance().setMyExitRow(0);
        assertEquals(Maze.getInstance().getMyExitRow(), 0);
    }

    @org.junit.jupiter.api.Test
    void getMyExitColumn() {
        Maze.getInstance().setMyExitColumn(0);
        assertEquals(Maze.getInstance().getMyExitColumn(), 0);
    }

    @org.junit.jupiter.api.Test
    void getMyRoom() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Maze.getInstance().getMyRoom(50, 50);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Maze.getInstance().getMyRoom(-1, -1);
        });
    }

    @org.junit.jupiter.api.Test
    void getMyAdjacentRoom() {
        assertEquals(Maze.getInstance()
                .getMyRoom(0, 1), Maze.getInstance().getMyAdjacentRoom(Direction.EAST, 0, 0));
    }

    @org.junit.jupiter.api.Test
    void getMyMazeRows() {
        assertEquals(Maze.getInstance().getMyMazeRows(), 4);
    }

    @org.junit.jupiter.api.Test
    void getMyMazeCols() {
        assertEquals(Maze.getInstance().getMyMazeCols(), 4);
    }

    @org.junit.jupiter.api.Test
    void mazeInstantiate() {
        assertNotEquals(Maze.getInstance().getMyRoom(0,0), null);
    }

    @org.junit.jupiter.api.Test
    void checkEntExitGen() {
        Maze.getInstance().setMyEntranceRow(0);
        Maze.getInstance().setMyEntranceColumn(0);
        Maze.getInstance().setMyExitRow(0);
        Maze.getInstance().setMyExitColumn(0);
        Maze.getInstance().checkEntExitGen();
        assertNotEquals(Maze.getInstance().getMyEntranceRow(), Maze.getInstance().getMyExitRow());
        assertNotEquals(Maze.getInstance().getMyEntranceColumn(), Maze.getInstance().getMyExitColumn());
    }

    @org.junit.jupiter.api.Test
    void roomOutOfBounds() {
        Maze.getInstance().roomOutOfBounds(0,0);
        assertTrue(Maze.getInstance().getMyRoom(0, 0).getMyDoor(Direction.NORTH).getMyLeadsOutOfBounds());
        assertTrue(Maze.getInstance().getMyRoom(0, 0).getMyDoor(Direction.WEST).getMyLeadsOutOfBounds());
        assertFalse(Maze.getInstance().getMyRoom(0,0).getMyDoor(Direction.EAST).getMyLeadsOutOfBounds());
        assertFalse(Maze.getInstance().getMyRoom(0,0).getMyDoor(Direction.SOUTH).getMyLeadsOutOfBounds());
    }

    @Test
    void resetMaze() {
        Maze testMaze = Maze.getInstance();
        Maze.resetMaze("MazeyMazeLayout.txt");
        assertNotEquals(testMaze, Maze.getInstance());
    }

    @Test
    void checkOOBMaze() {
        Maze.getInstance().resetMaze("MazeyMazeLayout.txt");
        assertTrue(Maze.getInstance().getMyRoom(0, 0).getMyDoor(Direction.NORTH).getMyLeadsOutOfBounds());
        assertTrue(Maze.getInstance().getMyRoom(0, 0).getMyDoor(Direction.WEST).getMyLeadsOutOfBounds());
        assertFalse(Maze.getInstance().getMyRoom(0,0).getMyDoor(Direction.EAST).getMyLeadsOutOfBounds());
        assertFalse(Maze.getInstance().getMyRoom(0,0).getMyDoor(Direction.SOUTH).getMyLeadsOutOfBounds());
    }

}