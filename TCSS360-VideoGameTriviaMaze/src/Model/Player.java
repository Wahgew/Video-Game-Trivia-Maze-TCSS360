package Model;

import Controller.MazeController;

import javax.swing.*;
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
    private Direction myFacingDirection;
    /**
     * Number of correct answers given by the player.
     */
    private int myCorrectAns;
    private int myConsecutiveAns;
    private boolean myVictory;
    private int myHealth;
    private HashMap<Integer,Boolean> myQuestionsAnswered;

    /**
     * Constructs a new Player object with default attributes.
     * The player's initial location is set to the maze entrance,
     * and both the score and number of correct answers are initialized to zero.
     */
    public Player() {
        myLocationRow = Maze.getInstance().getMyEntranceRow();
        myLocationCol = Maze.getInstance().getMyEntranceColumn();
        myHealth = 3;
        myScore = 0;
        myCorrectAns = 0;
        myConsecutiveAns = 0;
        myVictory = false;
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

    public HashMap<Integer, Boolean> getQuestionsAnswered() {
        return myQuestionsAnswered;
    }

    /**
     * Checks if the player can attempt to move towards the room corresponding to the parameter.
     * E.g, the door in the direction the player is moving toward is not locked or hasn't attempted to answer.
     * @param theX the row the player is attempting to move to.
     * @param theY the column the player is attempting to move to.
     * @return if the movement is a valid player move.
     */
    boolean validPlayerMove(int theX, int theY) {
        boolean moveAllowed = false;
        if (theX >= 0 && theX < Maze.getInstance().getMyMazeRows()
                && theY >= 0 && theY < Maze.getInstance().getMyMazeCols()) {
            if (!Maze.getInstance().getMyRoom(theX, theY) // if door is not locked
                    .getMyDoor(Direction.getPlayerDirection(theX, theY)).getMyLockStatus()) {
                moveAllowed = true;
            } else if (Maze.getInstance().getMyRoom(theX, theY) // if door hasn't been attempted yet.
                    .getMyDoor(Direction.getPlayerDirection(theX, theY)).getMyAttemptStatus()) {
                moveAllowed = true;
            }
        }
        return moveAllowed;
    }
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
            } else if (Maze.getInstance().getMyRoom(playerRow, playerCol) // if door hasn't been attempted yet.
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).getMyAttemptStatus()) {
                moveAllowed = true;
            }
        }
        return moveAllowed;
    }
    boolean validPlayerMove(int theMove) { // TODO: TEST VERSION TEST VERSION
        boolean moveAllowed = false;
        int playerRow = myLocationRow;
        int playerCol = myLocationCol;
        switch (theMove) {
            case 0 -> playerRow--; // North
            case 1 -> playerCol++; // East
            case 2 -> playerRow++; // South
            case 3 -> playerCol--; // West
        }
        if (playerRow >= 0 && playerRow < Maze.getInstance().getMyMazeRows()
                && playerCol >= 0 && playerCol < Maze.getInstance().getMyMazeCols()) {
            if (!Maze.getInstance().getMyRoom(playerRow, playerCol) // if door is not locked
                    .getMyDoor(Direction.getPlayerDirection(playerRow, playerCol)).getMyLockStatus()) {
                moveAllowed = true;
            } else if (!Maze.getInstance().getMyRoom(playerRow, playerCol) // if door hasn't been attempted yet.
                    .getMyDoor(Direction.getPlayerDirection(playerRow, playerCol)).getMyAttemptStatus()) {
                moveAllowed = true;
            }
        }
        return moveAllowed;
    }
    boolean attemptMove(Direction theDirection, Scanner theInput) { //TODO: possibly change this, just for testing rn
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
                //Question testQuestion = new Question();
//
//                /*
//                 * this is the new test,
//                 * but the database does not work because I still cant figure out the connection problem yet.
//                 * I believe the correct way this works is the door will call the database.
//                 */
////                QuestionAnswerDatabase database = new QuestionAnswerDatabase();
////                database.getRandomQuestion();
//
////                System.out.println(testQuestion);         //TODO: THIS IS WHERE WE WOULD SWAP TO USING SQLITE DATABASE
////                String userAns = theInput.nextLine();
////                if (testQuestion.checkAnswer(userAns)) { // check player's answer
////                    allowMove = true;
////                    Door.questionAttempted(true, myLocationRow, myLocationCol, theDirection);
////                    myCorrectAns++;
////                    scoreUpdate(true);
////                } else { // player failed to answer correctly
////                    Door.questionAttempted(false, myLocationRow, myLocationCol, theDirection);
////                    scoreUpdate(false;
////                }
            }
        }
        return allowMove;
    }
    boolean attemptMove(Direction theDirection) { //TODO: possibly change this, just for testing rn
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
//                Question randQuestion = MazeController.getQuestionDatabase().getRandomQuestion(); // commented out is database connection version
//                String userAns = JOptionPane.showInputDialog(randQuestion.getQuestion());
                TreeMap<String,Boolean> tempList = new TreeMap<>();
                tempList.put("Andy", true);
                ShortAnswerQuestion randQuestion = new ShortAnswerQuestion("What is Andrew Hwang's nickname?",new AnswerData(tempList), "Short",20);
                String userAns = JOptionPane.showInputDialog(randQuestion.getQuestion());
                if (userAns.equals(randQuestion.getCorrectAnswer())) { // check player's answer TODO: sanitize user input w/ to lowercase??
                    allowMove = true;
                    Door.questionAttempted(true, myLocationRow, myLocationCol, theDirection);
                    myCorrectAns++;
                    scoreUpdate(true);
                } else { // player failed to answer correctly
                    Door.questionAttempted(false, myLocationRow, myLocationCol, theDirection);
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
    void scoreUpdate(boolean theSuccess) {
        if (theSuccess) {
            myConsecutiveAns++;
            myCorrectAns++;
            if (myConsecutiveAns >= 5) {
                myScore += (100 * 5);
            } else {
                myScore += (100 * myConsecutiveAns);
            }
        } else {
            myConsecutiveAns = 0;
            myScore -= 100;
        }
    }
    /**
     * NEED TO VALIDATE THE MOVE BEFORE USING THIS IS JUST A SETTER.
     * @param theRow the row moving to.
     * @param theCol the column moving to.
     */
    public void movePlayer(int theRow, int theCol) {
        myLocationRow = theRow;
        myLocationCol = theCol;
    }
    public void movePlayer(Direction theDirection) {
        if (attemptMove(theDirection)) {
            myFacingDirection = theDirection;
            switch (theDirection) {
                case NORTH -> myLocationRow--;
                case SOUTH -> myLocationRow++;
                case EAST -> myLocationCol++;
                case WEST -> myLocationCol--;
            }
            myVictory = checkVictory();
        }
    }
    public void movePlayer(Direction theDirection, Scanner theInput) { // TODO: remove scanner usage when not needed
        if (attemptMove(theDirection, theInput)) {
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
        if (attemptMove(Direction.getDirectionInt(theMove))) {
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
    public int getMyCorrectAns() {
        return myCorrectAns;
    }

    /**
     * Gets player direction.
     *
     * @return direction of player
     */
    public Direction getDirection() {
       return myFacingDirection;
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
    boolean checkVictory() {
        return (Maze.getInstance().getMyExitRow() == getMyLocationRow()
                && Maze.getInstance().getMyExitColumn() == getMyLocationCol());
    }
}
