package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The player class represents a player in the game.
 * Each player has a current location, score, and number of correct answers.
 * The player's current location is represented by row and column indices.
 * The score indicates the player's progress or performance in the game,
 * while the number of correct answers reflects their success rate.
 * This class implements the Singleton design pattern, ensuring that only one instance
 * of the player exists throughout the program.
 * The Player class provides methods for moving the player within the game environment,
 * checking if a move is valid, and accessing player information such as location, score, and
 * number of correct answers.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */
public class Player {

    /**
     * Singleton instance of Player.
     */
    private static Player mySingleton;

    /**
     * Row index of the player's current location.
     */
    private int myLocationRow;

    /**
     * Column index of the player's current location.
     */
    private int myLocationCol;

    /**
     * Player's current score.
     */
    private int myScore;

    /**
     * Player's facing a direction.
     */
    private Direction myDirection;
    /**
     * Number of correct answers given by the player.
     */
    private int myCorrectTotal;
    private int myIncorrectTotal;
    private int myConsecutiveAns;
    private boolean myVictory;
    private int myHealth;
    private String myMazeLayout;
    private HashMap<Integer, Boolean> myQuestionsAnswered;
    private boolean myCheatOn;
    @JsonIgnore
    private final PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    /**
     * Constructs a new Player object with default attributes.
     * The player's initial location is set to the maze entrance,
     * and both the score and number of correct answers are initialized to zero.
     */
    private Player() {
        myLocationRow = Maze.getInstance().getMyEntranceRow();
        myLocationCol = Maze.getInstance().getMyEntranceColumn();
        myHealth = 3;
        myScore = 0;
        myConsecutiveAns = 0;
        myCorrectTotal = 0;
        myIncorrectTotal = 0;
        myVictory = false;
        myCheatOn = false;
        myQuestionsAnswered = new HashMap<>();
    }

    /**
     * Returns the Singleton instance of Player.
     * If no instance exists, a new player is created.
     *
     * @return Singleton instance of Player
     */
    public static synchronized Player getInstance() {
        if (mySingleton == null) {
            mySingleton = new Player();
        }
        return mySingleton;
    }

    public static synchronized void resetPlayer() {
        mySingleton = new Player();
    }

    /**
     * Reduces the health of the object by one.
     * If the current health is greater than zero, it decrements the health by one.
     * If the health becomes zero or negative, the object is considered to be "dead".
     */
    public void decreaseHealth() {
        if (myHealth > 0) {
            myHealth--;
        }
    }

    public int getMyHealth() {
        return myHealth;
    }

    /**
     * Get the question the player answer during their session.
     *
     * @param theID          the question ID.
     * @param theCorrectness the correctness of their answer.
     */
    public void QuestionsAnswered(int theID, Boolean theCorrectness) {
        myQuestionsAnswered.put(theID, theCorrectness);
        HashMap<Integer, Boolean> tempStats = new HashMap<>();
        tempStats.put(theID, theCorrectness);

        if (tempStats.get(theID)) {
            myCorrectTotal++;
            tempStats.remove(theID);
        } else {
            myIncorrectTotal++;
            tempStats.remove(theID);
        }
    }

