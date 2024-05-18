package Model;

import javax.swing.*;

/**
 * The Door class represents a door object with a lock and attempt status.
 *
 * This classes provides methods to access and modify the lock status and attempt status
 * the Lock status indicates wherever the door is locked or unlocked, while the attempt status\
 * signals if an attempt has been made to interact with the door.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */
public class Door {

    /**
     * Door lock status.
     */
    private boolean myLockStatus;

    /**
     * Question objects
     */
    private Question myQuestion;

    /**
     * Attempt status of the Door
     * Player can only attempt to pass through Door if attempt status is false.
     */
    private boolean myAttemptStatus;

    private boolean myLeadsOutOfBounds; // not actually useful? maybe remove this

    private final QuestionAnswerDatabase myQdb;
    /**
     * Constructs a new Door object with default lock
     * status as true (locked) and attempt status as false.
     */
    Door() {
        myLockStatus = true;
        myAttemptStatus = false;
        myLeadsOutOfBounds = false;
        myQuestion = null;
        myQdb = QuestionAnswerDatabase.getInstance();
    }

    /**
     * Gets the lock status of the door.
     *
     * @return true if the door is locked, false if it is unlocked
     */
    public boolean getMyLockStatus() {
        return myLockStatus;
    }

    /**
     * Gets the attempt status of the door.
     *
     * @return true if an attempt has been made to interact with the door, false otherwise
     */
    public boolean getMyAttemptStatus() {
        return myAttemptStatus;
    }
    public boolean getMyLeadsOutOfBounds() {
        return myLeadsOutOfBounds;
    }

    /**
     * Sets the lock status of the door.
     *
     * @param theLockStatus true to lock the door, false to unlock it
     */
    public void setMyLockStatus(boolean theLockStatus) {
        myLockStatus = theLockStatus;
    }

    /**
     * Sets the attempt status of the door.
     * Player can only attempt to pass through Door if attempt status is false.
     * @param theAttemptStatus if the Door has been attempted.
     */
    public void setMyAttemptStatus(boolean theAttemptStatus) {
        myAttemptStatus = theAttemptStatus;
    }

    /**
     * Getter method for question objects that attached the doors.
     * @return the Question objects attached.
     */
    public Question getQuestionObject() {
        return myQuestion;
    }

    void setMyLeadsOutOfBounds(boolean theOOBStatus) {
        myLeadsOutOfBounds = theOOBStatus;
        myLockStatus = theOOBStatus;
        myAttemptStatus = theOOBStatus;
    }

    public void setNonPassable(boolean thePassibility) {
        myLockStatus = thePassibility ;
        myAttemptStatus = thePassibility ;
        myLeadsOutOfBounds = thePassibility ;
    }

    /**
     * Should be called when a question has been attempted, "syncs" the state of
     * the corresponding door and adjacent room's door.
     * @param theSuccess if the question was answered correctly.
     * @param theRow the row of the room the player is attempting the question from.
     * @param theCol the column of the room the player is attempting the question from.
     * @param theDirection the Direction the player is attempting to move.
     */
    static void questionAttempted(boolean theSuccess, int theRow, int theCol, Direction theDirection) {
        if (theSuccess) {
            Maze.getInstance().getMyRoom(theRow, theCol).getMyDoor(theDirection).setMyAttemptStatus(true);
            Maze.getInstance().getMyRoom(theRow, theCol).getMyDoor(theDirection).setMyLockStatus(false);

            Maze.getInstance().getMyAdjacentRoom(theDirection, theRow, theCol)
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).setMyAttemptStatus(true);
            Maze.getInstance().getMyAdjacentRoom(theDirection, theRow, theCol)
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).setMyLockStatus(false);
        } else {
            Maze.getInstance().getMyRoom(theRow, theCol).getMyDoor(theDirection).setMyAttemptStatus(true);
            Maze.getInstance().getMyRoom(theRow, theCol).getMyDoor(theDirection).setMyLockStatus(true);

            Maze.getInstance().getMyAdjacentRoom(theDirection, theRow, theCol)
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).setMyAttemptStatus(true);
            Maze.getInstance().getMyAdjacentRoom(theDirection, theRow, theCol)
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).setMyLockStatus(true);
        }
    }

    //TODO: this method may need the row, col, and direction of player
    public Question askQuestion() {
        myQuestion = QuestionAnswerDatabase.getInstance().getRandomQuestion();
        return myQuestion;
    }

    @Override
    public String toString() {
        return "Door{" +
                "myLockStatus=" + myLockStatus +
                ", myAttemptStatus=" + myAttemptStatus +
                ", myLeadsOutofBounds=" + myLeadsOutOfBounds +
                '}';
    }
}
