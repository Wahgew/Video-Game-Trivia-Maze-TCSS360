package Model;

public class Door {
    private boolean myLockStatus;
    Door() {
        myLockStatus = true;
    }
    public boolean getMyLockStatus() {
        return myLockStatus;
    }
     void setMyLockStatus(boolean theLockStatus) {
        myLockStatus = theLockStatus;
    }
}
