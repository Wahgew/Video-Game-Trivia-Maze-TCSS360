package Model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * HighScore class represents the high score of the game.
 * It manages the loading, saving, and resetting of high scores.
 * <p>
 * High scores are stored in a file named "highscore.txt" in the format:
 * - Score
 * - Player Name
 * - Date
 * If the file doesn't exist or is corrupted, default values are used.
 * </p>
 * <p>
 * High scores are saved only if a new high score is achieved.
 * </p>
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 *
 * @version 0.0.1 May 20, 2024
 */
public class HighScore {
    private static final String HIGH_SCORE_FILE = "/Resource/highscore.txt";
    private static final String WRITABLE_HIGH_SCORE_FILE = "Resource/highscore.txt";
    private int myScore;
    private String myPlayerName;
    private LocalDateTime myDate;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");

    /**
     * Constructs a HighScore object and loads the high score data from the file.
     */
    public HighScore() {
        loadHighScore();
    }

    /**
     * Gets the current high score.
     *
     * @return the current high score
     */
    public int getScore() {
        return myScore;
    }

    /**
     * Gets the player name associated with the high score.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return myPlayerName;
    }

    /**
     * Gets the date and time when the high score was achieved.
     *
     * @return the date and time of the high score
     */
    public String getDate() {
        return myDate.format(DATE_FORMATTER);
    }

    /**
     * Helper loads the high-score data from the file.
     */
    private void loadHighScore() {
        // First, try loading from the writable file
        Path writableFilePath = Paths.get(WRITABLE_HIGH_SCORE_FILE);
        if (Files.exists(writableFilePath)) {
            loadFromFile(writableFilePath);
        } else {
            // If writable file doesn't exist, load from classpath
            try (Scanner scanner = new Scanner(HighScore.class.getResourceAsStream(HIGH_SCORE_FILE))) {
                if (scanner.hasNextLine()) {
                    myScore = Integer.parseInt(scanner.nextLine());
                    myPlayerName = scanner.nextLine();
                    myDate = LocalDateTime.parse(scanner.nextLine(), DATE_FORMATTER);
                } else {
                    resetHighScore();
                }
            } catch (Exception e) {
                resetHighScore();
            }
        }
    }

    private void loadFromFile(Path path) {
        try (Scanner scanner = new Scanner(path)) {
            if (scanner.hasNextLine()) {
                myScore = Integer.parseInt(scanner.nextLine());
                myPlayerName = scanner.nextLine();
                myDate = LocalDateTime.parse(scanner.nextLine(), DATE_FORMATTER);
            } else {
                resetHighScore();
            }
        } catch (IOException e) {
            e.printStackTrace();
            resetHighScore();
        }
    }

    /**
     * Saves the new high score if it's higher than the current one.
     *
     * @param theNewScore the new high score
     * @param theNewPlayerName the name of the player achieving the new high score
     */
    public void saveHighScore(int theNewScore, String theNewPlayerName) {
        if (theNewScore > myScore) {
            myScore = theNewScore;
            myPlayerName = theNewPlayerName;
            myDate = LocalDateTime.now();

            Path writableFilePath = Paths.get(WRITABLE_HIGH_SCORE_FILE);
            try {
                Files.createDirectories(writableFilePath.getParent());
                try (PrintWriter writer = new PrintWriter(new FileWriter(writableFilePath.toFile()))) {
                    writer.println(myScore);
                    writer.println(myPlayerName);
                    writer.println(myDate.format(DATE_FORMATTER));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Retrieves the system's username.
     *
     * @return the system's username
     */
    public static String getSystemUserName() {
        String userName = "Unknown";
        try {
            userName = System.getProperty("user.name");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return userName;
    }

    /**
     * Resets the high score to default values.
     */
    public void resetHighScore() {
        myScore = 0;
        myPlayerName = "N/A";
        myDate = LocalDateTime.now();
        Path writableFilePath = Paths.get(WRITABLE_HIGH_SCORE_FILE);
        try {
            Files.createDirectories(writableFilePath.getParent());
            try (PrintWriter writer = new PrintWriter(new FileWriter(writableFilePath.toFile()))) {
                writer.println(myScore);
                writer.println(myPlayerName);
                writer.println(myDate.format(DATE_FORMATTER));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