    /**
     * Checks if the player can attempt to move towards the room corresponding to the parameter.
     * E.g, the door in the direction the player is moving toward is not locked or hasn't attempted to answer.
     * @param theDirection the Direction the player is moving toward.
     * @return if the movement is a valid player move.
     */
    public boolean validPlayerMove(Direction theDirection) {
        boolean moveAllowed = false;
        int playerRow = myLocationRow;
        int playerCol = myLocationCol;
        switch (theDirection) {
            case NORTH -> playerRow--;
            case SOUTH -> playerRow++;
            case EAST -> playerCol++;
            case WEST -> playerCol--;
        }
        if (playerRow >= 0 && playerRow < Maze.getInstance().getMyMazeRows()
                && playerCol >= 0 && playerCol < Maze.getInstance().getMyMazeCols()) {
            if (!Maze.getInstance().getMyRoom(playerRow, playerCol) // if door is not locked
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).getMyLockStatus()) {
                moveAllowed = true;
            } else if (!Maze.getInstance().getMyRoom(playerRow, playerCol) // if door hasn't been attempted yet.
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).getMyAttemptStatus()) {
                moveAllowed = true;
            }
        }
        return moveAllowed;
    }
    boolean attemptMove(Direction theDirection) {
        boolean allowMove = false;
        int playerRow = myLocationRow;
        int playerCol = myLocationCol;
        switch (theDirection) {
            case NORTH -> playerRow--;
            case SOUTH -> playerRow++;
            case EAST -> playerCol++;
            case WEST -> playerCol--;
        }
        if (playerRow >= 0 && playerRow < Maze.getInstance().getMyMazeRows()
                && playerCol >= 0 && playerCol < Maze.getInstance().getMyMazeCols()) {
            if (!Maze.getInstance().getMyRoom(myLocationRow, myLocationCol).getMyDoor(theDirection).getMyLockStatus()) { // check if door is locked
                allowMove = true;
            } else if (!Maze.getInstance().getMyRoom(myLocationRow, myLocationCol).getMyDoor(theDirection).getMyAttemptStatus()) { // check if player has attempted door
                Question randQuestion = Maze.getInstance().getMyRoom(myLocationRow, myLocationCol).getMyDoor(theDirection).askQuestion();
                if (Objects.equals(randQuestion.getType(), "Audio")) {
                    AuditoryQuestion audioQuestion = (AuditoryQuestion) randQuestion;
                    Clip audio = audioQuestion.playMusic();
                    audio.start();
                    JOptionPane.showMessageDialog(null, audioQuestion.getQuestion() + "\nPress OK to stop playing."); // temporary testing
                    audio.stop();
                }
                String userAns;
                if (Objects.equals(randQuestion.getType(), "Multi")) {
                    userAns = JOptionPane.showInputDialog(randQuestion.getQuestion() + "\n" + randQuestion.getAnswers()).toLowerCase();
                } else if (Objects.equals(randQuestion.getType(), "Image")) {
                    assert randQuestion instanceof ImageQuestion;
                    ImageQuestion imgQ = (ImageQuestion) randQuestion;
                    System.out.println("Debug IMAGE: " + "src/" + imgQ.getImagePath());
                    try {
                        BufferedImage buffImage = ImageIO.read(new File("src/" + imgQ.getImagePath()));
                        JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(buffImage)),
                                imgQ.getQuestion(), JOptionPane.PLAIN_MESSAGE);
                        // Cannot invoke "java.awt.Image.getProperty(String, java.awt.image.ImageObserver)" because "image" is null <-- FILE FORMAT OR COLOR PROFILE ERROR
                        // NEED TO RE-EXPORT IN PHOTOSHOP OR GIMP AS sRGB, ASK KEN IF YOU NEED HELP
                        userAns = JOptionPane.showInputDialog(randQuestion.getQuestion()).toLowerCase();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    userAns = JOptionPane.showInputDialog(randQuestion.getQuestion()).toLowerCase(); // temporary testing
                }
                if (userAns.equals(randQuestion.getCorrectAnswer().toLowerCase())) { // check player's answer
                    allowMove = true;
                    //Door.questionAttempted(true, myLocationRow, myLocationCol, theDirection);
                    scoreUpdate(true);
                } else { // player failed to answer correctly
                    //Door.questionAttempted(false, myLocationRow, myLocationCol, theDirection);
                    scoreUpdate(false);
                }
            }
        }
        return allowMove;
    }
    /**
     * Called to update player score when question is attempted.
     * Players earn points for correct answers, with the score multiplier increasing with consecutive correct answers
     * up to a maximum of 5x. The base score for each correct answer is 100 points, multiplied by the consecutive
     * correct answer multiplier.  A deduction of 100 points is acquired for each incorrect answer,
     * however, the multiplier does not apply to losing points.
     * @param theSuccess if the question was answered correctly.
     */
    public void scoreUpdate(boolean theSuccess) {
        if (theSuccess) {
            myConsecutiveAns++;
            if (myConsecutiveAns >= 5) {
                myScore += (100 * 5);
            } else {
                myScore += (100 * myConsecutiveAns); // -1 if you want the number to be synced up.
            }
        } else {
            myConsecutiveAns = 0;
            myScore -= 100;
        }
        fireScoreChange();
    }
    public void movePlayer(Direction theDirection) {
        if (validPlayerMove(theDirection)) {
            myDirection = theDirection;
            switch (theDirection) {
                case NORTH -> myLocationRow--;
                case SOUTH -> myLocationRow++;
                case EAST -> myLocationCol++;
                case WEST -> myLocationCol--;
            }
            myVictory = checkVictory();
        }
    }
    public void movePlayer(int theMove) { //TESTING VERSION
        if (validPlayerMove(Direction.getDirectionInt(theMove))) {
            switch (theMove) {
                case 0 -> myLocationRow--; // North
                case 1 -> myLocationCol++; // East
                case 2 -> myLocationRow++; // South
                case 3 -> myLocationCol--; // West
            }
        }
    }
    /**
     * Gets the row index of the player's current location.
     *
     * @return the row index of the player's current location
     */
    public int getMyLocationRow() {
        return myLocationRow;
    }

    /**
     * Gets the column index of the player's current location.
     *
     * @return the column index of the player's current location
     */
    public int getMyLocationCol() {
        return myLocationCol;
    }

    /**
     * Gets the number of correct answers given by the player.
     *
     * @return the number of correct answers given by the player
     */
    public int getMyCorrectTotal() {
        return myCorrectTotal;
    }

    public int getMyIncorrectTotal() {
        return myIncorrectTotal;
    }

    public HashMap<Integer, Boolean> getMyQuestionsAnswered() {
        return myQuestionsAnswered;
    }

    /**
     * Gets player direction.
     *
     * @return direction of player
     */
    public Direction getMyDirection() {
       return myDirection;
    }

    public String getMyMazeLayout() {
        return myMazeLayout;
    }

    @JsonIgnore
    public PropertyChangeSupport getMyPCS() {
        return myPCS;
    }

    /**
     * Gets the player's current score.
     *
     * @return the player's current score
     */
    public int getMyScore() {
        return myScore;
    }
    public boolean getMyVictory() {
        return myVictory;
    }

    public void setMyScore(int theScore) {
        myScore = theScore;
    }

    public void setMyCorrectTotal(int theCorrectTotal) {
        myCorrectTotal = theCorrectTotal;
    }

    public void setMyIncorrectTotal(int theIncorrectTotal) {
        myIncorrectTotal = theIncorrectTotal;
    }

    public void setMyConsecutiveAns(int theConsecutiveAns) {
        myConsecutiveAns = theConsecutiveAns;
    }

    public void setMyHealth(int theHealth) {
        myHealth = theHealth;
    }

    public void setMyDirection(Direction theDirection) {
        myDirection = theDirection;
    }

    public void setMyLocationCol(int theLocationCol) {
        myLocationCol = theLocationCol;
    }

    public void setMyLocationRow(int theLocationRow) {
        myLocationRow = theLocationRow;
    }

    public void setMyQuestionsAnswered(HashMap<Integer, Boolean> theQuestionsAnswered) {
        myQuestionsAnswered = theQuestionsAnswered;
    }

    public void setMyVictory(boolean theVictory) {
        myVictory = theVictory;
    }

    public void setMyMazeLayout(String theMazeLayout) {
        myMazeLayout = theMazeLayout;
    }

    boolean checkVictory() {
        return (Maze.getInstance().getMyExitRow() == getMyLocationRow()
                && Maze.getInstance().getMyExitColumn() == getMyLocationCol());
    }

    private void fireScoreChange() {
        myPCS.firePropertyChange("score", null, myScore);
    }


}
