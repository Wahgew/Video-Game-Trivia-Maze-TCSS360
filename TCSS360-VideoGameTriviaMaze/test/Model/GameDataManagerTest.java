package Model;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class GameDataManagerTest {
    private GameDataManager gameDataManager;

    @Test
    void saveGameData() {
        gameDataManager = new GameDataManager();

        Player player = Player.getInstance();
        player.setMyLocationRow(1);
        player.setMyLocationCol(1);
        player.setMyHealth(3);
        player.setMyCheat(false);
        player.setMyScore(1000);
        player.setMyIncorrectTotal(0);
        player.setMyVictory(false);
        player.setMyDirection(Direction.SOUTH);
        player.setMyMazeLayout("default");

        Maze maze = Maze.getInstance();
        maze.setMyEntranceRow(0);
        maze.setMyEntranceColumn(0);
        maze.setMyExitColumn(2);
        maze.setMyExitRow(2);
        gameDataManager.saveGameData();

        File saveFile = new File("Resource/save.json");
        assertTrue(saveFile.exists(), "Save file should exist after saving game data.");
    }

    @Test
    void loadGameData() {
        gameDataManager = new GameDataManager();

        gameDataManager.loadGameData();

        Player player = Player.getInstance();
        assertEquals(1, player.getMyLocationRow());
        assertEquals(1, player.getMyLocationCol());
        assertEquals(3, player.getMyHealth());
        assertEquals(1000, player.getMyScore());
        assertEquals(0, player.getMyIncorrectTotal());
        assertEquals(Direction.SOUTH, player.getMyDirection());
        assertEquals("default", player.getMyMazeLayout());
        assertFalse(player.getMyCheat());
        assertFalse(player.getMyVictory());

        Maze maze = Maze.getInstance();
        assertEquals(0, maze.getMyEntranceColumn());
        assertEquals(0, maze.getMyEntranceRow());
        assertEquals(2, maze.getMyExitColumn());
        assertEquals(2, maze.getMyExitRow());
        assertEquals("default", maze.getMyLayout());

    }
}
