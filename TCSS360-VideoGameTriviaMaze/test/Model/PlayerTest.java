package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = Player.getInstance();
    }

    @Test
    void getInstance() {
        Player player2 = Player.getInstance();
        assertEquals(player, player2);
    }

    @Test
    void resetPlayer() {
        Player.resetPlayer();
        Player player2 = Player.getInstance();
        assertNotEquals(player, player2);
    }
    @Test
    void getMyHealth() {
        assertEquals(3, player.getMyHealth());
    }

    @Test
    void decreaseHealth() {
        player.decreaseHealth();
        assertEquals(2, player.getMyHealth());
    }



    @Test
    void questionsAnswered() {
        player.QuestionsAnswered(1, true);
        HashMap<Integer, Boolean> questionsAnswered = player.getMyQuestionsAnswered();
        assertTrue(questionsAnswered.containsKey(1));
        assertTrue(questionsAnswered.containsValue(true));
    }

    @Test
    void validPlayerMove() {
        assertTrue(player.validPlayerMove(Direction.NORTH));
    }

    @Test
    void scoreUpdate() {
        player.scoreUpdate(true);
        assertEquals(100, player.getMyScore());
    }

    @Test
    void movePlayer() {
        int initialRow = player.getMyLocationRow();
        int initialCol = player.getMyLocationCol();
        player.movePlayer(Direction.NORTH);
        assertEquals(initialRow - 1, player.getMyLocationRow());
        assertEquals(initialCol, player.getMyLocationCol());
    }

    @Test
    void checkVictory() {
        assertFalse(player.checkVictory());
    }
}
