package Model;

public class Door {
    private boolean myLockStatus;
    private boolean myAttemptStatus;
    Door() {
        myLockStatus = true;
        myAttemptStatus = false;
    }
    public boolean getMyLockStatus() {
        return myLockStatus;
    }
    public boolean getMyAttemptStatus() {
        return myAttemptStatus;
    }
    void setMyLockStatus(boolean theLockStatus) {
        myLockStatus = theLockStatus;
    }
    void setMyAttemptStatus(boolean theAttemptStatus) {
        myAttemptStatus = theAttemptStatus;
    }
}
