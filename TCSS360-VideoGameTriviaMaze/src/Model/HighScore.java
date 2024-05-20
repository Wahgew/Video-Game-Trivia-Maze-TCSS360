package Model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;

public class HighScore {
    private static final String HIGH_SCORE_FILE = "highscore.txt";
    private int myScore;
    private String myPlayerName;
    private LocalDateTime myDate;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");

    public HighScore() {
        loadHighScore();
    }

    public int getScore() {
        return myScore;
    }

    public String getPlayerName() {
        return myPlayerName;
    }

    public String getDate() {
        return myDate.format(DATE_FORMATTER);
    }

    private void loadHighScore() {
        try (Scanner scanner = new Scanner(new File(HIGH_SCORE_FILE))) {
            if (scanner.hasNextLine()) {
                myScore = Integer.parseInt(scanner.nextLine());
                myPlayerName = scanner.nextLine();
                myDate = LocalDateTime.parse(scanner.nextLine(), DATE_FORMATTER);
            } else {
                myScore = 0;
                myPlayerName = "N/A";
                myDate = LocalDateTime.now();
            }
        } catch (FileNotFoundException e) {
            myScore = 0;
            myPlayerName = "N/A";
            myDate =LocalDateTime.now();
        }
    }

    public void saveHighScore(int theNewScore, String theNewPlayerName) {
        if (theNewScore > myScore) {
            myScore = theNewScore;
            myPlayerName = theNewPlayerName;
            myDate =LocalDateTime.now();
            try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
                writer.println(myScore);
                writer.println(myPlayerName);
                writer.println(myDate.format(DATE_FORMATTER));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getSystemUserName() {
        String userName = "Unknown";
        try {
            userName = System.getProperty("user.name");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return userName;
    }

    public void resetHighScore() {
        myScore = 0;
        myPlayerName = "N/A";
        myDate =LocalDateTime.now();
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
            writer.println(myScore);
            writer.println(myPlayerName);
            writer.println(myDate.format(DATE_FORMATTER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
