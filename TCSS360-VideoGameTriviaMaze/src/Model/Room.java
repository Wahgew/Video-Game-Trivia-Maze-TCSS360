package Model;

public class Room {
    private Door[] myDoors;
    Room() {
        myDoors = new Door[4];
    }
    /**
     * Returns door in the direction of parameter.
     */
    Door getMyDoor(Direction theDirection) {
        return myDoors[theDirection.getMyValue()];
    }
}
