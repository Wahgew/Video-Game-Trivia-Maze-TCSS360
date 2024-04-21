package Model;

public class Room {
    private Door[] myDoors;
    Room() { //TODO: ALL rooms are created with 4 doors. Each "doorway" has two doors technically? need to fix.
        myDoors = new Door[4];
    }
    /**
     * Returns door in the direction of parameter.
     */
    Door getMyDoor(Direction theDirection) {
        return myDoors[theDirection.getMyValue()];
    }
}
