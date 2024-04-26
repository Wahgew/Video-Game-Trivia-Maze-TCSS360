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

    private boolean myLeadsOutofBounds;

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
    }
}
