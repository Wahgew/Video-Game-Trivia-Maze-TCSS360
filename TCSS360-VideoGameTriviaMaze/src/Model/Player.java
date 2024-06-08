package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.PropertyChangeSupport;
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

    /**
     * Total number of incorrect answers given by the player.
     */
    private int myIncorrectTotal;

    /**
     * Number of consecutive correct answers given by the player.
     */
    private int myConsecutiveAns;

    /**
     * Indicates whether the player achieved victory in the game.
     */
    private boolean myVictory;

    /**
     * Indicates whether the player resorted to cheating during the game.
     */
    private boolean myCheat;

    /**
     * The layout of the maze in which the player is navigating.
     */
    private int myHealth;

    /**
     * The layout of the maze in which the player is navigating.
     */
    private String myMazeLayout;

    /**
     * A mapping of question IDs to boolean values indicating whether the player has answered the questions.
     */
    private HashMap<Integer, Boolean> myQuestionsAnswered;

    /**
     * PropertyChangeSupport instance for managing property change listeners.
     * This field is marked with @JsonIgnore to exclude it from JSON serialization.
     */
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
        myCheat = false;
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

    /**
     * Reset the player instance, creating a new player instance.
     */
    public static synchronized void resetPlayer() {
        mySingleton = new Player();
    }

    /**
     * Gets the player's current health.
     *
     * @return the player's health
     */
    public int getMyHealth() {
        return myHealth;
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

    /**
     * Moves the player in the specified direction if the move is valid.
     *
     * @param theDirection the direction to move the player
     */
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

    /**
     * Gets the number of incorrect answers given by the player.
     *
     * @return the number of incorrect answers given by the player
     */
    public int getMyIncorrectTotal() {
        return myIncorrectTotal;
    }

    /**
     * Gets the number of consecutive correct answers given by the player.
     *
     * @return the number of consecutive correct answers given by the player
     */
    public int getMyConsecutiveAns() {
        return myConsecutiveAns;
    }

    /**
     * Gets the questions answered by the player.
     *
     * @return a HashMap containing the questions answered by the player
     */
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

    /**
     * Gets the layout of the maze.
     *
     * @return a string representing the layout of the maze
     */
    public String getMyMazeLayout() {
        return myMazeLayout;
    }

    /**
     * Checks if the player is using cheat mode.
     *
     * @return true if the player is using cheat mode, false otherwise
     */
    public boolean getMyCheat() {
        return myCheat;
    }

    /**
     * Gets the PropertyChangeSupport instance.
     *
     * @return the PropertyChangeSupport instance
     */
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

    /**
     * Checks if the player has achieved victory.
     *
     * @return true if the player has achieved victory, false otherwise
     */
    public boolean getMyVictory() {
        return myVictory;
    }

    /**
     * Sets the player's score.
     *
     * @param theScore the new score for the player
     */
    public void setMyScore(int theScore) {
        myScore = theScore;
    }

    /**
     * Sets the number of correct answers given by the player.
     *
     * @param theCorrectTotal the new number of correct answers
     */
    public void setMyCorrectTotal(int theCorrectTotal) {
        myCorrectTotal = theCorrectTotal;
    }

    /**
     * Sets the number of incorrect answers given by the player.
     *
     * @param theIncorrectTotal the new number of incorrect answers
     */
    public void setMyIncorrectTotal(int theIncorrectTotal) {
        myIncorrectTotal = theIncorrectTotal;
    }

    /**
     * Sets the number of consecutive correct answers given by the player.
     *
     * @param theConsecutiveAns the new number of consecutive correct answers
     */
    public void setMyConsecutiveAns(int theConsecutiveAns) {
        myConsecutiveAns = theConsecutiveAns;
    }

    /**
     * Sets the player's health.
     *
     * @param theHealth the new health value for the player
     */
    public void setMyHealth(int theHealth) {
        myHealth = theHealth;
    }

    /**
     * Sets the player's direction.
     *
     * @param theDirection the new direction for the player
     */
    public void setMyDirection(Direction theDirection) {
        myDirection = theDirection;
    }

    /**
     * Sets the column index of the player's location.
     *
     * @param theLocationCol the new column index of the player's location
     */
    public void setMyLocationCol(int theLocationCol) {
        myLocationCol = theLocationCol;
    }

    /**
     * Sets the row index of the player's location.
     *
     * @param theLocationRow the new row index of the player's location
     */
    public void setMyLocationRow(int theLocationRow) {
        myLocationRow = theLocationRow;
    }

    /**
     * Sets the questions answered by the player.
     *
     * @param theQuestionsAnswered the new set of questions answered
     */
    public void setMyQuestionsAnswered(HashMap<Integer, Boolean> theQuestionsAnswered) {
        myQuestionsAnswered = theQuestionsAnswered;
    }

    /**
     * Sets the player's victory status.
     *
     * @param theVictory the new victory status
     */
    public void setMyVictory(boolean theVictory) {
        myVictory = theVictory;
    }

    /**
     * Sets the player's cheat mode status.
     *
     * @param theCheat the new cheat mode status
     */
    public void setMyCheat(boolean theCheat) {
        myCheat = theCheat;
    }

    /**
     * Sets the layout of the maze.
     *
     * @param theMazeLayout the new layout of the maze
     */
    public void setMyMazeLayout(String theMazeLayout) {
        myMazeLayout = theMazeLayout;
    }

    /**
     * Checks if the player has achieved victory by comparing the player's
     * current location with the exit location of the maze.
     *
     * @return true if the player has reached the exit, false otherwise
     */
    boolean checkVictory() {
        return (Maze.getInstance().getMyExitRow() == getMyLocationRow()
                && Maze.getInstance().getMyExitColumn() == getMyLocationCol());
    }

    /**
     * Fires a property change event for the player's score.
     */
    private void fireScoreChange() {
        myPCS.firePropertyChange("score", null, myScore);
    }
}
