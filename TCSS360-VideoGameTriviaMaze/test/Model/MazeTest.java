package Model;

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
    }

    @org.junit.jupiter.api.Test
    void getMyEntranceColumn() {
    }

    @org.junit.jupiter.api.Test
    void getMyExitRow() {
    }

    @org.junit.jupiter.api.Test
    void getMyExitColumn() {
    }

    @org.junit.jupiter.api.Test
    void getMyRoom() {
    }

    @org.junit.jupiter.api.Test
    void getMyAdjacentRoom() {
    }

    @org.junit.jupiter.api.Test
    void getMyMazeRows() {
    }

    @org.junit.jupiter.api.Test
    void getMyMazeCols() {
    }

    @org.junit.jupiter.api.Test
    void mazeInstantiate() {
    }

    @org.junit.jupiter.api.Test
    void checkEntExitGen() {
    }

    @org.junit.jupiter.api.Test
    void roomOutOfBounds() {

    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }
}