package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreTest {
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field field = HighScore.class.getDeclaredField("WRITABLE_HIGH_SCORE_FILE");
        field.setAccessible(true);
        field.set(null, "Resource/highscore_test.txt");
    }

    @Test
    void testFileExistsAndReading() {
        File testFile = new File("Resource/highscore_test.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("500\nJohn Doe\n06/07/2024 04:20:00 AM");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HighScore highScore = new HighScore();

        assertTrue(testFile.exists(), "High score file should exist.");

        assertEquals(500, highScore.getScore(), "Score should be 500.");
        assertEquals("John Doe", highScore.getPlayerName(), "Player name should be John Doe.");
        assertEquals("06/07/2024 04:20:00 AM", highScore.getDate(), "Date should be 06/07/2024 04:20:00 AM.");

        assertTrue(testFile.delete(), "Temporary file should be deleted.");
    }
}