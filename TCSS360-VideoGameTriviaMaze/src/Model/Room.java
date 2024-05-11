package Model;


/**
 * Room class represents a room withing a maze
 * Each room has multiple doors max 4, allowing access to adjective rooms.
 * TODO: Currently, all rooms are created with four doors. Each "doorway" technically has two doors,
 *  * but this behavior may need to be adjusted.
 * This class is used in conjunction with other classes to model the layout and structure of a maze.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */

public class Room {
    /**
     * Array of doors in the room.
     */
    private final Door[] myDoors;

    /**
     * Constructs a new Room object with four doors.
     *
     * <p>TODO: Currently, all rooms are created with four doors. Each "doorway" technically has two doors,
     *      but this behavior may need to be adjusted.</p>
     */
    Room() {
        myDoors = new Door[4];
        for (int i = 0; i < 4; i++) {
            myDoors[i] = new Door();
        }
    }
    /**
     * Returns the door in the specified direction.
     *
     * @param theDirection the direction of the door to retrieve
     * @return the door in the specified direction
     */
    Door getMyDoor(Direction theDirection) {
        return myDoors[theDirection.getMyValue()];
    }

    @Override
    public String toString() {
        StringBuilder roomString = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            roomString.append(i).append(" = ").append("Door ").append(Direction.getDirectionInt(i))
                    .append(" Attempt Status: ").append(getMyDoor(Direction.getDirectionInt(i))).append("\n");
        }
        return roomString.toString();
    }
}
