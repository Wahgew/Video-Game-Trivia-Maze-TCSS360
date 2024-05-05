package Model;

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
     * Attempt status.
     */
    private boolean myAttemptStatus;

    private boolean myLeadsOutofBounds; // not actually useful? maybe remove this

    /**
     * Constructs a new Door object with default lock
     * status as true (locked) and attempt status as false.
     */
    Door() {
        myLockStatus = true;
        myAttemptStatus = false;
        myLeadsOutofBounds = false;
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

    /**
     * Sets the lock status of the door.
     *
     * @param theLockStatus true to lock the door, false to unlock it
     */
    void setMyLockStatus(boolean theLockStatus) {
        myLockStatus = theLockStatus;
    }

    /**
     * Sets the attempt status of the door.
     *
     * @param theAttemptStatus true if an attempt has been made to interact with the door, false otherwise
     */
    void setMyAttemptStatus(boolean theAttemptStatus) {
        myAttemptStatus = theAttemptStatus;
    }

    void setMyLeadsOutofBounds(boolean theOOBStatus) {
        myLeadsOutofBounds = theOOBStatus;
        myLockStatus = theOOBStatus;
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

    @Override
    public String toString() {
        return "Door{" +
                "myLockStatus=" + myLockStatus +
                ", myAttemptStatus=" + myAttemptStatus +
                ", myLeadsOutofBounds=" + myLeadsOutofBounds +
                '}';
    }
}
